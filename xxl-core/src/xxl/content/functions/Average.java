package xxl.content.functions;

import java.util.ArrayList;
import xxl.content.Reference;
import xxl.Visitor;

/**
 * Class for the Interval Function Average
 */
public class Average extends IntervalFunction{
  /** Function value */
  private String _value;

  /**
   * Constructor.
   */
  public Average(ArrayList<Reference> interval, String expression){
    super(interval, expression);
  }

  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitAverage(this);
  }


  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    if(!isValueUpdated()){
      try{
        int result = 0;
        int intervalSize = getIntervalSize();

        for(int i=0; i < intervalSize; i++){
          result += Integer.parseInt(getReferenceValue(i));
        }
        _value = result / intervalSize + "";
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