package xxl.content;

import xxl.Visitor;
import xxl.Cell;

/**
 * Type of content for strings
 */
public class LiteralString extends Content{
  
  /** LiteralString's value */
  private String _value;


  /**
   * Constructor.
   * 
   * @param value LiteralString's value
   */
  public LiteralString(String value){
    _value = value;
  }

  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitLiteralString(this);
  }

  /** @see xxl.content.Content#addCellAsObserver() */
  @Override
  public void addCellAsObserver(Cell cell){}

  /** @see xxl.content.Content#removeCellAsObserver() */
  @Override
  public void removeCellAsObserver(Cell cell){}

  /** @see xxl.content.Content#valueChanged() */
  @Override
  public void valueChanged(){}

  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    return _value;
  }

  /** @see java.lang.Object#toString() */
  @Override
  public String toString(){
    return _value;
  }
}