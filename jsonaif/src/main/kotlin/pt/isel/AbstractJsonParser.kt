package pt.isel

import java.security.InvalidParameterException
import kotlin.reflect.KClass


abstract class AbstractJsonParser : JsonParser {

    override fun <T : Any> parse(source: String, klass: KClass<T>): T? {
        return parse(JsonTokens(source), klass)
    }

    inline fun <reified T : Any> parse(source: String): T? {
        return parse(JsonTokens(source), T::class)
    }

    inline fun <reified T : Any> parse(tokens: JsonTokens): T? {
        return parse(tokens, T::class)
    }


    inline fun <reified T : Any> parseArray(source: String): List<T?> {
        return parseArray(JsonTokens(source), T::class)
    }

    inline fun <reified T : Any> parseSequence(json: String): Sequence<T?>{
        val tokens = JsonTokens(json)

        if(tokens.current != ARRAY_OPEN) throw InvalidParameterException()

        tokens.pop(ARRAY_OPEN) // Discard square brackets [ ARRAY_OPEN
        return sequence {
            while (tokens.current != ARRAY_END) {
                val v = parse<T>(tokens)
                yield(v)
                if (tokens.current == COMMA) // The last element finishes with ] rather than a comma
                    tokens.pop(COMMA) // Discard COMMA
                else break
                tokens.trim()
            }
            tokens.pop(ARRAY_END) // Discard square bracket ] ARRAY_END
        }
    }

    override fun <T : Any> parseArray(tokens: JsonTokens, klass: KClass<T>): List<T?> {
        val list = mutableListOf<T?>()
        tokens.pop(ARRAY_OPEN) // Discard square brackets [ ARRAY_OPEN
        while (tokens.current != ARRAY_END) {
            val v = parse(tokens, klass)
            list.add(v)
            if (tokens.current == COMMA) // The last element finishes with ] rather than a comma
                tokens.pop(COMMA) // Discard COMMA
            else break
            tokens.trim()
        }
        tokens.pop(ARRAY_END) // Discard square bracket ] ARRAY_END
        return list
    }

    fun <T : Any> parse(tokens: JsonTokens, klass: KClass<T>) : T? = when (tokens.current) {
        OBJECT_OPEN -> parseObject(tokens, klass)
        DOUBLE_QUOTES -> parseString(tokens) as T
        else -> parsePrimitive(tokens, klass) as T?
    }

    abstract fun <T : Any> parseObject(tokens: JsonTokens, klass: KClass<T>): T?

    private fun parseString(tokens: JsonTokens): String {
        tokens.pop(DOUBLE_QUOTES) // Discard double quotes "
        return tokens.popWordFinishedWith(DOUBLE_QUOTES)
    }

    abstract fun parsePrimitive(tokens: JsonTokens, klass: KClass<*>): Any?
}