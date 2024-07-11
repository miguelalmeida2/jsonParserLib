package pt.isel.setters

import pt.isel.ARRAY_OPEN
import pt.isel.JsonParserReflect
import pt.isel.JsonTokens
import pt.isel.Setter
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty

class SetterProperty(val prop: KMutableProperty<*>) : Setter{
    override fun apply(target: Any, tokens: JsonTokens) {
        val value = if(tokens.current == ARRAY_OPEN) JsonParserReflect.parseArray(tokens, prop.returnType.classifier as KClass<*>) else JsonParserReflect.parse(tokens, prop.returnType.classifier as KClass<*>)
        prop.setter.call(target, value)
    }
}