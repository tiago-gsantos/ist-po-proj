package xxl.content.functions;

import java.util.ArrayList;
import xxl.content.Reference;
import xxl.Visitor;

/**
 * Class for the Interval Function Product
 */
public class Product extends IntervalFunction{
  /** Function value */
  private String _value;

  /**
   * Constructor.
   */
  public Product(ArrayList<Reference> interval, String expression){
    super(interval, expression);
  }


  /** @see xxl.content.Content#accept() */
  @Override
  public String accept(Visitor v){
    return v.visitProduct(this);
  }


  /** @see xxl.content.Content#getValue() */
  @Override
  public String getValue(){
    if(!isValueUpdated()){
      try{
        int result = 1;

        for(int i=0; i < getIntervalSize(); i++){
          result *= Integer.parseInt(getReferenceValue(i));
        }

        _value = result + "";
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