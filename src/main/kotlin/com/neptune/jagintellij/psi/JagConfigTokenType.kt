package com.neptune.jagintellij.psi

import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.JagConfigLanguage

class JagConfigTokenType(debugName: String) : IElementType(debugName, JagConfigLanguage.INSTANCE) {

    override fun toString(): String {
        return "JagConfigTokenType.${super.toString()}"
    }

}
