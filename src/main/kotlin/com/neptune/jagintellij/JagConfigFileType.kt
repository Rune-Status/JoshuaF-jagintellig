package com.neptune.jagintellij

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object JagConfigFileType : LanguageFileType(JagConfigLanguage) {

    override fun getIcon(): Icon? {
        return IconLoader.getIcon("/com/neptune/jagintellij/jar-gray.png")
    }

    override fun getName(): String {
        return "JagConfig"
    }

    override fun getDefaultExtension(): String {
        return "jag"
    }

    override fun getDescription(): String {
        return "Jagex Config File"
    }

}