package com.neptune.jagintellij.type

/**
 * Enumeration of possible types that clientscript uses.
 */
enum class ScriptVarType(
    val charKey: Char,
    val fullName: String,
    val defaultValue: Any,
    val baseVarType: BaseVarType = BaseVarType.INTEGER,
    val useable: Boolean = true
) {
    NULL('\u0000', "null", -1, useable = false),
    TYPE('\u0001', "type", -1),
    VARP('\u0002', "varp", -1),
    VARBIT('\u0003', "varbit", -1),
    VARC('\u0004', "varc", -1),
    INTEGER('i', "int", 0),
    BOOLEAN('1', "bool", 0),
    SEQ('A', "seq", -1),
    COMPONENT('I', "component", -1),
    IDKIT('K', "idkit", -1),
    MIDI('M', "midi", -1),
    SYNTH('P', "synth", -1),
    STAT('S', "stat", -1),
    COORD('c', "coord", -1),
    GRAPHIC('d', "graphic", -1),
    FONTMETRICS('f', "fontmetrics", -1),
    ENUM('g', "enum", -1),
    JINGLE('j', "jingle", -1),
    LOC('l', "loc", -1),
    MODEL('m', "model", -1),
    NPC('n', "npc", -1),
    OBJ('o', "obj", -1),
    NAMEDOBJ('O', "namedobj", -1),
    STRING('s', "string", "", BaseVarType.STRING),
    SPOTANIM('t', "spotanim", -1),
    INV('v', "inv", -1),
    TEXTURE('x', "texture", -1),
    CATEGORY('y', "category", -1),
    CHAR('z', "char", -1),
    MAPELEMENT('µ', "mapelement", -1),
    HITMARK('×', "hitmark", -1),
    STRUCT('J', "struct", -1),
    INTERFACE('a', "interface", -1);

    companion object {

        /**
         * A map of [charKey] to [ScriptVarType].
         */
        private val keyToTypeMap = values().associateBy { it.charKey }

        /**
         * A map of [fullName] to [ScriptVarType].
         */
        private val fullNameToTypeMap = values().associateBy { it.fullName }

        /**
         * A set of all the types names.
         */
        val typeNames = values().filter { it.useable }.map { it.fullName.toLowerCase() }.toSet()

        /**
         * Gets the [ScriptVarType] based on the char key.
         */
        fun forCharKey(charKey: Char) = keyToTypeMap[charKey]

        /**
         * Gets the [ScriptVarType] based on the full name.
         */
        fun forFullName(fullName: String) = fullNameToTypeMap[fullName]

    }

}
