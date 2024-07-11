import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPerson_sibling implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    pt.isel.sample.Person v = (pt.isel.sample.Person) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.sample.Person.class));
    ((pt.isel.sample.Person) target).setSibling(v);
  }
}
