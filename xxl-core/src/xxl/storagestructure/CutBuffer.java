package xxl.storagestructure;

import xxl.Cell;

import java.util.ArrayList;

/**
 * Class representing the CutBuffer
 */
public class CutBuffer extends StorageStructure{
  
  /** ArrayList of cells */
  private ArrayList<Cell> _cells = new ArrayList<Cell>();

  /** True if it's a line and false if it's a column */
  private boolean _isLine;

  /**
   * Constructor.
   * 
   * @param lines max number of lines
   * @param columns max number of columns
   * @param isLine is Line?
   */
  public CutBuffer(int lines, int columns, boolean isLine){
    super(lines, columns);
    _isLine = isLine;
  }

  /**
   * Creates a new cell with the content from the given cell and inserts it in
   * the ArrayList.
   * 
   * @param cell we want to insert
   */
  public void insertCell(Cell cell){
    Cell cellCopy = new Cell(cell.getContent());
    _cells.add(cellCopy);
  }

  /**
   * @return ArrayList size
   */
  public int getSize(){
    return _cells.size();
  }

  /**
   * @return is Line?
   */
  public boolean isLine(){
    return _isLine;
  }

  /**
   * @param i position of the cell we want in the ArrayList
   * 
   * @return cell in the position i
   */
  public Cell getCell(int i){
    return _cells.get(i);
  }

  /** @see xxl.storagestructure.StorageStructure#getCell() */
  @Override
  public Cell getCell(String address){
    return null;
  }

  /** @see xxl.storagestructure.StorageStructure#insertCell() */
  @Override
  public void insertCell(Cell cell, String address){}
}