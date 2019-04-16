package com.neptune.jagintellij.cs2

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.tokens
import com.neptune.jagintellij.parser.ClientScriptLexer
import com.neptune.jagintellij.type.ScriptVarType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import java.util.*

class ClientScriptSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        val lexer = ClientScriptLexer(null, ScriptVarType.typeNames)
        return ANTLRLexerAdaptor(ClientScriptLanguage, lexer)
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        val attributes: TextAttributesKey?

        @Suppress("CascadeIf")
        /*if (tokenType == tokens[ClientScriptLexer.SCRIPT_DECLARATION]) {
            attributes = SCRIPT_HEADER
        } else */if (tokenType == tokens[ClientScriptLexer.ID]) {
            attributes = ID
        } else if (keywords.contains(tokenType)) {
            attributes = KEYWORD
        } else if (ints.contains(tokenType)) {
            attributes = CONSTANT_INT
        } else if (strings.contains(tokenType)) { // TODO change to list after interpolation added
            attributes = STRING
        } else if (tokenType == tokens[ClientScriptLexer.LINE_COMMENT]) {
            attributes = LINE_COMMENT
        } else if (tokenType == tokens[ClientScriptLexer.BLOCK_COMMENT]) {
            attributes = BLOCK_COMMENT
        }/* else if (tokenType == tokens[ClientScriptLexer.LOCAL_VAR]) {
            attributes = VAR
        } else if (tokenType == tokens[ClientScriptLexer.CONSTANT_VAR]) {
            attributes = CONSTANT_VAR
        } else if (tokenType == tokens[ClientScriptLexer.GAME_VAR]) {
            attributes = GAME_VAR
        }*/ else if (tokenType == tokens[ClientScriptLexer.ERRCHAR]) {
            attributes = HighlighterColors.BAD_CHARACTER
        } else {
            attributes = null
        }

        return pack(attributes)
    }

    companion object {

        val SCRIPT_HEADER = createTextAttributesKey("CLIENTSCRIPT_HEADER",
            DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)

        val ID = createTextAttributesKey("CLIENTSCRIPT_ID",
            DefaultLanguageHighlighterColors.IDENTIFIER)

        val KEYWORD = createTextAttributesKey("CLIENTSCRIPT_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD)

        val VAR = createTextAttributesKey("CLIENTSCRIPT_VAR",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD)

        val GAME_VAR = createTextAttributesKey("CLIENTSCRIPT_GAME_VAR", VAR)

        val CONSTANT_VAR = createTextAttributesKey("CLIENTSCRIPT_CONSTANT_VAR", VAR)

        val CONSTANT_INT = createTextAttributesKey("CLIENTSCRIPT_CONSTANT_INT",
            DefaultLanguageHighlighterColors.NUMBER)

        val STRING = createTextAttributesKey("CLIENTSCRIPT_STRING",
            DefaultLanguageHighlighterColors.STRING)

        val LINE_COMMENT = createTextAttributesKey("CLIENTSCRIPT_LINE_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT)

        val BLOCK_COMMENT = createTextAttributesKey("CLIENTSCRIPT_BLOCK_COMMENT",
            DefaultLanguageHighlighterColors.BLOCK_COMMENT)

    }

    private val keywords = Arrays.asList(
        tokens[ClientScriptLexer.TRUE], tokens[ClientScriptLexer.FALSE], tokens[ClientScriptLexer.NULL],
        tokens[ClientScriptLexer.TYPE], tokens[ClientScriptLexer.DEF_TYPE], tokens[ClientScriptLexer.RETURN],
        tokens[ClientScriptLexer.CALC], tokens[ClientScriptLexer.IF], tokens[ClientScriptLexer.ELSE],
        tokens[ClientScriptLexer.WHILE], tokens[ClientScriptLexer.FOR], tokens[ClientScriptLexer.SWITCH],
        tokens[ClientScriptLexer.SWITCH_TYPE], tokens[ClientScriptLexer.CASE], tokens[ClientScriptLexer.DEFAULT],
        tokens[ClientScriptLexer.TYPEINT], tokens[ClientScriptLexer.DEF_INT], tokens[ClientScriptLexer.TYPESTRING],
        tokens[ClientScriptLexer.DEF_STRING]
    )

    private val strings = Arrays.asList(
        tokens[ClientScriptLexer.QUOTE_OPEN], tokens[ClientScriptLexer.QUOTE_CLOSE]
    )

    private val ints = Arrays.asList(
        tokens[ClientScriptLexer.INT], tokens[ClientScriptLexer.COORD_GRID], tokens[ClientScriptLexer.HEX]
    )

}
