import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterAccountWithAnnotation_balance implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    int v = Integer.parseInt(tokens.popWordPrimitive());
    ((pt.isel.AccountWithAnnotation) target).setBalance(v);
  }
}
