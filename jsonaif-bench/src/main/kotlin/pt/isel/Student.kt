package pt.isel

data class Student (var nr: Int = 0, var name: String? = null, @JsonConvert(JsonToDate::class) var birth: Date? = null)