package com.neptune.jagintellij.cs2

import com.intellij.lang.Commenter

class ClientScriptCommenter : Commenter {

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null

    override fun getBlockCommentPrefix(): String? = "/*"

    override fun getBlockCommentSuffix(): String? = "*/"

    override fun getLineCommentPrefix(): String? = "//"

}
