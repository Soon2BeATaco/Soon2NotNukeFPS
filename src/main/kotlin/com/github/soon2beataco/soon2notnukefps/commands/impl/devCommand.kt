package com.github.soon2beataco.soon2notnukefps.commands.impl

import com.github.stivais.commodore.Commodore
import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils

val devCommand = Commodore("tacodev") {
    literal("testMessage").runs {
        ChatUtils.modMessage("test message from /tacodev testMessage, shouldnt be dev message.")
        ChatUtils.modMessage("test message from /tacodev testMessage, should be dev message.", true)
    }
    literal("sendMessageTest").runs {
        ChatUtils.sendMessage("test")
        ChatUtils.runCommand("say test")
    }
}