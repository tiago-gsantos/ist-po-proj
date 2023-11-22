package xxl.content.functions;

import xxl.content.Content;
import xxl.Cell;

/**
 * Class for all Binary Functions
 */
public abstract class BinaryFunction extends Function{
  
  /** Array with two arguments of type content (LiteralInt or Reference) */
  private Content[] _args = new Content[2];


  /**
   * Constructor.
   * 
   * @param arg1 First argument (LiteralInt or Reference)
   * @param arg2 Second argument (LiteralInt or Reference)
   */
  public BinaryFunction(Content arg1, Content arg2, String expression){
    super(expression);
    _args[0] = arg1;
    _args[1] = arg2;
  }

  /**
   * @param i argument's index
   * 
   * @return Argument's value
   */
  public String getArgValue(int i){
    return _args[i].getValue();
  }

  /** @see xxl.content.Content#valueChanged() */
  @Override
  public void valueChanged(){
    setIsValueUpdated(false);
    _args[0].valueChanged();
    _args[1].valueChanged();
  }

  /** @see xxl.content.Content#addCellAsObserver() */
  @Override
  public void addCellAsObserver(Cell cell){
    _args[0].addCellAsObserver(cell);
    _args[1].addCellAsObserver(cell);
  }

  /** @see xxl.content.Content#removeCellAsObserver() */
  @Override
  public void removeCellAsObserver(Cell cell){
    _args[0].removeCellAsObserver(cell);
    _args[1].removeCellAsObserver(cell);
  }
}