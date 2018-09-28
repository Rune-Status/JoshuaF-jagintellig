package com.neptune.jagintellij

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.psi.JagConfigTypes

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

class JagConfigSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        private val SEPARATOR = createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        private val KEY = createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        private val VALUE = createTextAttributesKey("SIMPLE_VALUE", DefaultLanguageHighlighterColors.STRING)
        private val COMMENT = createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

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

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            JagConfigTypes.SEPARATOR -> SEPARATOR_KEYS
            JagConfigTypes.KEY -> KEY_KEYS
            JagConfigTypes.VALUE -> VALUE_KEYS
            JagConfigTypes.COMMENT -> COMMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}
