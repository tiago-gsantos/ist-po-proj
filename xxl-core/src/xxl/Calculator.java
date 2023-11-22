package xxl;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import xxl.exceptions.*;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {

    /** The current spreadsheet. */
    private Spreadsheet _spreadsheet = null;

    /** The file associated to the current spreadsheet */
    private String _filename = null;

    /** Active user. The default is the user named root */
    private User _activeUser = new User("root");

    /** Map of the application user */
    private HashMap<String, User> _users = new HashMap<String, User>();
    

    /**
     * Saves the serialized application's state into the file associated to the current spreadsheet.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
     * @throws MissingFileAssociationException if the current spreadsheet does not have a file.
     * @throws IOException if there is some error while serializing the state of the spreadsheet to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if(_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))){
            _spreadsheet.setHasUnsavedChanges(false);
            oos.writeObject(_spreadsheet);
        }
    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        setFilename(filename);
        save();
    }

    /**
     * Opens given file and loads serialized application's state.
     * 
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        setFilename(filename);
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)))){
            _spreadsheet = (Spreadsheet) ois.readObject();
        }
        catch(IOException | ClassNotFoundException e){throw new UnavailableFileException(filename);}
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int lines = Integer.parseInt(reader.readLine().split("=")[1]);
            int columns = Integer.parseInt(reader.readLine().split("=")[1]);
            
            _spreadsheet = new Spreadsheet(lines, columns);
            _activeUser.addSpreadsheet(_spreadsheet);

            String line;
            while((line = reader.readLine()) != null && !line.isEmpty()){
                String[] args = line.split("\\|");
                if(args.length == 2){
                    _spreadsheet.insertContents(args[0], args[1]);
                }
            }
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        } catch (CellOutOfRangeException e){} // Exception never thrown ?
    }

    /**
     * @param userName name of the user we want to set active
     */
    public void setActiveUser(String userName){
        if(_users.size() > 0)
            _activeUser = _users.get(userName);
    }

    /**
     * @param user to be added to the map of users
     */
    public void addUser(User user){
        _users.put(user.getName(), user);
    }


    /**
     * Inicializes a spreadsheet. Creates a new one, associates it with the 
     * active user and sets the filename to null.
     * 
     * @param lines num of lines of the spreadsheet
     * @param columns num os columns of the spreadsheet
     */
    public void initializeSpreadsheet(int lines, int columns){
        _spreadsheet = new Spreadsheet(lines, columns);
        _activeUser.addSpreadsheet(_spreadsheet);
        _filename = null;
    }

    /**
     * @return spreadsheet
     */
    public Spreadsheet getSpreadsheet() {
        return _spreadsheet;
    }

    /**
     * @return Calculator has a spreadsheet?
     */
    public boolean hasSpreadsheet() {
        return _spreadsheet != null;
    }

    /**
     * @return filename
     */
    public String getFilename(){
        return _filename;
    }

    /**
     * Sets the given file as the file associated to the current network with the correct extension
     * 
     * @param filename the name of the file
     */
    public void setFilename(String filename){
        if(filename != null) _filename = filename + ".dat";
    }

    /**
     * @return spreadsheet has unsaved changes?
     */
    public boolean spreadsheetChanged(){
        return _spreadsheet.hasUnsavedChanges();
    }
}
