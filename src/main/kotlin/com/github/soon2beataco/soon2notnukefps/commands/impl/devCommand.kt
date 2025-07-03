package com.github.soon2beataco.soon2notnukefps.commands.impl

import com.github.stivais.commodore.Commodore
import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils
import com.github.soon2beataco.soon2notnukefps.utils.APIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
}