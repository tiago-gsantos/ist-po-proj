package xxl;

/** Interface for the Observer design pattern. */
public interface Observable{
  public void addObserver(Observer observer);
  public void removeObserver(Observer observer);
  public void notifyObservers();
}