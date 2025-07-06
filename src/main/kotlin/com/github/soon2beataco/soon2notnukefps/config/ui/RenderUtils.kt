package com.github.soon2beataco.soon2notnukefps.config.ui

import net.minecraft.client.gui.Gui
import org.lwjgl.opengl.GL11.*
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats

object RenderUtils {
    fun drawRect(x: Int, y: Int, w: Int, h: Int, color: Int) {
        Gui.drawRect(x, y, x + w, y + h, color)
    }

    fun drawRoundedRect(x: Int, y: Int, w: Int, h: Int, radius: Float, color: Int) {
        val tess = Tessellator.getInstance()
        val buffer = tess.worldRenderer

        val a = (color shr 24 and 0xFF) / 255f
        val r = (color shr 16 and 0xFF) / 255f
        val g = (color shr 8 and 0xFF) / 255f
        val b = (color and 0xFF) / 255f

        glPushAttrib(GL_ENABLE_BIT)
        glDisable(GL_TEXTURE_2D)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glColor4f(r, g, b, a)

        glBegin(GL_TRIANGLE_FAN)

        // center?
        glVertex2f(x + w / 2f, y + h / 2f)

        val segments = 20
        for (i in 0..segments) {
            val angle = Math.PI * 2.0 * i / segments
            val dx = (w / 2f - radius) * Math.cos(angle).toFloat()
            val dy = (h / 2f - radius) * Math.sin(angle).toFloat()
            glVertex2f(x + w / 2f + dx, y + h / 2f + dy)
        }

        glEnd()

        glPopAttrib()
    }
}
