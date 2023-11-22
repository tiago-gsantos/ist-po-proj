package xxl;

import xxl.content.*;
import xxl.content.functions.*;

/** Visitor class to get a content's value */
public class SearchValue implements Visitor{
  public String visitLiteralInt(LiteralInt c){return c.getValue();}
  public String visitLiteralString(LiteralString c){return c.getValue();}
  public String visitReference(Reference c){return c.getValue();}
  public String visitAdd(Add c){return c.getValue();}
  public String visitSub(Sub c){return c.getValue();}
  public String visitMul(Mul c){return c.getValue();}
  public String visitDiv(Div c){return c.getValue();}
  public String visitAverage(Average c){return c.getValue();}
  public String visitProduct(Product c){return c.getValue();}
  public String visitConcat(Concat c){return c.getValue();}
  public String visitCoalesce(Coalesce c){return c.getValue();}
}