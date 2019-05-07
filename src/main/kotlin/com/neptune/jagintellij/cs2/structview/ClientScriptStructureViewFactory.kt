package com.neptune.jagintellij.cs2.structview

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.psi.PsiFile
import com.intellij.openapi.editor.Editor
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.neptune.jagintellij.cs2.psi.ClientScriptPSIFileRoot

class ClientScriptStructureViewFactory : PsiStructureViewFactory {

    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        return object : TreeBasedStructureViewBuilder() {

            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                return ClientScriptStructureViewModel(psiFile as ClientScriptPSIFileRoot)
            }

        }
    }

}
