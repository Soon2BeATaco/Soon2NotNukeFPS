package com.github.soon2beataco.soon2notnukefps.features

import com.google.gson.JsonObject
import com.github.soon2beataco.soon2notnukefps.config.ConfigManager

class UwUAddonsHider : FeatureConfig {
    override val name = "UwUAddonsHider"
    override var configManager: ConfigManager? = null

    var enabled = false
        set(value) {
            field = value
            configManager?.markDirty()
        }

    override fun load(json: JsonObject) {
        enabled = json.get("enabled")?.asBoolean ?: true
    }

    override fun toJson(): JsonObject {
        return JsonObject().apply {
            addProperty("enabled", enabled)
        }
    }
}
