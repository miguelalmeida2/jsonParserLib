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
    pt.isel.sample.Date v = (pt.isel.sample.Date) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(pt.isel.sample.Date.class)));
    ((pt.isel.sample.Student) target).setBirth(v);
  }
}
