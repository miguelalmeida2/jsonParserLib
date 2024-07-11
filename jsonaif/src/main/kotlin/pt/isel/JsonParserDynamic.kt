package pt.isel

import com.squareup.javapoet.JavaFile
import pt.isel.setters.*
import kotlin.reflect.*
import kotlin.reflect.full.*

object JsonParserDynamic : AbstractJsonParser() {
    private val setters = mutableMapOf<KClass<*>, Map<String, Setter>>()
    private val altNames = mutableMapOf<String, String>()


    override fun parsePrimitive(tokens: JsonTokens, klass: KClass<*>): Any? {
        val str: String = tokens.popWordPrimitive()
        val primitiveParser: ((String) -> Any)? = basicParser[klass]
        return primitiveParser!!(str)
    }

    override fun <T : Any> parseObject(tokens: JsonTokens, klass: KClass<T>): T? {
        // Check if all parameters are optional
        val allOptionalP = klass.constructors.singleOrNull {
            it.parameters.all(KParameter::isOptional)
        } != null

        val members = mutableMapOf<KParameter, Any?>()

        // Check if KClass has JsonProperty annotations
        loadAltNames(klass)

        tokens.pop(OBJECT_OPEN) // Discard initial bracket {
        tokens.trim()

        // Load setters map for this KClass
        val setterMap: Map<String, Setter> = setters.computeIfAbsent(klass,
            JsonParserDynamic::createSetter
        )
        // Create instance of KClass object according to optional parameters
        var instance: Any? = if (allOptionalP) klass.createInstance() else klass.primaryConstructor

        while (tokens.current != OBJECT_END) {
            var parameterName = tokens.popWordFinishedWith(':')
            parameterName = altNames[parameterName] ?: parameterName    // Check for alternative parameter name

            if (allOptionalP) {
                setterMap[parameterName]!!.apply(instance!!, tokens)    // Parse value from tokens and set to instance
            } else {
                val kParameter = klass.primaryConstructor?.parameters?.find { it.name == parameterName }
                members[kParameter!!] = parseMandatoryParam(
                    klass,
                    parameterName,
                    tokens,
                    setterMap
                )   // Parse value from tokens and add to members map
            }

            if (tokens.current == COMMA) // The last element finishes with } rather than a comma
                tokens.pop(COMMA) // Discard COMMA
            else break
            tokens.trim()
        }
        tokens.pop(OBJECT_END) // Discard final bracket }

        if (!allOptionalP) {
            instance = klass.primaryConstructor!!.callBy(members)   // Call primary constructor passing members map
        }
        return instance as T?
    }

    // Check if KClass has JsonProperty annotations adding alternative property name to altNames map
    private fun loadAltNames(klass: KClass<*>){
        klass.memberProperties.forEach {
            if (it.hasAnnotation<JsonProperty>())
                altNames[it.findAnnotation<JsonProperty>()!!.value] = it.name
        }
    }

    // Parse value from tokens and add to members map
    private fun parseMandatoryParam(klass: KClass<*>, parameterName: String, tokens: JsonTokens,setterMap:Map<String, Setter>): Any? {
        val property = klass.memberProperties.find { it.name == parameterName }
        var newKlass = property?.returnType?.classifier as KClass<*>
        //Check if the Klass is a List Type
        if (property.returnType.classifier == List::class) {
            //if it was, get the type of the object saved on List.
            val type: KTypeProjection = property.returnType.arguments[0]
            newKlass = type.type?.classifier as KClass<*>
        }

        val value = if(tokens.current == ARRAY_OPEN) parseArray(tokens, newKlass) else parse(tokens, newKlass)

        if (property.hasAnnotation<JsonConvert>()){
            val formatter = property
                .findAnnotation<JsonConvert>()
                ?.klass
                ?.createInstance() as Formatter
            return formatter.format(value)
        }
        return value
    }

    private fun createSetter(klass: KClass<*>): Map<String, Setter> {
        return klass
            .memberProperties
            .filterIsInstance<KMutableProperty<*>>()
            .associate{
                val file: JavaFile
                val classifier = it.returnType.classifier
                it.name to
                        if (it.hasAnnotation<JsonConvert>()) { // Propriedade ter anotação JsonConvert
                            file = if(classifier == List::class) { // Propriedade ter anotação JsonConvert e ser do tipo List
                                buildSetterPropertyDynamicAnnoList(klass, it) //TODO adicionar teste para dinamico
                            } else buildSetterPropertyDynamicAnno(klass, it)

                            val formatter = it
                                .findAnnotation<JsonConvert>()
                                ?.klass
                                ?.createInstance() as Formatter

                            //file.writeTo(System.out)
                            loadAndCreateInstance(file, formatter) as Setter
                        } else if (classifier == List::class) { // Propriedade ser do tipo List
                            file = buildSetterPropertyDynamicList(klass, it)
                            //file.writeTo(System.out)
                            loadAndCreateInstance(file) as Setter
                        } else if (refType.containsKey(classifier)) {
                            file = buildSetterPropertyDynamicPrimitive(klass, it)
                            //file.writeTo(System.out)
                            loadAndCreateInstance(file) as Setter
                        } else {
                            file = buildSetterPropertyDynamic(klass, it)
                            //file.writeTo(System.out)
                            loadAndCreateInstance(file) as Setter
                        }
            }
    }
}