package com.neptune.jagintellij.cs2

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.neptune.jagintellij.cs2.psi.ClientScriptPSIFileRoot
import com.neptune.jagintellij.cs2.psi.ScriptSubTree
import com.neptune.jagintellij.parser.ClientScriptLexer
import com.neptune.jagintellij.parser.ClientScriptParser
import com.neptune.jagintellij.type.ScriptVarType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.v4.runtime.Parser
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class ClientScriptParserDefinition : ParserDefinition {

    companion object {

        init {
            @Suppress("DEPRECATION")
            PSIElementTypeFactory.defineLanguageIElementTypes(ClientScriptLanguage,
                ClientScriptParser.tokenNames,
                ClientScriptParser.ruleNames)
        }

        val FILE = IFileElementType(ClientScriptLanguage)

        val COMMENTS = PSIElementTypeFactory.createTokenSet(ClientScriptLanguage, ClientScriptLexer.BLOCK_COMMENT, ClientScriptLexer.LINE_COMMENT)

        val WHITESPACE = PSIElementTypeFactory.createTokenSet(ClientScriptLanguage, ClientScriptLexer.WS)

        val STRING = PSIElementTypeFactory.createTokenSet(ClientScriptLanguage, ClientScriptLexer.STRING)

        val tokens: List<TokenIElementType> = PSIElementTypeFactory.getTokenIElementTypes(ClientScriptLanguage)

        val rules: List<RuleIElementType> = PSIElementTypeFactory.getRuleIElementTypes(ClientScriptLanguage)

    }

    override fun createLexer(project: Project): Lexer {
        val lexer = ClientScriptLexer(null, ScriptVarType.typeNames)
        return ANTLRLexerAdaptor(ClientScriptLanguage, lexer)
    }

    override fun createParser(project: Project): PsiParser {
        val parser = ClientScriptParser(null)
        return object : ANTLRParserAdaptor(ClientScriptLanguage, parser) {

            override fun parse(parser: Parser?, root: IElementType?) = if (root is IFileElementType) {
                (parser as ClientScriptParser).file()
            } else {
                throw UnsupportedOperationException("Can't parse ${root?.javaClass?.name}")
            }

        }
    }

    override fun getWhitespaceTokens(): TokenSet = WHITESPACE

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun createFile(viewProvider: FileViewProvider): PsiFile = ClientScriptPSIFileRoot(viewProvider)

    override fun getStringLiteralElements(): TokenSet = STRING

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createElement(node: ASTNode): PsiElement {
        val elType = node.elementType
        if (elType is TokenIElementType) {
            return ANTLRPsiNode(node)
        }
        if (elType !is RuleIElementType) {
            return ANTLRPsiNode(node)
        }
        return when (elType.ruleIndex) {
            ClientScriptParser.RULE_script -> ScriptSubTree(node, elType)
//            SampleLanguageParser.RULE_function -> return FunctionSubtree(node, elType)
//            SampleLanguageParser.RULE_vardef -> return VardefSubtree(node, elType)
//            SampleLanguageParser.RULE_formal_arg -> return ArgdefSubtree(node, elType)
//            SampleLanguageParser.RULE_block -> return BlockSubtree(node)
//            SampleLanguageParser.RULE_call_expr -> return CallSubtree(node)
            else -> ANTLRPsiNode(node)
        }
    }

}
