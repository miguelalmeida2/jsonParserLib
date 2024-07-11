package pt.isel.sample

import pt.isel.Student

data class Classroom (val students: List<Student>) {
    val size: Int get() = if(students.isNullOrEmpty()) 0 else students.size
}