package pt.isel.sample

import pt.isel.Formatter

class JsonToDate : Formatter {
    override fun format(value: Any?): Any {
        val date = value as String
        val dateParts = date.split("-")
        LazyCounter.incLazyCounter()
        return Date(dateParts[2].toInt(),dateParts[1].toInt(),dateParts[0].toInt())
    }
}
