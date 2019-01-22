package com.neptune.jagintellij

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.psi.JagConfigTypes

class JagConfigSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        internal val SEPARATOR = createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        internal val KEY = createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        internal val VALUE = createTextAttributesKey("SIMPLE_VALUE", DefaultLanguageHighlighterColors.STRING)
        internal val COMMENT = createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        internal val BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        internal val HEADER = createTextAttributesKey("JAGCONFIG_HEADER", DefaultLanguageHighlighterColors.IDENTIFIER)
        internal val TYPE = createTextAttributesKey("JAGCONFIG_TYPE", DefaultLanguageHighlighterColors.KEYWORD)

        private val HEADER_KEYS = arrayOf(TYPE)
        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val KEY_KEYS = arrayOf(KEY)
        private val VALUE_KEYS = arrayOf(VALUE)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer {
        return JagConfigLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType) = when (tokenType) {
        JagConfigTypes.SEPARATOR -> SEPARATOR_KEYS
        JagConfigTypes.HEADER -> HEADER_KEYS
        JagConfigTypes.KEY -> KEY_KEYS
        JagConfigTypes.VALUE -> VALUE_KEYS
        JagConfigTypes.COMMENT -> COMMENT_KEYS
        TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
        else -> EMPTY_KEYS
    }
}
