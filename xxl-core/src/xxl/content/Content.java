package xxl.content;

import java.io.Serial;
import java.io.Serializable;

import xxl.Visitor;
import xxl.Cell;

/**
 * Class representing the Content of a cell
 */
public abstract class Content implements Serializable{

  @Serial
  private static final long serialVersionUID = 202308312359L;
  
  /**
   * @return the content's value as a string
   */
  public abstract String getValue();

  /**
   * Calls the specific content's visit method according to a Visitor passed as argument
   * 
   * @param v Visitor
   * 
   * @return String with a result depending on the Visitor passed
   */
  public abstract String accept(Visitor v);

  /**
   * Calls the same method until it finds a Reference, which adds the cell passed
   * as argument to the list of Observers of the cell referenced by the reference,
   * or until it finds a Literal wi«hich will do nothing.
   * 
   * @param cell to be added to the ObserverList
   */
  public abstract void addCellAsObserver(Cell cell);

  /**
   * Same as addCellAsObserver but removes cell from the ObserverList
   * 
   * @param cell to be removed from the ObserverList
   */
  public abstract void removeCellAsObserver(Cell cell);

  /**
   * For the types of content that have a valueUpdated state, set it to false. 
   * For the others, do nothing.
   */
  public abstract void valueChanged();
}