package com.neptune.jagintellij.cs2.structview

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.neptune.jagintellij.cs2.psi.ClientScriptPSIFileRoot


class ClientScriptStructureViewModel(root: ClientScriptPSIFileRoot) :
    StructureViewModelBase(root, ClientScriptStructureViewRootElement(root)),
    StructureViewModel.ElementInfoProvider {

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        return !isAlwaysShowsPlus(element)
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
        val value = element.value
        return value is ClientScriptPSIFileRoot
    }

}
