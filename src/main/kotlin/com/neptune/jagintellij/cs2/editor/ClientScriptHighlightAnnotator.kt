package com.neptune.jagintellij.cs2.editor

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.tokens
import com.neptune.jagintellij.cs2.ClientScriptSyntaxHighlighter
import com.neptune.jagintellij.parser.ClientScriptLexer

class ClientScriptHighlightAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(ClientScriptHighlightVisitor(holder))
    }

}

private class ClientScriptHighlightVisitor(val holder: AnnotationHolder) : PsiRecursiveElementVisitor() {

    override fun visitElement(element: PsiElement?) {
        super.visitElement(element)

        element ?: return

        if (element.node.elementType == tokens[ClientScriptLexer.ID] && element.node.text == "test") {
            val range = TextRange.from(element.textOffset, element.text.length)
            holder.createInfoAnnotation(range, null).textAttributes = ClientScriptSyntaxHighlighter.KEYWORD
        }
    }

}
