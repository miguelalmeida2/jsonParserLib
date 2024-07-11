package pt.isel.setters

import pt.isel.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation


class SetterPropertyFormatter(private val prop: KMutableProperty<*>) : Setter {
    private val formatter = prop
        .findAnnotation<JsonConvert>()
        ?.klass
        ?.createInstance() as Formatter

    override fun apply(target: Any, tokens: JsonTokens) {
        val value = if(tokens.current == ARRAY_OPEN) JsonParserReflect.parseArray(tokens, prop.returnType.classifier as KClass<*>) else JsonParserReflect.parse(tokens, prop.returnType.classifier as KClass<*>)
        prop.setter.call(target, formatter.format(value) )
    }
    fun getFormatter (): Formatter {
        return formatter
    }
}