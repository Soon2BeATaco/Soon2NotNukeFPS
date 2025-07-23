package com.github.soon2beataco.soon2notnukefps.commands.impl

import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils
import com.github.stivais.commodore.Commodore
import net.minecraft.client.Minecraft

val apiCommand = Commodore("taco") {
    literal("cata").runs {
        ChatUtils.modMessage("this thing works")
    }
}



