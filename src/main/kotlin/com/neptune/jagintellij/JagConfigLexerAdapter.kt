package com.neptune.jagintellij

import com.intellij.lexer.FlexAdapter
import com.neptune.jagintellij.lexer.JagConfigLexer

class JagConfigLexerAdapter : FlexAdapter(JagConfigLexer(null))
