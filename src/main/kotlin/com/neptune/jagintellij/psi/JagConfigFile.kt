package com.neptune.jagintellij.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.neptune.jagintellij.JagConfigFileType
import com.neptune.jagintellij.JagConfigLanguage

class JagConfigFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, JagConfigLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return JagConfigFileType.INSTANCE
    }

    override fun toString(): String {
        return "JagConfig"
    }

}
