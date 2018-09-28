package com.neptune.jagintellij

import com.intellij.lang.Language

class JagConfigLanguage : Language(NAME) {

    companion object {

        @JvmField
        val INSTANCE = JagConfigLanguage()

        @JvmField
        val NAME = "JagConfig"

    }

}
