package com.neptune.jagintellij

import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo

class JagIntellijController(private val project: Project) : ProjectComponent {

    companion object {

        const val PLUGIN_ID = "com.neptune.jagintellij"

        val LOG = Logger.getInstance("JagIntellijController")

    }

    var isProjectClosed = false

    override fun projectClosed() {
        LOG.info("projectClosed ${project.name}")
        isProjectClosed = true
    }

    override fun projectOpened() {
        val plugin = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID))
        var version = "unknown"
        if (plugin != null) {
            version = plugin.version
        }
        LOG.info("Sample Plugin version " + version + ", Java version " + SystemInfo.JAVA_VERSION)
    }

}
