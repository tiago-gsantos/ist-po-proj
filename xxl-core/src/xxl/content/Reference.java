package xxl.content;

import xxl.Cell;
import xxl.Visitor;

/**
 * Type of content for strings
 */
public class Reference extends Content{
  
  /** Reference's value */
  private Cell _cell;

  /** String with the reference expression (referenced cell) */
  private String _expression;

  /** Reference value */
  private String _value;

  /** Is Reference value updated? */
  private boolean _isValueUpdated = false;


  /**
   * Constructor.
   * 
   * @param value Reference's value
   * @param expression String with the reference expression
   */
  public Reference(Cell cell, String expression){
    _cell = cell;
    _expression = expression;
  }

  /** @see xxl.content.Content#valueChanged() */
  @Override
  public void valueChanged(){
    _isValueUpdated = false;
  }

  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitReference(this);
  }

  /** @see xxl.content.Content#addCellAsObserver() */
  @Override
  public void addCellAsObserver(Cell cell){
    _cell.addObserver(cell);
  }

  /** @see xxl.content.Content#removeCellAsObserver() */
  @Override
  public void removeCellAsObserver(Cell cell){
    _cell.removeObserver(cell);
  }

  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    if(!_isValueUpdated){
      String value = _cell.getValue(); 

      if(value == "") _value = "#VALUE";
      else _value = value;

      _isValueUpdated = true;
    }
    return _value;
  }

  /** @see java.lang.Object#toString() */
  @Override
  public String toString(){
    return getValue() + "=" + _expression;
  }
}