package pt.isel.sample

import pt.isel.JsonProperty
import pt.isel.Person

data class Person (@JsonProperty("Id") val id: Int, val name: String, @JsonProperty("birth_date") val birth: Date? = null, var sibling: Person? = null)
