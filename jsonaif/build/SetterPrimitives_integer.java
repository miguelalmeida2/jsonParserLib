import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPrimitives_integer implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    int v = Integer.parseInt(tokens.popWordPrimitive());
    ((pt.isel.sample.Primitives) target).setInteger(v);
  }
}
