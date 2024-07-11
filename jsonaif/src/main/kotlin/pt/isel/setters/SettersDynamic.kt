package pt.isel.setters

import com.squareup.javapoet.*
import pt.isel.Formatter
import pt.isel.JsonTokens
import pt.isel.Setter
import pt.isel.refType
import java.util.*
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KTypeProjection
import kotlin.reflect.jvm.javaType


/**
   public class SetterStudent_name implements Setter {
       public void apply (Object target, JsonTokens tokens) {
            String v = (String) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, JvmClassMappingKt.getKotlinClass(String.class));
            ((Student) target).setName(v);
       }
   }
 **/
fun buildSetterPropertyDynamic(domainKlass: KClass<*>, prop: KProperty<*>): JavaFile {
    val apply = MethodSpec
        .methodBuilder("apply")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Object::class.java, "target")
        .addParameter(JsonTokens::class.java, "tokens")
        .addStatement(
            "${prop.returnType.javaType.typeName} v = (${prop.returnType.javaType.typeName}) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(${prop.returnType.javaType.typeName}.class))")
        .addStatement("((${domainKlass.qualifiedName}) target).set${capitalizeFirstLetter(prop.name)}(v)")
        .build()

    val setter = TypeSpec
        .classBuilder("Setter${domainKlass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Setter::class.java)
        .addMethod(apply)
        .build()

    return JavaFile
        .builder("", setter)
        .build()
}

/**
    public class SetterStudent_birth implements Setter {
        private final Formatter f;

        SetterStudent_birth (Formatter f) {
            this.f = f;
        }

        public void apply (Object target, JsonTokens tokens) {
            Date v = (Date) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, JvmClassMappingKt.getKotlinClass(Date.class)));
            ((Student) target).setBirth(v);
        }
    }
 */

fun buildSetterPropertyDynamicAnno(domainKlass: KClass<*>, prop: KProperty<*>): JavaFile {
    val fmt = FieldSpec.builder(Formatter::class.java, "f")
        .addModifiers(Modifier.FINAL, Modifier.PRIVATE)
        .build()

    val ctor = MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Formatter::class.java, "f")
        .addStatement("this.\$N = f", fmt)
        .build()

    val apply = MethodSpec
        .methodBuilder("apply")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Object::class.java, "target")
        .addParameter(JsonTokens::class.java, "tokens")
        .addStatement(
            "${prop.returnType.javaType.typeName} v = (${prop.returnType.javaType.typeName}) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(${prop.returnType.javaType.typeName}.class)))")
        .addStatement("((${domainKlass.qualifiedName}) target).set${capitalizeFirstLetter(prop.name)}(v)")
        .build()

    val setter = TypeSpec
        .classBuilder("Setter${domainKlass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Setter::class.java)
        .addField(fmt)
        .addMethod(ctor)
        .addMethod(apply)
        .build()

    return JavaFile
        .builder("", setter)
        .build()
}

/**
public class SetterAccount_transactions implements Setter {
    public void apply (Object target, JsonTokens tokens) {
        List<Transaction> v = (List<Transaction>) pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, JvmClassMappingKt.getKotlinClass(Transaction.class));
        ((Account) target).setTransactions(v);
    }
}
 */

fun buildSetterPropertyDynamicList(domainKlass: KClass<*>, prop: KProperty<*>): JavaFile {
    val listType: KTypeProjection = prop.returnType.arguments[0] // Tipo de dados na lista

    val apply = MethodSpec
        .methodBuilder("apply")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Object::class.java, "target")
        .addParameter(JsonTokens::class.java, "tokens")
        .addStatement(
            "java.util.List<${listType}> v = (java.util.List<${listType}>) pt.isel.JsonParserDynamic.INSTANCE.parseArray(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(${listType}.class))")
        .addStatement("((${domainKlass.qualifiedName}) target).set${capitalizeFirstLetter(prop.name)}(v)")
        .build()

    val setter = TypeSpec
        .classBuilder("Setter${domainKlass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Setter::class.java)
        .addMethod(apply)
        .build()

    return JavaFile
        .builder("", setter)
        .build()
}

/**
public class SetterAccountWithAnnotation_transactions implements Setter {
    private final Formatter f;

    public SetterAccountWithAnnotation_transactions (Formatter f) {
        this.f = f;
    }

    public void apply (Object target, JsonTokens tokens) {
        List<Transaction> v = (List<Transaction>) f.format(pt.isel.JsonParserDynamic.INSTANCE.parse(tokens, JvmClassMappingKt.getKotlinClass(Transaction.class)));
        ((Account) target).setTransactions(v);
    }
}
 */

fun buildSetterPropertyDynamicAnnoList(domainKlass: KClass<*>, prop: KProperty<*>): JavaFile {
    val listType: KTypeProjection = prop.returnType.arguments[0] // Tipo de dados na lista

    val fmt = FieldSpec.builder(Formatter::class.java, "f")
        .addModifiers(Modifier.FINAL, Modifier.PRIVATE)
        .build()

    val ctor = MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Formatter::class.java, "f")
        .addStatement("this.\$N = f", fmt)
        .build()

    val apply = MethodSpec
        .methodBuilder("apply")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Object::class.java, "target")
        .addParameter(JsonTokens::class.java, "tokens")
        .addStatement(
            "${prop.returnType.javaType.typeName} v = (${prop.returnType.javaType.typeName}) f.format(pt.isel.JsonParserDynamic.INSTANCE.parseArray(tokens, kotlin.jvm.JvmClassMappingKt.getKotlinClass(${listType}.class)))")
        .addStatement("((${domainKlass.qualifiedName}) target).set${capitalizeFirstLetter(prop.name)}(v)")
        .build()

    val setter = TypeSpec
        .classBuilder("Setter${domainKlass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Setter::class.java)
        .addField(fmt)
        .addMethod(ctor)
        .addMethod(apply)
        .build()

    return JavaFile
        .builder("", setter)
        .build()
}

/**
public class SetterAddress_postcode implements Setter {
    public void apply (Object target, JsonTokens tokens) {
        int v = Integer.parseInt(tokens.popWordPrimitive());
        ((Address) target).setPostcode(v);
    }
}
 */

fun buildSetterPropertyDynamicPrimitive(domainKlass: KClass<*>, prop: KProperty<*>): JavaFile {
    val typeName = refType[prop.returnType.classifier]

    val apply = MethodSpec
        .methodBuilder("apply")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Object::class.java, "target")
        .addParameter(JsonTokens::class.java, "tokens")
        .addStatement(
            "${prop.returnType.javaType.typeName} v = ${typeName}.parse${capitalizeFirstLetter(prop.returnType.javaType.typeName)}(tokens.popWordPrimitive())")
        .addStatement("((${domainKlass.qualifiedName}) target).set${capitalizeFirstLetter(prop.name)}(v)")
        .build()

    val setter = TypeSpec
        .classBuilder("Setter${domainKlass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Setter::class.java)
        .addMethod(apply)
        .build()

    return JavaFile
        .builder("", setter)
        .build()
}

private fun capitalizeFirstLetter(str: String): String? {
    return str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
}