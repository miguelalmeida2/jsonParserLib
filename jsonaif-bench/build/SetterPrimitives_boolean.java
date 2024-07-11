import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPrimitives_boolean implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    boolean v = Boolean.parseBoolean(tokens.popWordPrimitive());
    ((pt.isel.Primitives) target).setBoolean(v);
  }
}
