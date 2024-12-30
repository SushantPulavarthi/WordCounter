package com.sushant.wordcounter

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.JBEmptyBorder
import java.awt.BorderLayout
import javax.swing.JButton

class WordCounter : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        if (project == null || editor == null) {
            println("No project or editor")
            return
        }

        // Get selection from all carets
        val carets = editor.caretModel.allCarets
        val selectionText = carets.filter { it.hasSelection() }.joinToString {
            it.selectedText.toString()
        }
        if (selectionText.isEmpty()) return

        // Get python script location and setup executor
        val pluginDescriptor = PluginManagerCore.getPlugin(PluginId.getId("com.sushant.WordCounter")) ?: return
        val resourcePath = "${pluginDescriptor.pluginPath}/scripts"
        val commandLine = GeneralCommandLine("python3", "wordcount.py", selectionText)
            .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            .withWorkDirectory(resourcePath)
        val processHandler = OSProcessHandler(commandLine)

        // Setup tool window manager
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Word Counter Window") ?: return
        val contentManager = toolWindow.contentManager
        val factory = contentManager.factory

        // Drop first line of output - python command
        var first = false

        val outputText = StringBuilder()
        processHandler.addProcessListener(object : ProcessAdapter() {
            // Whenever script prints a word length add it to output text
            override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                if (!first) {
                    first = true
                    return
                }
                outputText.append(event.text)
            }

            // At the end of the process add a new results tab to tool window and focus it
            override fun processTerminated(event: ProcessEvent) {
                val output = outputText.toString()
                print(output)

                ApplicationManager.getApplication().invokeLater {
                    val panel = JBPanel<JBPanel<*>>(BorderLayout())
                    val content = factory.createContent(panel, "Result ${contentManager.contents.size}", false)

                    // Add a way to delete the content
                    val deleteButton = JButton("Clear Result")
                    deleteButton.addActionListener {
                        contentManager.removeContent(content, true)
                    }
                    deleteButton.size = deleteButton.preferredSize
                    panel.add(deleteButton, BorderLayout.NORTH)

                    val textArea = JBTextArea(output)
                    textArea.isEditable = false
                    textArea.size = textArea.preferredSize
                    textArea.foreground = JBColor.foreground()
                    textArea.background = JBColor.background()
                    textArea.border = JBEmptyBorder(10)
                    panel.add(JBScrollPane(textArea), BorderLayout.CENTER)

                    contentManager.addContent(content)
                    contentManager.setSelectedContent(content)
                    toolWindow.show()
                }
            }
        })
        processHandler.startNotify()
    }
}
