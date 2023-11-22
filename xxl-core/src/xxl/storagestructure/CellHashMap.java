package xxl.storagestructure;

import xxl.Cell;

import java.util.Map;
import java.util.HashMap;

/**
 * Class representing a HashMap of cells
 */
public class CellHashMap extends StorageStructure{
  
  /** HashMap of cells */
  private Map<String, Cell> _cells = new HashMap<String, Cell>(getNumOfLines()*getNumOfColumns());


  /**
   * Constructor. Initializes, in a loop, all the cells of the HashMap, with content null and with 
   * the corresponding address as the key in the map.
   * 
   * @param lines number of lines
   * @param columns number of columns
   */
  public CellHashMap(int lines, int columns){
    super(lines, columns);

    for(int l = 1; l <= lines; l++){
      for(int c = 1; c <= columns; c++){
        _cells.put(l + ";" + c, new Cell());
      }
    }
  }

  /** @see xxl.storagestructure.StorageStructure#getCell(String address) */
  @Override
  public Cell getCell(String address){
    return _cells.get(address);
  }

  /** @see xxl.storagestructure.StorageStructure#insertCell(String address) */
  @Override
  public void insertCell(Cell cell, String address){
    _cells.put(address, cell);
  }
}