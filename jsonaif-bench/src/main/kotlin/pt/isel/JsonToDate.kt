package pt.isel

class JsonToDate : Formatter {
    override fun format(value: Any?): Any {
        val date = value as String
        val dateParts = date.split("-")
        return Date(dateParts[2].toInt(),dateParts[1].toInt(),dateParts[0].toInt())
    }
}
