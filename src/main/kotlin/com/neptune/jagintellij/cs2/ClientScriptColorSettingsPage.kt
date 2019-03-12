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
            AttributesDescriptor("Header", ClientScriptSyntaxHighlighter.SCRIPT_HEADER),
            AttributesDescriptor("Variable", ClientScriptSyntaxHighlighter.VAR),
            AttributesDescriptor("Game Variable", ClientScriptSyntaxHighlighter.GAME_VAR),
            AttributesDescriptor("Constant", ClientScriptSyntaxHighlighter.CONSTANT_VAR),
            AttributesDescriptor("Line Comment", ClientScriptSyntaxHighlighter.LINE_COMMENT),
            AttributesDescriptor("Block Comment", ClientScriptSyntaxHighlighter.BLOCK_COMMENT),
            AttributesDescriptor("Constant Int", ClientScriptSyntaxHighlighter.CONSTANT_INT)
//            AttributesDescriptor("Key", JagConfigSyntaxHighlighter.KEY),
//            AttributesDescriptor("Separator", JagConfigSyntaxHighlighter.SEPARATOR),
//            AttributesDescriptor("Value", JagConfigSyntaxHighlighter.VALUE)
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
            |[clientscript,demo](int ${'$'}some_var)(int)
            |// Declaring a local variable
            |def_int ${'$'}var = ^max_32bit_int;
            |%some_game_var = 1;
            |return(%some_game_var);
        """.trimMargin()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String {
        return "ClientScript"
    }
}
