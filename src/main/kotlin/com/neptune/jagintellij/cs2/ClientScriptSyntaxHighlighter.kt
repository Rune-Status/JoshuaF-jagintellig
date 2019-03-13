package com.neptune.jagintellij.cs2

import com.intellij.lexer.Lexer
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.parser.ClientScriptLexer
import com.neptune.jagintellij.parser.ClientScriptParser
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class ClientScriptSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {

        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(ClientScriptLanguage,
                ClientScriptParser.tokenNames,
                ClientScriptParser.ruleNames)
        }


        val LOG = Logger.getInstance("ClientScriptSyntaxHighlighter")

        private val EMPTY_KEYS = arrayOf<TextAttributesKey>()

        val SCRIPT_HEADER = createTextAttributesKey("CLIENTSCRIPT_HEADER", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        val ID = createTextAttributesKey("CLIENTSCRIPT_ID", DefaultLanguageHighlighterColors.IDENTIFIER)
        val KEYWORD = createTextAttributesKey("CLIENTSCRIPT_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val VAR = createTextAttributesKey("CLIENTSCRIPT_VAR", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val GAME_VAR = createTextAttributesKey("CLIENTSCRIPT_GAME_VAR", VAR)
        val CONSTANT_VAR = createTextAttributesKey("CLIENTSCRIPT_CONSTANT_VAR", VAR)
        val CONSTANT_INT = createTextAttributesKey("CLIENTSCRIPT_CONSTANT_INT", DefaultLanguageHighlighterColors.NUMBER)
        val STRING = createTextAttributesKey("CLIENTSCRIPT_STRING", DefaultLanguageHighlighterColors.STRING)
        val LINE_COMMENT = createTextAttributesKey("CLIENTSCRIPT_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BLOCK_COMMENT = createTextAttributesKey("CLIENTSCRIPT_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)

    }

    override fun getHighlightingLexer(): Lexer {
        val lexer = ClientScriptLexer(null)
        return ANTLRLexerAdaptor(ClientScriptLanguage, lexer)
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (tokenType !is TokenIElementType) {
            return arrayOf(ID)
        }

        val ttype = tokenType.antlrTokenType
        val attrKey: TextAttributesKey

        when (ttype) {
            ClientScriptLexer.SCRIPT_DECLARATION -> {
                attrKey = SCRIPT_HEADER
            }
            ClientScriptLexer.ID -> {
                attrKey = ID
            }
            ClientScriptLexer.LOCAL_VAR -> {
                attrKey = VAR
            }
            ClientScriptLexer.GAME_VAR -> {
                attrKey = GAME_VAR
            }
            ClientScriptLexer.CONSTANT_VAR -> {
                attrKey = CONSTANT_VAR
            }
            ClientScriptLexer.INT,
            ClientScriptLexer.COORD_GRID -> {
                attrKey = CONSTANT_INT
            }
            in ClientScriptLexer.TYPEINT..ClientScriptLexer.TYPEBOOL,
            ClientScriptLexer.CALC,
            ClientScriptLexer.RETURN,
            ClientScriptLexer.DEF_TYPE,
            ClientScriptLexer.NULL,
            ClientScriptLexer.TRUE,
            ClientScriptLexer.FALSE -> {
                attrKey = KEYWORD
            }
//            ClientScriptLexer.VAR,
//            ClientScriptLexer.WHILE,
//            ClientScriptLexer.IF,
//            ClientScriptLexer.ELSE,
//            ClientScriptLexer.RETURN,
//            ClientScriptLexer.PRINT,
//            ClientScriptLexer.FUNC,
//            ClientScriptLexer.TYPEINT,
//            ClientScriptLexer.TYPEFLOAT,
//            ClientScriptLexer.TYPESTRING,
//            ClientScriptLexer.TYPEBOOLEAN,
//            ClientScriptLexer.TRUE,
//            ClientScriptLexer.FALSE -> {
//                attrKey = KEYWORD
//            }
            ClientScriptLexer.STRING -> {
                attrKey = STRING
            }
            ClientScriptLexer.COMMENT -> {
                attrKey = BLOCK_COMMENT
            }
            ClientScriptLexer.LINE_COMMENT -> {
                attrKey = LINE_COMMENT
            }
            else -> {
                return EMPTY_KEYS
            }
        }

        return arrayOf(attrKey)
    }

}
