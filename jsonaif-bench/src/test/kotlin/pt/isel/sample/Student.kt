package pt.isel.sample

import pt.isel.JsonConvert
import pt.isel.JsonToDate

data class Student (var nr: Int = 0, var name: String? = null, @JsonConvert(JsonToDate::class) var birth: Date? = null)
