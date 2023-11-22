package xxl;

import xxl.content.*;
import xxl.content.functions.*;

/** Interface for the Visitor design pattern. */
public interface Visitor{
  public String visitLiteralInt(LiteralInt c);
  public String visitLiteralString(LiteralString c);
  public String visitReference(Reference c);
  public String visitAdd(Add c);
  public String visitSub(Sub c);
  public String visitMul(Mul c);
  public String visitDiv(Div c);
  public String visitAverage(Average c);
  public String visitProduct(Product c);
  public String visitConcat(Concat c);
  public String visitCoalesce(Coalesce c);
}