package com.github.soon2beataco.soon2notnukefps.utils

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object ChatUtils {
    private val mc = Minecraft.getMinecraft()

    fun clientMessage(message: Any) {
        val ChatComponent = ChatComponentText("$message")
        mc.thePlayer?.addChatMessage(ChatComponent)
    }
    fun modMessage(message: Any) {
        val ChatComponent = ChatComponentText("§8[§b§ltaco§f§8]§f $message")
        mc.thePlayer?.addChatMessage(ChatComponent)
    }
    fun sendMessage(message: Any) {
        mc.thePlayer?.sendChatMessage(message.toString())
    }
    fun runCommand(cmd: Any) {
        sendMessage("/$cmd")
    }
}
