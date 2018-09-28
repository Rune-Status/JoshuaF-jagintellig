package com.neptune.jagintellij

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class JagConfigFileType : LanguageFileType(JagConfigLanguage.INSTANCE) {

    companion object {

        @JvmField
        val INSTANCE = JagConfigFileType()

    }

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
