package com.github.soon2beataco.soon2notnukefps.features

import com.google.gson.JsonObject
import com.github.soon2beataco.soon2notnukefps.config.ConfigManager
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

class UwUAddonsHider : FeatureConfig {
    override val name = "UwUAddonsHider"
    override var configManager: ConfigManager? = null

    var enabled = true
        set(value) {
            field = value
            configManager?.markDirty()
        }

    var hideUwUAddons = true
        set(value) {
            field = value
            configManager?.markDirty()
        }

    var hideMiscMods = true
        set(value) {
            field = value
            configManager?.markDirty()
        }

    private val badModsArrow = listOf("UwUaddons", "PurpleAddons", "MeowAddons")
    private val badModsBracket = listOf("Skyblocker", "Dingus", "HG")
    private val badModsBigArrow = listOf("SkyMoe")

    private val patternArrow: Pattern = Pattern.compile("Party > .+ (.+): (.+) » (.+)", Pattern.CASE_INSENSITIVE)
    private val patternBigArrow: Pattern = Pattern.compile("Party > .+ (.+): (.+) >> (.+)", Pattern.CASE_INSENSITIVE)
    private val patternBracket: Pattern = Pattern.compile("Party > .+ (.+): \\[(.+)] (.+)", Pattern.CASE_INSENSITIVE)
    private val patternBlood: Pattern = Pattern.compile("Party > .+ (.+): (Blood Done|Blood Ready)!", Pattern.CASE_INSENSITIVE)
    private val patternRelic: Pattern = Pattern.compile("Party > .+ (.+): (\\w+) Relic", Pattern.CASE_INSENSITIVE)

    override fun load(json: JsonObject) {
        enabled = json.get("enabled")?.asBoolean ?: true
        hideUwUAddons = json.get("hideUwUAddons")?.asBoolean ?: true
        hideMiscMods = json.get("hideMiscMods")?.asBoolean ?: true
    }

    override fun toJson(): JsonObject {
        return JsonObject().apply {
            addProperty("enabled", enabled)
            addProperty("hideUwUAddons", hideUwUAddons)
            addProperty("hideMiscMods", hideMiscMods)
        }
    }

    @SubscribeEvent
    fun onMessage(event: ClientChatReceivedEvent) {
        if (!enabled) return

        val msg = event.message.unformattedText

        if (hideUwUAddons) {
            val matcherArrow = patternArrow.matcher(msg)
            if (matcherArrow.matches()) {
                val modType = matcherArrow.group(2)
                if (badModsArrow.any { modType.contains(it, ignoreCase = true) }) {
                    event.isCanceled = true
                    return
                }
            }
        if (hideMiscMods) {
            val matcherBigArrow = patternBigArrow.matcher(msg)
            if (matcherBigArrow.matches()) {
                val modType = matcherBigArrow.group(2)
                if (badModsBigArrow.any { modType.contains(it, ignoreCase = true) }) {
                    event.isCanceled = true
                    return
                }
            }
            val matcherBracket = patternBracket.matcher(msg)
            if (matcherBracket.matches()) {
                val modType = matcherBracket.group(2)
                if (badModsBracket.any { modType.contains(it, ignoreCase = true) }) {
                    event.isCanceled = true
                    return
                }
            }
            if (patternBlood.matcher(msg).matches()) {
                event.isCanceled = true
                return
            }
            if (patternRelic.matcher(msg).matches()) {
                event.isCanceled = true
            }
        }
    }
}}
