package pt.isel

import kotlin.reflect.KClass

interface JsonParser {

    fun <T : Any> parse(source: String, klass: KClass<T>): T?

    fun <T : Any> parseArray(tokens: JsonTokens, klass: KClass<T>): List<T?>

}
