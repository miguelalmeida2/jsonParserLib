import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterStudent_nr implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    int v = Integer.parseInt(tokens.popWordPrimitive());
    ((pt.isel.sample.Student) target).setNr(v);
  }
}
