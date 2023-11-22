package xxl;

import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.CellOutOfRangeException;
import xxl.storagestructure.*;
import xxl.content.*;
import xxl.content.functions.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;

    /** Cells */
    private StorageStructure _cells;

    /** Cutbuffer */
    private CutBuffer _cutbuffer;

    /** Spreadsheet object has unsaved changes */
    private boolean _hasUnsavedChanges;

    /* ArrayList of all teh users that have access to this spreadsheet */
    private ArrayList<User> _users = new ArrayList<User>();


    /**
     * Constructor.
     * 
     * @param lines spreadsheet's number of lines
     * @param columns spreadsheet's number of columns 
     */
    public Spreadsheet(int lines, int columns){
        _cells = new CellHashMap(lines, columns);
        setHasUnsavedChanges(true);
    }

    /**
     * Insert specified content in specified range.
     * 
     * @param rangeSpecification
     * @param contentSpecification
     * @throws UnrecognizedEntryException
     * @throws CellOutOfRangeException
     */
    public void insertContents(String rangeSpecification, String contentSpecification) throws UnrecognizedEntryException, CellOutOfRangeException {
        Content cellContent = null;
        if(contentSpecification != "" && contentSpecification != null && !contentSpecification.isEmpty()){
            char firstChar = contentSpecification.charAt(0);

            // LiteralString
            if(firstChar == '\''){
                cellContent = new LiteralString(contentSpecification);
            }
            else if(firstChar == '='){
                String expression = contentSpecification.substring(1);

                // Reference
                if(isCellInRange(expression)){
                    cellContent = new Reference(_cells.getCell(expression), expression);
                }
                //Function
                else{
                    String[] splitedExpression = expression.split("\\(");
                    String funcName = splitedExpression[0];
                    String[] args = splitedExpression[1].substring(0, splitedExpression[1].length() -1).split(",");

                    // BinaryFunction
                    if(args.length == 2){
                        Content arg1 = newBinaryFuncArg(args[0]);
                        Content arg2 = newBinaryFuncArg(args[1]);

                        cellContent = switch(funcName){
                            case "ADD" -> new Add(arg1, arg2, expression);
                            case "SUB" -> new Sub(arg1, arg2, expression);
                            case "MUL" -> new Mul(arg1, arg2, expression);
                            case "DIV" -> new Div(arg1, arg2, expression);
                            default -> throw new UnrecognizedEntryException(funcName);
                        };
                    }
                    //IntervalFunction
                    else if(args.length == 1){
                        ArrayList<String> interval = createAddressInterval(args[0]);
                        ArrayList<Reference> intervalRef = new ArrayList<Reference>();

                        for(int i = 0; i < interval.size(); i++){
                            String address = interval.get(i);
                            intervalRef.add(new Reference(_cells.getCell(address), "=" + address));
                        }

                        cellContent = switch(funcName){
                            case "AVERAGE" -> new Average(intervalRef, expression);
                            case "PRODUCT" -> new Product(intervalRef, expression);
                            case "CONCAT" -> new Concat(intervalRef, expression);
                            case "COALESCE" -> new Coalesce(intervalRef, expression);
                            default -> throw new UnrecognizedEntryException(funcName);
                        };
                    }    
                }
            }
            // LiteralInt
            else{
                cellContent = new LiteralInt(Integer.parseInt(contentSpecification));
            }
        }

        ArrayList<String> interval = createAddressInterval(rangeSpecification);

        for(int i = 0; i < interval.size(); i++)
            _cells.getCell(interval.get(i)).setContent(cellContent);
    }

    /**
     * Delets content from specified range.
     * 
     * @param rangeSpecification
     * @throws UnrecognizedEntryException
     * @throws CellOutOfRangeException
     */
    public void deleteContents(String rangeSpecification) throws UnrecognizedEntryException, CellOutOfRangeException {
        insertContents(rangeSpecification, null);
    }

    /**
     * Builds a string with the given gamma's cell(s) and it's/their content.
     * 
     * @param gamma String with gamma (it can be a cell or an interval of cells)
     * 
     * @return String with the given cell(s) and it's/their content 
     */
    public String showCells(String gamma) throws CellOutOfRangeException{
        ArrayList<String> interval = createAddressInterval(gamma);
        int size = interval.size();

        String text = "";

        for(int i = 0; i < size; i++){
            String address = interval.get(i);
            text += address + "|" + _cells.getCell(address).contentToString();
            text += "\n";
        }

        return text + "Este intervalo tem " + size + " células.";
    }

    /**
     * Copies a given gamma from the spreadsheet to the CutBuffer.
     * 
     * @param gamma
     * @throws CellOutOfRangeException
     */
    public void copy(String gamma) throws CellOutOfRangeException{
        ArrayList<String> interval = createAddressInterval(gamma);
        
        _cutbuffer = new CutBuffer(_cells.getNumOfLines(), _cells.getNumOfColumns(), isIntervalLine(gamma));

        for(int i = 0; i < interval.size(); i++)
            _cutbuffer.insertCell(_cells.getCell(interval.get(i)));
    }
    
    /**
     * Pastes a given gamma from the Cutbuffer to the Spreadsheet.
     * 
     * @param gamma
     * @throws CellOutOfRangeException
     */
    public void paste(String gamma) throws CellOutOfRangeException{
        ArrayList<String> interval = createAddressInterval(gamma);
        
        int intervalSize = interval.size();

        // Paste until it hits a boundary
        if(intervalSize == 1){
            int[] cell = getCellLineColumn(interval.get(0));
            
            if(_cutbuffer.isLine()){
                int numColumns = _cells.getNumOfColumns();
                for(int i = 0, column = cell[1]; i < _cutbuffer.getSize() && column <= numColumns; i++, column++){
                    _cells.insertCell(_cutbuffer.getCell(i), cell[0] + ";" + column);
                }
            }
            else{
                int numLines = _cells.getNumOfLines();
                for(int i = 0, line = cell[0]; i < _cutbuffer.getSize() && line <= numLines; i++, line++){
                    _cells.insertCell(_cutbuffer.getCell(i), line + ";" + cell[1]);
                }
            }
        }
        // Paste the exact size of the cutBuffer to the interval given
        else if(intervalSize == _cutbuffer.getSize()){
            for(int i = 0; i < intervalSize; i++){
                _cells.insertCell(_cutbuffer.getCell(i), interval.get(i));
            }
        }
    }

    /**
     * Cut a given gamma. First copies it to the CutBuffer and then deletes it 
     * from the Spreadsheet.
     * 
     * @param gamma
     * @throws CellOutOfRangeException
     */
    public void cut(String gamma) throws CellOutOfRangeException{
        copy(gamma);
        try{
            deleteContents(gamma);
        } catch(UnrecognizedEntryException e){} // Exception never thrown
    }

    /**
     * @return a text with all the cells in the cutbuffer
     */
    public String showCutBuffer(){
        String text = "";
        if(_cutbuffer.isLine()){
            for(int i = 1; i <= _cutbuffer.getSize(); i++){
                text += "1;" + i + "|" + _cutbuffer.getCell(i-1).contentToString();
                if(i != _cutbuffer.getSize()){ text += "\n"; }
            }
        }
        else{
            for(int i = 1; i <= _cutbuffer.getSize(); i++){
                text += i + ";1|" + _cutbuffer.getCell(i-1).contentToString();
                if(i != _cutbuffer.getSize()){ text += "\n"; }
            }
        }
        return text;
    }

    
    /**
     * @param value to be searched
     * 
     * @return text with all the cells whose contents have the given value
     */
    public String searchValue(String value){
        int numLines = _cells.getNumOfLines();
        int numColumns = _cells.getNumOfColumns();

        String text = "";
        boolean textEmpty = true;

        Visitor visitor = new SearchValue();

        for(int l = 1; l <= numLines; l++){
            for(int c = 1; c <= numColumns; c++){
                Content cellContent = _cells.getCell(l + ";" + c).getContent();
                String cellValue = "";

                if(cellContent != null) cellValue = cellContent.accept(visitor);

                if(cellValue.equals(value)){
                    if(textEmpty) textEmpty = false;
                    else text += "\n";

                    text += l+";"+c+"|"+cellValue;
                }
            }
        }
        return text;
    }

    public String searchEvenValue(){
        int numLines = _cells.getNumOfLines();
        int numColumns = _cells.getNumOfColumns();

        String text = "";
        boolean textEmpty = true;

        Visitor visitor = new SearchLiteralIntValue();

        for(int l = 1; l <= numLines; l++){
            for(int c = 1; c <= numColumns; c++){
                Content cellContent = _cells.getCell(l + ";" + c).getContent();
                int cellValue = 1;

                if(cellContent != null){
                    try{
                        cellValue = Integer.parseInt(cellContent.accept(visitor));

                        if(cellValue % 2 == 0){
                            if(textEmpty) textEmpty = false;
                            else text += "\n";

                            text += l+";"+c+"|"+cellValue;
                        }
                    } catch(NumberFormatException e){}
                }
            }
        }
        return text;
    }

    public String searchReference(){
        int numLines = _cells.getNumOfLines();
        int numColumns = _cells.getNumOfColumns();

        String text = "";
        boolean textEmpty = true;

        Visitor visitor = new SearchReference();

        for(int l = 1; l <= numLines; l++){
            for(int c = 1; c <= numColumns; c++){
                Cell cell = _cells.getCell(l + ";" + c);
                Content cellContent = cell.getContent();
                String refString = "";

                if(cellContent != null) refString = cellContent.accept(visitor);

                if(refString != ""){
                    if(textEmpty) textEmpty = false;
                    else text += "\n";

                    text += l+";"+c+"|"+refString;
                }
            }
        }
        return text;
    }

    /**
     * @param functionName to be searched. It can be a segment of a name
     * 
     * @return text with all the cells (alphabetically ordered first and then 
     * by the spreadsheet order) whose content is a function that has the given
     * function name.
     */
    public String searchFunction(String functionName){
        int numLines = _cells.getNumOfLines();
        int numColumns = _cells.getNumOfColumns();

        String text = "";
        boolean textEmpty = true;
        ArrayList<String> textList = new ArrayList<String>();

        Visitor visitor = new SearchFunctionName();

        for(int l = 1; l <= numLines; l++){
            for(int c = 1; c <= numColumns; c++){
                Cell cell = _cells.getCell(l + ";" + c);
                Content cellContent = cell.getContent();

                if(cellContent != null && cellContent.accept(visitor).contains(functionName)){    
                    textList.add(l+";"+c+"|"+cell.contentToString());
                }
            }
        }

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String funcName1 = s1.split("=")[1].split("\\(")[0];
                String funcName2 = s2.split("=")[1].split("\\(")[0];
                
                // Compares alphabetically first...
                int alfabeticOrderResult = funcName1.compareTo(funcName2);
                if (alfabeticOrderResult != 0)
                    return alfabeticOrderResult;

                int[] cell1 = getCellLineColumn(s1.split("\\|")[0]);
                int[] cell2 = getCellLineColumn(s2.split("\\|")[0]);
                
                // ...then by line...
                if (cell1[0] != cell2[0])
                    return Integer.compare(cell1[0], cell2[0]);
                
                //..and then by column
                return Integer.compare(cell1[1], cell2[1]);
            }
        };

        Collections.sort(textList, comparator);
        
        // Building the string
        for(String s : textList){
            if(textEmpty) textEmpty = false;
            else text += "\n";

            text += s;
        }
        
        return text;
    }


    /**
     * @param gamma
     * 
     * @return array list with all the addresses of the given gamma in order
     * 
     * @throws CellOutOfRangeException
     */
    public ArrayList<String> createAddressInterval(String gamma) throws CellOutOfRangeException{
        ArrayList<String> interval = new ArrayList<>();

        String[] addressArray = gamma.split(":");
        
        if(!isCellInRange(addressArray[0])) throw new CellOutOfRangeException();

        if(addressArray.length == 1)
            interval.add(addressArray[0]);
        else{
            if(!isCellInRange(addressArray[1])) throw new CellOutOfRangeException();

            int[] cell1 = getCellLineColumn(addressArray[0]);
            int[] cell2 = getCellLineColumn(addressArray[1]);

            // if lines are the same, loop throught the columns 
            if(cell1[0] == cell2[0]){
                int start = smallestNum(cell1[1], cell2[1]);
                int end = highestNum(cell1[1], cell2[1]);
                for(int i = start; i <= end; i++)
                    interval.add(cell1[0] + ";" + i);
            }
            // if columns are the same, loop throught the lines 
            else if(cell1[1] == cell2[1]){
                int start = smallestNum(cell1[0], cell2[0]);
                int end = highestNum(cell1[0], cell2[0]);
                for(int i = start; i <= end; i++)
                    interval.add(i + ";" + cell1[1]);
            }
            else throw new CellOutOfRangeException();
        }
        return interval;
    }

    /**
     * Calculates the smallest of 2 numbers
     * 
     * @param x First number
     * @param y Second number
     * 
     * @return smallest of the 2 numbers
     */
    public int smallestNum(int x, int y){
        if(x > y) return y;
        return x;
    }

    /**
     * Calculates the highest of 2 numbers
     * 
     * @param x First number
     * @param y Second number
     * 
     * @return highest of the 2 numbers
     */
    public int highestNum(int x, int y){
        if(x <= y) return y;
        return x;
    }

    /**
     * @param address Cell's address
     * 
     * @return Array with the line and column of a cell as int
     */
    public int[] getCellLineColumn(String address){
        String[] addressArray = address.split(";");
        int[] cellArray = {Integer.parseInt(addressArray[0]),Integer.parseInt(addressArray[1])};
        return cellArray;
    }

    /**
     * Creates a new content which is a valid argument for a Binary Function
     * 
     * @param strArg argument in string
     * 
     * @return a new content (it can be a Reference or a LiteralInt)
     */
    public Content newBinaryFuncArg(String strArg){
        if(isCellInRange(strArg)){
            return new Reference(_cells.getCell(strArg), strArg);
        }
        return new LiteralInt(Integer.parseInt(strArg));
    }

    /**
     * Checks if a given String represents a cell and if that cell is in the spreadsheet range.
     * 
     * @param str String to check
     * 
     * @return does the string represent a cell in the spreadsheet's range?
     */
    public boolean isCellInRange(String str){
        String[] addressArray = str.split(";");

        if(addressArray.length == 2){
            try{
                int line = Integer.parseInt(addressArray[0]);
                int column = Integer.parseInt(addressArray[1]);

                return 0 < line && line <= _cells.getNumOfLines() && 0 < column && column <= _cells.getNumOfColumns();
            }
            catch(NumberFormatException e){ return false; }
        }
        return false;
    }

    /**
     * @param gamma
     * 
     * @return is the given gamma a line?
     */
    public boolean isIntervalLine(String gamma){
        String[] addressArray = gamma.split(":");
        if(addressArray.length == 1) 
            return true;

        int cell1Line = getCellLineColumn(addressArray[0])[0];
        int cell2Line = getCellLineColumn(addressArray[1])[0];

        return cell1Line == cell2Line;
    }

    /**
     * @param user
     */
    public void addUser(User user){
        _users.add(user);
    }

    /**
     * @return has unsaved changes?
     */
    public boolean hasUnsavedChanges(){
        return _hasUnsavedChanges;
    }

    /**
     * @param state Spreadsheet's unsaved changes
     */
    public void setHasUnsavedChanges(boolean state){
        _hasUnsavedChanges = state;
    }
}
