package com.neptune.jagintellij.cs2

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class ClientScriptColorSettingsPage : ColorSettingsPage {

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Keyword", ClientScriptSyntaxHighlighter.KEYWORD),
            AttributesDescriptor("Header", ClientScriptSyntaxHighlighter.SCRIPT_HEADER),
            AttributesDescriptor("Variable", ClientScriptSyntaxHighlighter.VAR),
            AttributesDescriptor("Game Variable", ClientScriptSyntaxHighlighter.GAME_VAR),
            AttributesDescriptor("Constant", ClientScriptSyntaxHighlighter.CONSTANT_VAR),
            AttributesDescriptor("Line Comment", ClientScriptSyntaxHighlighter.LINE_COMMENT),
            AttributesDescriptor("Block Comment", ClientScriptSyntaxHighlighter.BLOCK_COMMENT),
            AttributesDescriptor("Constant Int", ClientScriptSyntaxHighlighter.CONSTANT_INT),
            AttributesDescriptor("Command Call", ClientScriptSyntaxHighlighter.COMMAND_CALL),
            AttributesDescriptor("Proc Call", ClientScriptSyntaxHighlighter.PROC_CALL),
            AttributesDescriptor("Identifier", ClientScriptSyntaxHighlighter.IDENTIFIER)
        )
    }

    override fun getIcon(): Icon? {
        return ClientScriptFileType.icon
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return ClientScriptSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return """/**
            | * Example script
            | */
            |<header>[clientscript,demo]</header>(int <var>${'$'}some_var</var>)(int)
            |// Declaring a local variable
            |def_int <var>${'$'}var</var> = <constant_var>^max_32bit_int</constant_var>;
            |<game_var>%some_game_var</game_var> = 1;
            |<proc>~some_script</proc>();
            |<command>cc_setcolour</command>(0xFFFFFF);
            |return(<game_var>%some_game_var</game_var>);
        """.trimMargin()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = mapOf(
        "header" to ClientScriptSyntaxHighlighter.SCRIPT_HEADER,
        "proc" to ClientScriptSyntaxHighlighter.PROC_CALL,
        "command" to ClientScriptSyntaxHighlighter.COMMAND_CALL,
        "var" to ClientScriptSyntaxHighlighter.VAR,
        "game_var" to ClientScriptSyntaxHighlighter.GAME_VAR,
        "constant_var" to ClientScriptSyntaxHighlighter.CONSTANT_VAR
    )

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String {
        return "ClientScript"
    }
}
