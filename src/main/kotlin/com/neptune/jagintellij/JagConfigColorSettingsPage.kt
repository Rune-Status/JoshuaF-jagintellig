package com.neptune.jagintellij

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class JagConfigColorSettingsPage : ColorSettingsPage {

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Header", JagConfigSyntaxHighlighter.HEADER),
            AttributesDescriptor("Key", JagConfigSyntaxHighlighter.KEY),
            AttributesDescriptor("Separator", JagConfigSyntaxHighlighter.SEPARATOR),
            AttributesDescriptor("Value", JagConfigSyntaxHighlighter.VALUE)
        )
    }

    override fun getIcon(): Icon? {
        return JagConfigFileType.INSTANCE.icon
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return JagConfigSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return "[identifier]\n" +
            "key1=value\n" +
            "key2=1\n" +
            "multikey=identifier,value"
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String {
        return "JagConfig"
    }
}
