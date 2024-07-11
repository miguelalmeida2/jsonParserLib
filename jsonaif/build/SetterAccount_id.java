import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterAccount_id implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    int v = Integer.parseInt(tokens.popWordPrimitive());
    ((pt.isel.sample.Account) target).setId(v);
  }
}
