package com.neptune.jagintellij.psi

import com.intellij.psi.tree.IElementType
import com.neptune.jagintellij.JagConfigLanguage

class JagConfigElementType(debugName: String) : IElementType(debugName, JagConfigLanguage.INSTANCE)
