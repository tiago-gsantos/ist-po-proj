package xxl;

import xxl.content.*;
import xxl.content.functions.*;

/** Visitor class to get the name of a function */
public class SearchFunctionName implements Visitor{
  public String visitLiteralInt(LiteralInt c){return "";}
  public String visitLiteralString(LiteralString c){return "";}
  public String visitReference(Reference c){return "";}
  public String visitAdd(Add c){return "ADD";}
  public String visitSub(Sub c){return "SUB";}
  public String visitMul(Mul c){return "MUL";}
  public String visitDiv(Div c){return "DIV";}
  public String visitAverage(Average c){return "AVERAGE";}
  public String visitProduct(Product c){return "PRODUCT";}
  public String visitConcat(Concat c){return "CONCAT";}
  public String visitCoalesce(Coalesce c){return "COALESCE";}
}