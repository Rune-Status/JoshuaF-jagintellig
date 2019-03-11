package com.neptune.jagintellij.cs2.structview

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class ClientScriptStructureViewRootElement(element: PsiElement) : ClientScriptStructureViewElement(element) {

    override fun getPresentation(): ItemPresentation {
        return ClientScriptRootPresentation(element as PsiFile)
    }

}
