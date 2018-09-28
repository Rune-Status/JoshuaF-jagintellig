package com.neptune.jagintellij

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class JagConfigFileTypeFactory : FileTypeFactory() {

    companion object {

        private val FILE_TYPES = arrayOf("obj", "loc", "npc")

    }

    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(JagConfigFileType.INSTANCE, FILE_TYPES.joinToString(separator = ";"))
    }

}
