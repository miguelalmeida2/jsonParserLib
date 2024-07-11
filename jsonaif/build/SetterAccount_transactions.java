import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterAccount_transactions implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    java.util.List<pt.isel.sample.Transaction> v = (java.util.List<pt.isel.sample.Transaction>) pt.isel.JsonParserDynamic.INSTANCE.parseArray(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.sample.Transaction.class));
    ((pt.isel.sample.Account) target).setTransactions(v);
  }
}
