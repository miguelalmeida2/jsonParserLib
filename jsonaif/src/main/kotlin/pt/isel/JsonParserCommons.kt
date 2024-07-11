package pt.isel

import kotlin.reflect.KClass

val basicParser: Map<KClass<*>, (String) -> Any> = mapOf(
            Byte::class to { it.toByte() },
            Short::class to { it.toShort() },
            Int::class to { it.toInt() },
            Long::class to { it.toLong() },
            Float::class to { it.toFloat() },
            Double::class to { it.toDouble() },
            Boolean::class to { it.toBoolean() }
    )

val refType: Map<KClass<*>, String> = mapOf(
    Byte::class to "Byte",
    Short::class to "Short",
    Int::class to "Integer",
    Long::class to "Long",
    Float::class to "Float",
    Double::class to "Double",
    Boolean::class to "Boolean"
)
