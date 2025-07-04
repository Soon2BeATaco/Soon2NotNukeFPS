package com.github.soon2beataco.soon2notnukefps.features

import com.github.soon2beataco.soon2notnukefps.config.ConfigManager
import com.google.gson.JsonObject

interface FeatureConfig {
    val name: String
    var configManager: ConfigManager?
    fun load(json: JsonObject)
    fun toJson(): JsonObject
}
