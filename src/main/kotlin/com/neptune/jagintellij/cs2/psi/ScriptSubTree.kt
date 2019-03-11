package com.neptune.jagintellij.cs2.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.cs2.ClientScriptLanguage
import org.antlr.intellij.adaptor.SymtabUtils
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree
import org.antlr.intellij.adaptor.psi.ScopeNode

class ScriptSubTree(node: ASTNode, idElementType: IElementType) : IdentifierDefSubtree(node, idElementType), ScopeNode {

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        return SymtabUtils.resolve(this, ClientScriptLanguage, element, "/file/scriptDeclaration/SCRIPT_DECLARATION")
    }

}
