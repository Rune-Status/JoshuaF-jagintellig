package com.neptune.jagintellij.cs2.structview

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiFile
import com.neptune.jagintellij.cs2.ClientScriptFileType
import javax.swing.Icon

class ClientScriptRootPresentation(private val element: PsiFile) : ItemPresentation {

    override fun getLocationString(): String? {
        return null
    }

    override fun getIcon(unused: Boolean): Icon? {
        return ClientScriptFileType.icon
    }

    override fun getPresentableText(): String? {
        return element.virtualFile.nameWithoutExtension
    }

}
