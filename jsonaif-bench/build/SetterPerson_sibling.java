import java.lang.Object;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterPerson_sibling implements Setter {
  public void apply(Object target, JsonTokens tokens) {
    pt.isel.Person v = (pt.isel.Person) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.Person.class));
    ((pt.isel.Person) target).setSibling(v);
  }
}
