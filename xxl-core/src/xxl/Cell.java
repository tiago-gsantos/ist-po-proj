package xxl;

import java.io.Serial;
import java.io.Serializable;

import xxl.content.Content;
import java.util.ArrayList;

/**
 * Class representing a cell.
 */
public class Cell implements Serializable, Observer, Observable{

  @Serial
  private static final long serialVersionUID = 202308312359L;

  /** Cell's content */
  private Content _content;

  /** List with the observers of this cell */
  private ArrayList<Observer> _observerList = new ArrayList<Observer>();


  /**
   * Constructor. When ommited, content is null.
   */
  public Cell(){
    this(null);
  }

  /**
   * Constructor.
   * 
   * @param content cell content
   */
  public Cell(Content content){
    _content = content;
  }

  /**
   * @return cell content
   */
  public Content getContent(){
    return _content;
  }

  /**
   * Sets the content to a new one. Removes itself from the observer's List of
   * the old content (if its Reference or Function), adds itself to the observer's
   * list of the new content and notifies all observers of this change. 
   * 
   * @param content Cell's content
   */
  public void setContent(Content content){
    if(_content != null) _content.removeCellAsObserver(this);
    _content = content;
    if(_content != null) _content.addCellAsObserver(this);
    notifyObservers();
  }

  /** @see xxl.Observable#addObserver() */
  @Override
  public void addObserver(Observer o){
    _observerList.add(o);
  } 

  /** @see xxl.Observable#removeObserver() */
  @Override
  public void removeObserver(Observer o){
    _observerList.remove(o);
  }

  /** @see xxl.Observable#notifyObservers() */
  @Override
  public void notifyObservers(){
    for(Observer o : _observerList){
      o.update();
    }
  }

  /** @see xxl.Observer#update() */
  @Override
  public void update(){
    _content.valueChanged();
    notifyObservers();
  }

  /**
   * @return cell content's value as a String. Empty when content is null
   */
  public String getValue(){
    if(_content != null)
      return _content.getValue();
    return "";
  }

  /**
   * @return cell content as a String.
   */
  public String contentToString(){
    if(_content != null)
      return _content.toString();
    return "";
  }
}