package com.neptune.jagintellij.cs2.structview

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.neptune.jagintellij.cs2.ClientScriptLanguage
import com.neptune.jagintellij.cs2.psi.ClientScriptPSIFileRoot
import org.antlr.intellij.adaptor.xpath.XPath
import java.util.*

open class ClientScriptStructureViewElement(protected val element: PsiElement) : StructureViewTreeElement, SortableTreeElement {

    override fun getValue(): Any {
        return element
    }

    override fun navigate(requestFocus: Boolean) {
        if (element is NavigationItem) {
            (element as NavigationItem).navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean {
        return element is NavigationItem && (element as NavigationItem).canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return element is NavigationItem && (element as NavigationItem).canNavigateToSource()
    }

    override fun getAlphaSortKey(): String {
        return (if (element is PsiNamedElement) element.name else null) ?: return "unknown key"
    }

    override fun getPresentation(): ItemPresentation {
        return ClientScriptItemPresentation(element)
    }

    override fun getChildren(): Array<TreeElement> {
        if (element is ClientScriptPSIFileRoot) {
            val funcs = XPath.findAll(ClientScriptLanguage, element, "/file/script/scriptDeclaration")
            val treeElements = ArrayList<TreeElement>(funcs.size)
            for (el in funcs) {
                treeElements.add(ClientScriptStructureViewElement(el))
            }
            return treeElements.toTypedArray()
        }
        return arrayOf()
    }

}
