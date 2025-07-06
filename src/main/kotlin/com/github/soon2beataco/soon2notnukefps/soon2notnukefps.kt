package com.github.soon2beataco.soon2notnukefps

import com.github.soon2beataco.soon2notnukefps.commands.CommandManager
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.Minecraft
import com.github.soon2beataco.soon2notnukefps.config.ui.ClickGUI

@Mod(modid = "soonnotnukefps", useMetadata = true)
class soon2notnukefps {

    companion object {
        val mc = Minecraft.getMinecraft()
        var screenToOpen: ClickGUI? = null
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        CommandManager.register()
        ConfigHolder.initConfig()
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(GuiHandler)
    }
}

object GuiHandler {
    private val mc = Minecraft.getMinecraft()

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        val screen = soon2notnukefps.screenToOpen
        if (screen != null) {
            mc.displayGuiScreen(screen)
            soon2notnukefps.screenToOpen = null
        }
    }
}
