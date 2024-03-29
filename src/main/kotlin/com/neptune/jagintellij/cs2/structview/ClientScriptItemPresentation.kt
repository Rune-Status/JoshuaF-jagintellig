package com.neptune.jagintellij.cs2.structview

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.util.PlatformIcons
import javax.swing.Icon

class ClientScriptItemPresentation(private val element: PsiElement) : ItemPresentation {

    override fun getPresentableText(): String? {
        return element.node.text/*.removePrefix("[clientscript,").removePrefix("[proc,").removeSuffix("]")*/
    }

    override fun getIcon(unused: Boolean): Icon = if (element.node.text.startsWith("[clientscript,")) {
        PlatformIcons.METHOD_ICON
    } else {
        PlatformIcons.FUNCTION_ICON
    }

    override fun getLocationString(): String? {
        return null
    }

}
