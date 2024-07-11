import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPrimitives_float implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    float v = Float.parseFloat(tokens.popWordPrimitive());
    ((pt.isel.Primitives) target).setFloat(v);
  }
}
