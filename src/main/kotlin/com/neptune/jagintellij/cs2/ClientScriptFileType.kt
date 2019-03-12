package com.neptune.jagintellij.cs2

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object ClientScriptFileType : LanguageFileType(ClientScriptLanguage) {

    val FILE_EXTENSION = "cs2"

    override fun getName(): String {
        return "ClientScript"
    }

    override fun getDescription() = "A ClientScript script file."

    override fun getDefaultExtension() = ClientScriptFileType.FILE_EXTENSION

    override fun getIcon(): Icon? = IconLoader.getIcon("/com/neptune/jagintellij/cs2.svg")

}
