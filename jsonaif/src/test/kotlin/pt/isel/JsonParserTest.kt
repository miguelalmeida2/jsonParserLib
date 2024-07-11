package pt.isel

import pt.isel.sample.*
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonParserTest {

    @Test fun parseSimpleObjectViaProperties() {
        val json = "{ name: \"Ze Manel\", nr: 7353}"
        val student = JsonParserReflect.parse<Student>(json)
        assertEquals("Ze Manel", student?.name)
        assertEquals(7353, student?.nr)
    }

    @Test fun parseSimpleObjectViaConstructor() {
        val json = "{ id: 94646, name: \"Ze Manel\"}"
        val p = JsonParserReflect.parse<Person>(json)
        assertEquals(94646, p?.id)
        assertEquals("Ze Manel", p?.name)
    }

    @Test fun parseComposeObject() {
        val json = "{ id: 94646, name: \"Ze Manel\", birth: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}"
        val p = JsonParserReflect.parse<Person>(json)
        assertEquals(94646, p?.id)
        assertEquals("Ze Manel", p?.name)
        assertEquals(19, p?.birth?.day)
        assertEquals(9, p?.birth?.month)
        assertEquals(1999, p?.birth?.year)
    }

    @Test fun parseArray() {
        val json = "[{ id: 3156, name: \"Ze Manel\"}, {id: 4662, name: \"Candida Raimunda\"}, { id: 8653, name: \"Kata Mandala\"}]";
        val ps = JsonParserReflect.parseArray<Person>(json)
        assertEquals(3, ps.size)
        assertEquals("Ze Manel", ps[0]?.name)
        assertEquals("Candida Raimunda", ps[1]?.name)
        assertEquals("Kata Mandala", ps[2]?.name)
    }

    @Test fun parseClassroom() {
        val json = "{ students:[{ name: \"David Costa\", nr: 45935}, {name: \"Miguel Almeida\", nr: 48752}, { name: \"Filipe f\", nr: 9841}]}"
        val classroom = JsonParserReflect.parse<Classroom>(json)
        assertEquals(3, classroom!!.size)
        assertEquals("David Costa", classroom.students[0].name)
        assertEquals(45935, classroom.students[0].nr)
        assertEquals("Miguel Almeida", classroom.students[1].name)
        assertEquals(9841, classroom.students[2].nr)
    }

    @Test fun parseAccount() {
        val json = "{ id: 12, balance: 10000, transactions:[{ sender: 1, value: 45935, receiver: 2}, { sender: 2, value: 205, receiver: 1}]}"
        val account = JsonParserReflect.parse<Account>(json)
        assertEquals(10000, account!!.balance)
        assertEquals(12, account.id)
        assertEquals(45935, account.transactions[0].value)
        assertEquals(1, account.transactions[1].receiver)
        assertEquals(205, account.transactions[1].value)
    }

    @Test fun parsePropertyAltName() {
        val json = "{ Id: 94646, name: \"Ze Manel\", birth_date: { year: 1999, month: 9, day: 19}, sibling: { id: 1234, name: \"Kata Badala\"}}"
        val p = JsonParserReflect.parse<Person>(json)
        assertEquals(94646, p!!.id)
        assertEquals("Ze Manel", p.name)
        assertEquals(19, p.birth?.day)
        assertEquals(9, p.birth?.month)
        assertEquals(1999, p.birth?.year)
    }

    @Test fun parseBirthPropertyToClass() {

        val json = "{ name: \"Maria Papoila\", nr: 73753, birth: \"1998-11-17\" }"
        val student = JsonParserReflect.parse<Student>(json)

        assertEquals("Maria Papoila", student!!.name)
        student.birth?.let { assertEquals(17, it.day) }
        student.birth?.let { assertEquals(11, it.month) }
        student.birth?.let { assertEquals(1998, it.year) }
    }

    @Test fun parseAccountWithTransactionAnnotation() {
        val json = "{ id: 12, balance: 10000, transactions: [\"45935-1-2\",\"205-2-1\"]}"
        val account = JsonParserReflect.parse<AccountWithAnnotation>(json)
        assertEquals(10000, account!!.balance)
        assertEquals(12, account.id)
        assertEquals(45935, account.transactions[0].value)
        assertEquals(1, account.transactions[1].receiver)
        assertEquals(205, account.transactions[1].value)
    }

    @Test fun parseArrayLazy() {
        val json = "[{ id: 3156, name: \"Ze Manel\"}, {id: 4662, name: \"Candida Raimunda\"}, { id: 8653, name: \"Kata Mandala\"}]";
        val ps = JsonParserReflect.parseSequence<Person>(json).iterator()

        assertEquals("Ze Manel", ps.next()!!.name)
        assertEquals("Candida Raimunda", ps.next()!!.name)
        assertEquals("Kata Mandala", ps.next()!!.name)
    }
}
