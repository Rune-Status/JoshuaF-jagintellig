package com.neptune.jagintellij.cs2

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class ClientScriptFileTypeFactory : FileTypeFactory() {

    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(ClientScriptFileType, ClientScriptFileType.FILE_EXTENSION)
    }

}
