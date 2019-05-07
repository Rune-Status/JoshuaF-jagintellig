package com.neptune.jagintellij.type

import kotlin.reflect.KClass

/**
 * Enumeration of all possible types that [ScriptVarType] can rely on.
 */
enum class BaseVarType(val clazz: KClass<*>) {
    INTEGER(Int::class),
    STRING(String::class),
    LONG(Long::class)
}
