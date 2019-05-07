package com.neptune.jagintellij.cs2.editor

import com.intellij.ide.IdeBundle
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.ElementColorProvider
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.ui.ColorUtil
import com.neptune.jagintellij.cs2.ClientScriptParserDefinition.Companion.tokens
import com.neptune.jagintellij.parser.ClientScriptLexer
import java.awt.Color

class ClientScriptColorProvider : ElementColorProvider {

    override fun setColorTo(element: PsiElement, color: Color) {
        val factory = JavaPsiFacade.getElementFactory(element.project)
        val document = PsiDocumentManager.getInstance(element.project).getDocument(element.containingFile)

        CommandProcessor.getInstance().executeCommand(element.project, {
            element.replace(factory.createExpressionFromText("0x${ColorUtil.toHex(color).toUpperCase()}", null))
        }, IdeBundle.message("change.color.command.text"), null, document)
    }

    override fun getColorFrom(element: PsiElement): Color? {
        if (element.node.elementType != tokens[ClientScriptLexer.HEX]) {
            return null
        }

        return Color(element.node.text.substring(2).toInt(16))
    }

}
