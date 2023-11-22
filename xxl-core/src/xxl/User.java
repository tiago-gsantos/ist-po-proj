package xxl;

import java.util.ArrayList;

/** Class for the user */
public class User{
  /** User name */
  private String _name;

  /** ArrayList of the user's spreadsheets */
  private ArrayList<Spreadsheet> _spreadsheets = new ArrayList<Spreadsheet>();

  /**
   * Constructor.
   * 
   * @param name
   */
  public User(String name){
    _name = name;
  }

  /**
   * @return user name
   */
  public String getName(){
    return _name;
  }

  /**
   * Adds given spreadsheet to the ArrayList of spreadsheets
   * 
   * @param spreadsheet
   */
  public void addSpreadsheet(Spreadsheet spreadsheet){
    _spreadsheets.add(spreadsheet);
  }
}