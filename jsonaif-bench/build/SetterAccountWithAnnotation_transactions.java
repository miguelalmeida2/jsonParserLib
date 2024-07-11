import java.lang.Object;
import pt.isel.Formatter;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterAccountWithAnnotation_transactions implements Setter {
  private final Formatter f;

  public SetterAccountWithAnnotation_transactions(Formatter f) {
    this.f = f;
  }

  public void apply(Object target, JsonTokens tokens) {
    java.util.List<pt.isel.Transaction> v = (java.util.List<pt.isel.Transaction>) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.Transaction.class)));
    ((pt.isel.AccountWithAnnotation) target).setTransactions(v);
  }
}
