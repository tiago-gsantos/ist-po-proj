package xxl.content.functions;

import xxl.content.Reference;
import xxl.Cell;

import java.util.ArrayList;

/**
 * Class for all Interval Functions
 */
public abstract class IntervalFunction extends Function{
  
  /**  Array with all the references of the interval passed as the function argument */
  private ArrayList<Reference> _interval;


  /**
   * Constructor.
   * 
   * @param interval Array with all the references of the interval passed as the function argument
   */
  public IntervalFunction(ArrayList<Reference> interval, String expression){
    super(expression);
    _interval = interval;
  }


  /**
   * @param i argument's index
   * 
   * @return Argument's value
   */
  public String getReferenceValue(int i){
    return _interval.get(i).getValue();
  }

  /**
   * @return interval size
   */
  public int getIntervalSize(){
    return _interval.size();
  }
  
  /** @see xxl.content.Content#valueChanged() */
  @Override
  public void valueChanged(){
    setIsValueUpdated(false);
    for(Reference ref : _interval)
      ref.valueChanged();
  }

  /** @see xxl.content.Content#addCellAsObserver() */
  @Override
  public void addCellAsObserver(Cell cell){
    for(Reference ref : _interval)
      ref.addCellAsObserver(cell);
  }

  /** @see xxl.content.Content#removeCellAsObserver() */
  @Override
  public void removeCellAsObserver(Cell cell){
    for(Reference ref : _interval)
      ref.removeCellAsObserver(cell);
  }
}