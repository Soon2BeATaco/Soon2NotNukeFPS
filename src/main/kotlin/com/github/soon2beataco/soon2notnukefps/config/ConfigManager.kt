package com.github.soon2beataco.soon2notnukefps.config

import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils.modMessage
import com.github.soon2beataco.soon2notnukefps.features.FeatureConfig
import com.github.soon2beataco.soon2notnukefps.features.UwUAddonsHider
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ConfigManager(path: File) {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var rootJson = JsonObject()
    private val configDir = File(path, "soon2notnukefps")
    private val configFile = File(configDir, "tacosettings.json")

    private val features: List<FeatureConfig> = listOf(
        UwUAddonsHider()
        // add more when more features ig.?? im only testing it with 1 tho so gege
    ).onEach { it.configManager = this }

    private var dirty = false

    init {
        try {
            if (!configDir.exists()) configDir.mkdirs()
            if (!configFile.exists()) configFile.createNewFile()
        } catch (e: Exception) {
            modMessage("Error initializing config: $e")
            e.printStackTrace()
        }
    }

    fun loadConfig() {
        try {
            FileReader(configFile).use { reader ->
                rootJson = JsonParser().parse(reader).asJsonObject
            }
            modMessage("Config loaded from file: ${configFile.absolutePath}", true)
        } catch (e: Exception) {
            modMessage("Config empty or invalid, using defaults!", true)
            rootJson = JsonObject()
        }
        features.forEach { feature ->
            val json = rootJson.getAsJsonObject(feature.name) ?: JsonObject()
            feature.load(json)
        }
        println("Config loaded successfully!")
    }

    fun saveConfig() {
        features.forEach { feature ->
            rootJson.add(feature.name, feature.toJson())
        }
        try {
            FileWriter(configFile).use { writer ->
                gson.toJson(rootJson, writer)
            }
            dirty = false
            println("Config saved successfully!")
        } catch (e: Exception) {
            println("Error saving config: $e")
            e.printStackTrace()
        }
    }

    fun markDirty() {
        dirty = true
        saveConfig()
    }

    fun <T : FeatureConfig> getFeature(featureClass: Class<T>): T? {
        return features.firstOrNull { featureClass.isInstance(it) } as? T
    }
}
