package com.neptune.jagintellij.cs2.editor

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.rules
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.tokens
import com.neptune.jagintellij.cs2.ClientScriptSyntaxHighlighter
import com.neptune.jagintellij.parser.ClientScriptLexer
import com.neptune.jagintellij.parser.ClientScriptParser

class ClientScriptHighlightAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(ClientScriptHighlightVisitor(holder))
    }

}

private class ClientScriptHighlightVisitor(val holder: AnnotationHolder) : PsiRecursiveElementVisitor() {

    override fun visitElement(element: PsiElement?) {
        // TODO causes problems by highlighting ID multiple times due to calling this on the elements children
//        super.visitElement(element)

        element ?: return

        // highlight instruction declarations
        if (element.node.elementType == rules[ClientScriptParser.RULE_instructionDeclaration]) {
            val annotation = holder.createInfoAnnotation(element, null)
            annotation.textAttributes = ClientScriptSyntaxHighlighter.SCRIPT_HEADER
        }

        // engine command highlight example
        if (element.node.elementType == tokens[ClientScriptLexer.ID] && element.node.text == "test") {
            val annotation = holder.createInfoAnnotation(element, null)
            annotation.textAttributes = ClientScriptSyntaxHighlighter.KEYWORD
        }

        // constant value example
        if (element.node.elementType == tokens[ClientScriptLexer.CONSTANT_VAR] && element.node.text == "^max_32bit_int") {
            holder.createInfoAnnotation(element, "2,147,483,647")
        }

        // error checking for coordgrid/showing absolute coordinates if no error
        if (element.node.elementType == tokens[ClientScriptLexer.COORD_GRID]) {
            val split = element.node.text.split("_")

            val level = split[0].toInt()
            val mapX = split[1].toInt()
            val mapY = split[2].toInt()
            val xInMap = split[3].toInt()
            val yInMap = split[4].toInt()

            val absX = mapX * 64 + xInMap
            val absY = mapY * 64 + yInMap

            if (level > 3) {
                holder.createWarningAnnotation(element, "Level must be [0,3].")
            } else if (xInMap > 63) {
                holder.createWarningAnnotation(element, "X in map must be [0,63].")
            } else if (yInMap > 63) {
                holder.createWarningAnnotation(element, "Y in map must be [0,63].")
            } else {
                holder.createInfoAnnotation(element, "$absX, $absY, $level")
            }
        }
    }

}
