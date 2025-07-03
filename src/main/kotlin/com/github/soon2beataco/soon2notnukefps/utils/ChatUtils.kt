package com.github.soon2beataco.soon2notnukefps.utils

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object ChatUtils {
    private val mc: Minecraft = Minecraft.getMinecraft()

    fun modMessage(message: Any?, dev: Boolean = false) {
        if (dev) {
            val ign = mc.thePlayer?.name
            if (ign == "Soon2BeATaco_" || ign == "catgirloblong") { // ill add devmode in config later when there is a config
                val chatComponent = ChatComponentText("§8[§b§lTacoDev§r§8] §r$message")
                mc.thePlayer?.addChatMessage(chatComponent)
            } else {
                return
            }
        } else {
            val chatComponent = ChatComponentText("§8[§b§ltaco§r§8] §r$message")
            mc.thePlayer?.addChatMessage(chatComponent)
        }
    }
    fun sendMessage(message: Any) {
        mc.thePlayer?.sendChatMessage(message.toString())
    }
    fun runCommand(cmd: Any) {
        sendMessage("/$cmd")
    }

}
