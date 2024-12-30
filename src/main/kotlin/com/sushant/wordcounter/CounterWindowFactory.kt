package com.sushant.wordcounter

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.JBEmptyBorder
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JTextArea

class CounterWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val counterWindow = CounterWindow()
        val contentManager = ContentFactory.getInstance()
        val content = contentManager.createContent(counterWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }

    inner class CounterWindow {
        private val panel = SimpleToolWindowPanel(true)

        fun getContent(): JComponent {
            panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

            val actionGroup = DefaultActionGroup()
            actionGroup.add(WordCounter())
            val actionToolbar = ActionManager.getInstance().createActionToolbar("WordCounter", actionGroup, false)
            panel.add(actionToolbar.component)

            val label =
                JTextArea("Select text and press above button to count length of each word \nResults will be displayed in a seperate tab")
            label.isEditable = false
            label.lineWrap = true
            label.border = JBEmptyBorder(10)
            label.wrapStyleWord = true
            panel.add(label)

            return panel
        }
    }
}

