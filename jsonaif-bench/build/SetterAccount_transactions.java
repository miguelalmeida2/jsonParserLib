import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterAccount_transactions implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    java.util.List<pt.isel.Transaction> v = (java.util.List<pt.isel.Transaction>) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.Transaction.class));
    ((pt.isel.Account) target).setTransactions(v);
  }
}
