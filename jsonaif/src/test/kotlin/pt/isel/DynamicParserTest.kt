package pt.isel

import org.junit.Test
import pt.isel.sample.*
import kotlin.test.assertEquals

class DynamicParserTest {


    @Test fun parseSimpleObjectViaProperties() {
        val json = "{ name: \"Ze Manel\", nr: 7353}"
        val student = JsonParserDynamic.parse<Student>(json)
        assertEquals("Ze Manel", student!!.name)
        assertEquals(7353, student.nr)
    }

    @Test fun parseSimpleObjectViaConstructor() {
        val json = "{ id: 94646, name: \"Ze Manel\"}"
        val p = JsonParserDynamic.parse<Person>(json)
        assertEquals(94646, p!!.id)
        assertEquals("Ze Manel", p.name)
    }

    @Test fun parseComposeObject() {
        val json = "{ id: 94646, name: \"Ze Manel\", birth: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}"
        val p = JsonParserDynamic.parse<Person>(json)
        assertEquals(94646, p!!.id)
        assertEquals("Ze Manel", p.name)
        assertEquals(19, p.birth?.day)
        assertEquals(9, p.birth?.month)
        assertEquals(1999, p.birth?.year)
    }

    @Test fun parseArray() {
        val json = "[{ id: 3156, name: \"Ze Manel\"}, {id: 4662, name: \"Candida Raimunda\"}, { id: 8653, name: \"Kata Mandala\"}]";
        val ps = JsonParserDynamic.parseArray<Person>(json)
        assertEquals(3, ps.size)
        assertEquals("Ze Manel", ps[0]?.name)
        assertEquals("Candida Raimunda", ps[1]?.name)
        assertEquals("Kata Mandala", ps[2]?.name)
    }

    @Test fun parseClassroom() {
        val json = "{ students:[{ name: \"David Costa\", nr: 45935}, {name: \"Miguel Almeida\", nr: 48752}, { name: \"Filipe Costa\", nr: 43544}]}"
        val classroom = JsonParserDynamic.parse<Classroom>(json)
        assertEquals(3, classroom!!.size)
        assertEquals("David Costa", classroom.students!![0].name)
        assertEquals(45935, classroom.students[0].nr)
        assertEquals("Miguel Almeida", classroom.students[1].name)
        assertEquals(9841, classroom.students[2].nr)
    }

    @Test fun parseAccount() {
        val json = "{ id: 12, balance: 10000, transactions:[{ sender: 1, value: 45935, receiver: 2}, { sender: 2, value: 205, receiver: 1}]}"
        val account = JsonParserDynamic.parse<Account>(json)
        assertEquals(10000, account!!.balance)
        assertEquals(12, account.id)
        assertEquals(45935, account.transactions[0].value)
        assertEquals(1, account.transactions[1].receiver)
        assertEquals(205, account.transactions[1].value)
    }

    @Test fun parsePropertyAltName() {
        val json = "{ Id: 94646, name: \"Ze Manel\", birth_date: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}"
        val p = JsonParserDynamic.parse<Person>(json)
        assertEquals(94646, p!!.id)
        assertEquals("Ze Manel", p.name)
        assertEquals(19, p.birth?.day)
        assertEquals(9, p.birth?.month)
        assertEquals(1999, p.birth?.year)
    }

    @Test fun parseBirthPropertyToClass() {
        val json = "{ name: \"Maria Papoila\", nr: 73753, birth: \"1998-11-17\" }"
        val student = JsonParserDynamic.parse<Student>(json)

        assertEquals("Maria Papoila", student!!.name)
        student.birth?.let { assertEquals(17, it.day) }
        student.birth?.let { assertEquals(11, it.month) }
        student.birth?.let { assertEquals(1998, it.year) }
    }

    @Test
    fun parseAccountWithTransactionAnnotation() {
        val json = "{ id: 12, balance: 10000, transactions: [\"45935-1-2\",\"205-2-1\"]}"
        val account = JsonParserDynamic.parse<AccountWithAnnotation>(json)
        assertEquals(10000, account!!.balance)
        assertEquals(12, account.id)
        assertEquals(45935, account.transactions[0].value)
        assertEquals(1, account.transactions[1].receiver)
        assertEquals(205, account.transactions[1].value)
    }

    @Test
    fun parsePrimitives() {
        val json = "{ integer: 123, float: 2.7182818284f, double: 2.7182818284, boolean: true}"
        val primitive = JsonParserDynamic.parse<Primitives>(json)
        assertEquals(123, primitive!!.integer)
        assertEquals(2.7182817f, primitive.float)
        assertEquals(2.7182818284, primitive.double)
        assertEquals(true, primitive.boolean)
    }

    @Test fun parseArrayLazy() {
        val json = "[{ id: 3156, name: \"Ze Manel\"}, {id: 4662, name: \"Candida Raimunda\"}, { id: 8653, name: \"Kata Mandala\"}]";
        val ps = JsonParserDynamic.parseSequence<Person>(json).iterator()

        assertEquals("Ze Manel", ps.next()!!.name)
        assertEquals("Candida Raimunda", ps.next()!!.name)
        assertEquals("Kata Mandala", ps.next()!!.name)
    }

    @Test fun parseArrayLazyWithAnnotations() {
        val json = "[{ name: \"David Costa\", nr: 45935, birth: \"1999-09-25\"}, {name: \"Miguel Almeida\", nr: 48752, birth: \"1998-11-17\"}, { name: \"Filipe Costa\", nr: 43544, birth: \"1998-04-20\"}]"
        val classroom = JsonParserDynamic.parseSequence<Student>(json).iterator()
        assertEquals(0, LazyCounter.getLazyCounter())
        assertEquals("David Costa", classroom.next()!!.name)
        assertEquals(1, LazyCounter.getLazyCounter())
        assertEquals("Miguel Almeida", classroom.next()!!.name)
        assertEquals(2, LazyCounter.getLazyCounter())
        assertEquals("Filipe Costa", classroom.next()!!.name)
        assertEquals(3, LazyCounter.getLazyCounter())
    }

}