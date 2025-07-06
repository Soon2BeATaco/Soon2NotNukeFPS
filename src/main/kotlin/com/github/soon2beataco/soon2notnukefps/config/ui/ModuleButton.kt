package com.github.soon2beataco.soon2notnukefps.config.ui

import net.minecraft.client.gui.FontRenderer
import com.github.soon2beataco.soon2notnukefps.utils.GeneralUtils.mc
import com.github.soon2beataco.soon2notnukefps.config.ui.RenderUtils

data class ModuleButton(
    val name: String,
    var enabled: Boolean,
    var x: Int,
    var y: Int,
    val w: Int = 100,
    val h: Int = 20,
    val onToggle: (Boolean) -> Unit
) {
    private val fontRenderer: FontRenderer = mc.fontRendererObj

    fun draw(mouseX: Int, mouseY: Int) {
        val color = if (enabled) 0xFF00FF00.toInt() else 0xFFFF0000.toInt()
        RenderUtils.drawRect(x, y, w, h, color)
        fontRenderer.drawString(name, x + 5, y + 6, 0xFFFFFFFF.toInt())
    }

    fun isMouseOver(mouseX: Int, mouseY: Int): Boolean {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h
    }

    fun onClick() {
        enabled = !enabled
        onToggle(enabled)
    }
}

