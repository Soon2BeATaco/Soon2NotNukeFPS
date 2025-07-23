package com.github.soon2beataco.soon2notnukefps

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import com.github.soon2beataco.soon2notnukefps.commands.CommandManager

@Mod(modid = "soonnotnukefps", useMetadata = true)
public class soon2notnukefps {
    @Mod.EventHandler
    public fun init(event: FMLInitializationEvent?) {
        MinecraftForge.EVENT_BUS.register(this)
        CommandManager.register()
    }
}

