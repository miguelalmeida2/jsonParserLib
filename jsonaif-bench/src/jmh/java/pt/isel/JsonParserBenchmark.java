package pt.isel;

import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

import static pt.isel.JsonParserObjectsKt.*;

//@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonParserBenchmark {

    /*
    public KClass ClassToKClass(Class sut){
        return Reflection.createKotlinClass(sut);
    }*/

    //Objects
    static final String jsonOfSimpleStudent = "{ name: \"Ze Manel\", nr: 7353}";
    static final String jsonOfSimplePerson = "{ id: 94646, name: \"Ze Manel\"}";
    static final String jsonOfComposeObject ="{ id: 94646, name: \"Ze Manel\", birth: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}";
    static final String jsonOfArray = "[{ id: 3156, name: \"Ze Manel\"}, {id: 4662, name: \"Candida Raimunda\"}, { id: 8653, name: \"Kata Mandala\"}]";
    static final String jsonOfClassroom = "{ students:[{ name: \"David Costa\", nr: 45935}, {name: \"Miguel Almeida\", nr: 48752}, { name: \"Filipe f\", nr: 9841}]}";
    static final String jsonOfAccount = "{ id: 12, balance: 10000, transactions:[{ sender: 1, value: 45935, receiver: 2}, { sender: 2, value: 205, receiver: 1}]}";
    static final String jsonOfPropertyAltName ="{ Id: 94646, name: \"Ze Manel\", birth_date: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}";
    static final String jsonOfBirthPropertyToClass = "{ name: \"Maria Papoila\", nr: 73753, birth: \"1998-11-17\" }";
    static final String jsonOfAccountWithTransactionAnnotation = "{ id: 12, balance: 10000, transactions: [\"45935-1-2\",\"205-2-1\"]}";

    static final String jsonPrimitive = "{ integer: 123, float: 2.7182818284f, double: 2.7182818284, boolean: true}";

    //Parsers
    static final JsonParserReflect parserReflect = JsonParserReflect.INSTANCE;
    static final JsonParserDynamic parserDynamic = JsonParserDynamic.INSTANCE;

    @Benchmark
    public void benchParserSimpleObjectViaPropertiesWithReflection() {
        parseStudent(jsonOfSimpleStudent,parserReflect);
    }

    @Benchmark
    public void benchParserSimpleObjectViaPropertiesWithDynamic() {
        parseStudent(jsonOfSimpleStudent,parserDynamic);
    }

    @Benchmark
    public void benchParserSimpleObjectViaConstructorWithReflection() {
        parsePerson(jsonOfSimplePerson,parserReflect);
    }

    @Benchmark
    public void benchParserSimpleObjectViaConstructorWithDynamic() {
        parsePerson(jsonOfSimplePerson,parserDynamic);
    }

    @Benchmark
    public void benchParserComposeObjectWithReflection() {
        parsePerson(jsonOfComposeObject, parserReflect);
    }

    @Benchmark
    public void benchParserComposeObjectWithDynamic() {
        parsePerson(jsonOfComposeObject, parserDynamic);
    }

    @Benchmark
    public void benchParserArrayWithReflection() {
        parsePerson(jsonOfArray, parserReflect);
    }

    @Benchmark
    public void benchParserArrayWithDynamic() {
        parsePerson(jsonOfArray, parserDynamic);
    }

    @Benchmark
    public void benchParserClassroomWithReflection() {
        parseClassroom(jsonOfClassroom, parserReflect);
    }

    @Benchmark
    public void benchParserClassroomWithDynamic() {
        parseClassroom(jsonOfClassroom, parserDynamic);
    }

    @Benchmark
    public void benchParserAccountWithReflection() {
        parseAccount(jsonOfAccount, parserReflect);
    }

    @Benchmark
    public void benchParserAccountWithDynamic() {
        parseAccount(jsonOfAccount, parserDynamic);
    }

    @Benchmark
    public void benchParserPropertyAltNameWithReflection() {
        parsePerson(jsonOfPropertyAltName, parserReflect);
    }

    @Benchmark
    public void benchParserPropertyAltNameWithDynamic() {
        parsePerson(jsonOfPropertyAltName, parserDynamic);
    }

    @Benchmark
    public void benchParserBirthPropertyToClassWithReflection() {
        parseStudent(jsonOfBirthPropertyToClass, parserReflect);
    }

    @Benchmark
    public void benchParserBirthPropertyToClassWithDynamic() {
        parseStudent(jsonOfBirthPropertyToClass, parserDynamic);
    }

    @Benchmark
    public void benchParserAccountWithTransactionAnnotationWithReflection() {
        parseAccountWithAnnotation(jsonOfAccountWithTransactionAnnotation, parserReflect);
    }

    @Benchmark
    public void benchParserAccountWithTransactionAnnotationWithDynamic() {
        parseAccountWithAnnotation(jsonOfAccountWithTransactionAnnotation, parserDynamic);
    }

    @Benchmark
    public void benchParserPrimitivesReflection(){
        parsePrimitives(jsonPrimitive, parserReflect);
    }

    @Benchmark
    public void benchParserPrimitivesDynamic() {
        parsePrimitives(jsonPrimitive, parserDynamic);
    }

}

