package xxl.content.functions;

import java.util.ArrayList;
import xxl.content.Reference;
import xxl.Visitor;

/**
 * Class for the Interval Function Concat
 */
public class Concat extends IntervalFunction{
  /** Function value */
  private String _value;


  /**
   * Constructor.
   */
  public Concat(ArrayList<Reference> interval, String expression){
    super(interval, expression);
  }

 
  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitConcat(this);
  }

  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    if(!isValueUpdated()){
      _value = "'";

      for(int i=0; i < getIntervalSize(); i++){
        String refValue = getReferenceValue(i);
        if(refValue != "" && refValue.charAt(0) == '\'')
          _value += refValue.substring(1);
      }

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