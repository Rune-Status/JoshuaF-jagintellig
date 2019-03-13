package com.neptune.jagintellij.cs2

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.tokens
import com.neptune.jagintellij.parser.ClientScriptLexer

class ClientScriptBraceMatcher : PairedBraceMatcher {

    companion object {

        private val PAIRS = arrayOf(
            BracePair(tokens[ClientScriptLexer.LPAREN], tokens[ClientScriptLexer.RPAREN], false),
            BracePair(tokens[ClientScriptLexer.LBRACE], tokens[ClientScriptLexer.RBRACE], true)
        )

    }

    override fun getPairs() = PAIRS

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}
