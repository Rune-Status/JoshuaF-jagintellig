package com.neptune.jagintellij.cs2.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.neptune.jagintellij.cs2.ClientScriptFileType
import com.neptune.jagintellij.cs2.ClientScriptLanguage
import org.antlr.intellij.adaptor.psi.ScopeNode

class ClientScriptPSIFileRoot(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, ClientScriptLanguage), ScopeNode {

    override fun getFileType(): FileType = ClientScriptFileType

    override fun getContext(): ScopeNode? {
        return null
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        // TODO ??
        return null
    }

}
