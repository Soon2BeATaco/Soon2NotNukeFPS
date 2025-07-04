package com.github.soon2beataco.soon2notnukefps.commands.impl

import com.github.stivais.commodore.Commodore
import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils
import com.github.soon2beataco.soon2notnukefps.utils.APIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.soon2beataco.soon2notnukefps.ConfigHolder.configManager
import com.github.soon2beataco.soon2notnukefps.features.UwUAddonsHider

val devCommand = Commodore("tacodev") {
    literal("testMessage").runs {
        ChatUtils.modMessage("test message from /tacodev testMessage, shouldnt be dev message.")
        ChatUtils.modMessage("test message from /tacodev testMessage, should be dev message.", true)
    }
    literal("testAPI").runs {
        CoroutineScope(Dispatchers.IO).launch { //todo: no more Dispatchers.IO, fix ts
            val response = APIUtils.request("cata?username=Soon2BeAtaco_")
    
            if (response != null) {
                ChatUtils.modMessage("API returned with : $response")
            } else {
                ChatUtils.modMessage("response returned null !??")
            }
        }
    }
    literal("getuuid").runs {
        CoroutineScope(Dispatchers.IO).launch {
            val uuid = APIUtils.getUUID()
            if (uuid != null) {
                ChatUtils.modMessage("uuid api returned : '$uuid'")
            } else {
                ChatUtils.modMessage("wow it seems mojang no work rn!!!")
            }
        }
    }
    literal("configtest").runs {
        val feature = configManager.getFeature(UwUAddonsHider::class.java)
        if (feature != null) {
            feature.enabled = !feature.enabled
            val state = feature.enabled
            ChatUtils.modMessage("uwuaddonshider is now $state")
        } else {
            ChatUtils.modMessage("Feature UwUAddonsHider not found!")
        }
    }

}