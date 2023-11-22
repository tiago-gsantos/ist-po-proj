package xxl.content;

import xxl.Visitor;
import xxl.Cell;

/**
 * Type of content for integers
 */
public class LiteralInt extends Content{
  /** LiteralInt's value */
  private int _value;


  /**
   * Constructor.
   * 
   * @param value LiteralInt's value
   */
  public LiteralInt(int value){
    _value = value;
  }

  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitLiteralInt(this);
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
    return _value + "";
  }

  /** @see java.lang.Object#toString() */
  @Override
  public String toString(){
    return _value + "";
  }
}