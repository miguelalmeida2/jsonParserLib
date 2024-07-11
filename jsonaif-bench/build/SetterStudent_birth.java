import java.lang.Object;
import pt.isel.Formatter;
import pt.isel.JsonTokens;
import pt.isel.Setter;

public class SetterStudent_birth implements Setter {
  private final Formatter f;

  public SetterStudent_birth(Formatter f) {
    this.f = f;
  }

  public void apply(Object target, JsonTokens tokens) {
    pt.isel.Date v = (pt.isel.Date) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.Date.class)));
    ((pt.isel.Student) target).setBirth(v);
  }
}
