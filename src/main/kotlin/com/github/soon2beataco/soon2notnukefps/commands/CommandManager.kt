package com.github.soon2beataco.soon2notnukefps.commands

import com.github.stivais.commodore.Commodore
import com.github.stivais.commodore.nodes.Executable
import com.github.stivais.commodore.nodes.LiteralNode
import com.github.stivais.commodore.utils.findCorrespondingNode
import com.github.stivais.commodore.utils.getArgumentsRequired
import com.github.stivais.commodore.utils.getRootNode
import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils
import com.github.soon2beataco.soon2notnukefps.commands.impl.devCommand

object CommandManager {
    private val commands: ArrayList<Commodore> = arrayListOf(
        devCommand
    )
    fun add(vararg commands: Commodore) {
        commands.forEach { commodore ->
            CommandManager.commands.add(commodore)
        }
    }
    fun register() {
        commands.forEach { commodore ->
            commodore.register { problem, cause ->
                val builder = StringBuilder()

                builder.append("§c$problem\n\n")
                builder.append("  Did you mean to run:\n\n")
                buildTreeString(cause, builder)

                findCorrespondingNode(getRootNode(cause), "help")?.let {
                    builder.append("\n  §7Run /${getArgumentsRequired(it).joinToString(" ")} for more help.")
                }
                ChatUtils.modMessage(builder.toString())
            }
        }
    }
    private fun buildTreeString(from: LiteralNode, builder: StringBuilder) {
        for (node in from.children) {
            when (node) {
                is LiteralNode -> buildTreeString(node, builder)
                is Executable -> {
                    builder.append("  /${getArgumentsRequired(from).joinToString(" ")}")
                    builder.append("\n")
                } // todo: fix.
            }
        }
    }
}