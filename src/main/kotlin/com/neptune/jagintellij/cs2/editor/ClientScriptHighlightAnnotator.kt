package com.neptune.jagintellij.cs2.editor

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
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

    companion object {

        val LOG = Logger.getInstance(ClientScriptHighlightVisitor::class.java)

    }

    override fun visitElement(element: PsiElement?) {
        // TODO causes problems by highlighting ID multiple times due to calling this on the elements children
//        super.visitElement(element)

        element ?: return

        if (element.node.elementType == rules[ClientScriptParser.RULE_statement]) {
            super.visitElement(element)
            return
        }

        // highlight string content
        if (element.node.elementType == rules[ClientScriptParser.RULE_stringContent]) {
            val annotation = holder.createInfoAnnotation(element, null)
            annotation.textAttributes = ClientScriptSyntaxHighlighter.STRING
        }

        // highlight tags within strings
        if (element.node.elementType == rules[ClientScriptParser.RULE_stringTag]) {
            val annotation = holder.createInfoAnnotation(element, null)
            annotation.textAttributes = ClientScriptSyntaxHighlighter.KEYWORD
        }

        // highlight script and instruction declarations
        if (element.node.elementType in arrayOf(rules[ClientScriptParser.RULE_script],
                rules[ClientScriptParser.RULE_instruction])) {
            val range = TextRange(element.textOffset, element.textOffset + element.text.indexOf(']') + 1)
            val annotation = holder.createInfoAnnotation(range, null)
            annotation.textAttributes = ClientScriptSyntaxHighlighter.SCRIPT_HEADER
        }

        // var references highlighting
        if (element.node.elementType == rules[ClientScriptParser.RULE_var]) {
            val prefix = element.text[0]

            if (prefix in arrayOf('^', '%', '$')) {
                val annotation = holder.createInfoAnnotation(element, null)

                when(prefix) {
                    '^' -> {
                        annotation.textAttributes = ClientScriptSyntaxHighlighter.CONSTANT_VAR
                    }
                    '%' -> {
                        annotation.textAttributes = ClientScriptSyntaxHighlighter.GAME_VAR
                    }
                    '$' -> {
                        annotation.textAttributes = ClientScriptSyntaxHighlighter.VAR
                    }
                }
            }
        }

        // constant value example
//        if (element.node.elementType == tokens[ClientScriptLexer.CONSTANT_VAR] && element.node.text == "^max_32bit_int") {
//            holder.createInfoAnnotation(element, "2,147,483,647")
//        }

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

        // engine command highlight example
        if (element.parent?.node?.elementType == rules[ClientScriptParser.RULE_expr]) {
            if (element.node.elementType == rules[ClientScriptParser.RULE_identifier] && element.node.text == "test") {
                val annotation = holder.createInfoAnnotation(element, null)
                annotation.textAttributes = ClientScriptSyntaxHighlighter.KEYWORD
            }
        }
    }

}
