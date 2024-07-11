import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPrimitives_double implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    double v = Double.parseDouble(tokens.popWordPrimitive());
    ((pt.isel.sample.Primitives) target).setDouble(v);
  }
}
