package com.github.soon2beataco.soon2notnukefps

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraft.client.renderer.GlStateManager
import com.github.soon2beataco.soon2notnukefps.commands.CommandManager
import com.github.soon2beataco.soon2notnukefps.ConfigHolder

@Mod(modid = "soonnotnukefps", useMetadata = true)
class soon2notnukefps {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        CommandManager.register()
        ConfigHolder.initConfig()
    }
}
