package pt.isel

fun parseAccount(json: String, parser: JsonParser): Account {
    return parser.parse(json, Account::class) as Account
}

fun parseAccountWithAnnotation(json: String, parser: JsonParser): AccountWithAnnotation {
    return parser.parse(json, AccountWithAnnotation::class) as AccountWithAnnotation
}

fun parseClassroom(json: String, parser: JsonParser): Classroom {
    return parser.parse(json, Classroom::class) as Classroom
}

fun parseDate(json: String, parser: JsonParser): Date {
    return parser.parse(json, Date::class) as Date
}

fun parsePerson(json: String, parser: JsonParser): Person {
    return parser.parse(json, Person::class) as Person
}

fun parseStudent(json: String, parser: JsonParser): Student {
    return parser.parse(json, Student::class) as Student
}

fun parseTransaction(json: String, parser: JsonParser): Transaction {
    return parser.parse(json, Transaction::class) as Transaction
}

fun parsePrimitives(json: String, parser: JsonParser): Primitives {
    return parser.parse(json, Primitives::class) as Primitives
}

