package xxl.content.functions;

import xxl.content.Content;

/**
 * Class for all Functions
 */
public abstract class Function extends Content{
  /** String with the function expression (name of function + args) */
  private String _expression;

  /** Is function value updated */
  private boolean _isValueUpdated = false;

  /**
   * Constructor.
   * 
   * @param expression String with the function expression
   */
  public Function(String expression){
    _expression = expression;
  }

  /**
   * @return expression
   */
  public String getExpression(){
    return _expression;
  }

  /**
   * @return is function value updated?
   */
  public boolean isValueUpdated(){
    return _isValueUpdated;
  }

  /**
   * @param state of the value
   */
  public void setIsValueUpdated(boolean state){
    _isValueUpdated = state;
  }
}