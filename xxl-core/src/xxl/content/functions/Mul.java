package xxl.content.functions;

import xxl.content.Content;
import xxl.Visitor;

/**
 * Class for the Binary Function Mul
 */
public class Mul extends BinaryFunction{
  /** Function value */
  private String _value;

  
  /**
   * Constructor.
   */
  public Mul(Content arg1, Content arg2, String expression){
    super(arg1, arg2, expression);
  }

  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitMul(this);
  }

  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    if(!isValueUpdated()){
      try{
        _value = Integer.parseInt(getArgValue(0)) * Integer.parseInt(getArgValue(1)) + "";
      }
      catch(NumberFormatException e){_value = "#VALUE";}

      setIsValueUpdated(true);
    }
    return _value;
  }
  
  /** @see java.lang.Object#toString() */
  @Override
  public String toString(){
    return getValue() + "=" + getExpression();
  }
}