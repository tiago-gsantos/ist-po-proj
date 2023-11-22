package xxl;

import xxl.content.*;
import xxl.content.functions.*;

public class SearchReference implements Visitor{
  public String visitLiteralInt(LiteralInt c){return "";}
  public String visitLiteralString(LiteralString c){return "";}
  public String visitReference(Reference c){return c.toString();}
  public String visitAdd(Add c){return "";}
  public String visitSub(Sub c){return "";}
  public String visitMul(Mul c){return "";}
  public String visitDiv(Div c){return "";}
  public String visitAverage(Average c){return "";}
  public String visitProduct(Product c){return "";}
  public String visitConcat(Concat c){return "";}
  public String visitCoalesce(Coalesce c){return "";}
}