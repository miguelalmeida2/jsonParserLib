import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterStudent_name implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    java.lang.String v = (java.lang.String) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(java.lang.String.class));
    ((pt.isel.sample.Student) target).setName(v);
  }
}
