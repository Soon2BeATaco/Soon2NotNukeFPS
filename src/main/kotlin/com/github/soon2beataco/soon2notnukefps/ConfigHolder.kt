package com.github.soon2beataco.soon2notnukefps

import com.github.soon2beataco.soon2notnukefps.config.ConfigManager
import java.io.File

object ConfigHolder {
    lateinit var configManager: ConfigManager

    fun initConfig() {
        configManager = ConfigManager(File("config"))
        configManager.loadConfig()
    }
}
