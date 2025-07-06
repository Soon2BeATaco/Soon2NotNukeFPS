package com.github.soon2beataco.soon2notnukefps.config.ui

import com.github.soon2beataco.soon2notnukefps.config.ConfigManager
import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils.modMessage
import com.github.soon2beataco.soon2notnukefps.utils.GeneralUtils.mc
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import com.github.soon2beataco.soon2notnukefps.config.ui.RenderUtils

class ClickGUI(private val configManager: ConfigManager) : GuiScreen() {
    private lateinit var fontRenderer: FontRenderer

    private var boxW = 300
    private var boxH = 200
    private var boxX = 0
    private var boxY = 0

    private var belowBoxW = 300
    private var belowBoxH = 20
    private var belowBoxX = 0
    private var belowBoxY = 0

    private val moduleButtons = mutableListOf<ModuleButton>()

    override fun initGui() {
        fontRenderer = mc.fontRendererObj
        val scaledResolution = ScaledResolution(mc)
        val screenWidth = scaledResolution.scaledWidth
        val screenHeight = scaledResolution.scaledHeight

        boxW = screenWidth / 3
        boxH = screenHeight / 3
        belowBoxW = boxW
        belowBoxH = 20

        boxX = (screenWidth - boxW) / 2
        boxY = (screenHeight - boxH) / 2
        belowBoxX = boxX
        belowBoxY = boxY + boxH + 8

        moduleButtons.clear()
        var buttonY = boxY + 30
        val buttonSpacing = 24

        configManager.features.forEach { feature ->
            val button = ModuleButton(
                name = feature.name,
                enabled = (feature as? ToggleableFeature)?.enabled ?: false,
                x = boxX + 10,
                y = buttonY
            ) { newState ->
                if (feature is ToggleableFeature) {
                    feature.enabled = newState
                    configManager.markDirty()
                }
            }
            moduleButtons.add(button)
            buttonY += buttonSpacing
        }

        modMessage("GUI centered at $boxX, $boxY", true)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        RenderUtils.drawRect(boxX, boxY, boxW, boxH, 0xFF222222.toInt())
        RenderUtils.drawRect(belowBoxX, belowBoxY, belowBoxW, belowBoxH, 0xDD222222.toInt())
        fontRenderer.drawString("buh", boxX + 10, boxY + 10, 0xFFFFFF.toInt())

        for (button in moduleButtons) {
            button.draw(mouseX, mouseY)
        }

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        modMessage("mouseButton $mouseButton, mouseX: $mouseX, mouseY $mouseY", true)
        if (mouseButton == 0) { // left click
            for (button in moduleButtons) {
                if (button.isMouseOver(mouseX, mouseY)) {
                    button.onClick()
                    modMessage("${button.name} toggled to ${button.enabled}", true)
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun doesGuiPauseGame(): Boolean = false
}
