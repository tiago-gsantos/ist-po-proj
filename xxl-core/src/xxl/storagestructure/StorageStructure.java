package xxl.storagestructure;

import xxl.Cell;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class for ways of storing cells
 */
public abstract class StorageStructure implements Serializable{

  @Serial
	private static final long serialVersionUID = 202308312359L;

  /** Storage number of lines */
  private int _numLines;

  /** Storage number of columns */
  private int _numColumns;


  /**
   * Constructor.
   * 
   * @param lines storage number of lines
   * @param columns storage number of columns
   */
  public StorageStructure(int lines, int columns){
    _numLines = lines;
    _numColumns = columns;
  }

  /**
   * @return numLines
   */
  public int getNumOfLines(){
    return _numLines;
  }

  /**
   * @return numColumns
   */
  public int getNumOfColumns(){
    return _numColumns;
  }

  /**
   * @param address Cell address
   * 
   * @return the cell on the given address
   */
  public abstract Cell getCell(String address);

  /**
   * @param cell Cell we want to insert
   * 
   * @param address where we want to insert
   */
  public abstract void insertCell(Cell cell, String address);
}