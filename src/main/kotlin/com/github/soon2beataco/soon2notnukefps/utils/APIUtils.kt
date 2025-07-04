package com.github.soon2beataco.soon2notnukefps.utils

import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils.modMessage
import com.github.soon2beataco.soon2notnukefps.utils.GeneralUtils.mc
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

val jsonParserr = Json {
    ignoreUnknownKeys = true;
    isLenient = true
}


@Serializable
data class MojangProfile(
    val id: String,
    val name: String
)

object APIUtils {
    private const val baseUrl = "https://tacoaddons.tacoskyblockapi.workers.dev/"

    /*
    * API request util, usage: val response = APIUtils.request("cata?username=Soon2BeATaco_")
    *
    * @param endpoint the specific API endpoint to request, e.g., "cata?username=username".
    * @returns the response body as a String or null if the request fails.
    */

    suspend fun request(endpoint: String = "", useBaseURL: Boolean = true): String? {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                val url = if (useBaseURL) URL(baseUrl + endpoint) else URL(endpoint) // untested but prolly works ig
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("User-Agent", "Mozilla/5.0") // TODO: Refactor this to a non-suspend version or wrap coroutine calls internally to avoid needing suspend everywhere.
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    modMessage("Error with API. Please send this message to the Discord: $responseCode ${connection.responseMessage}")
                    null
                }
            } catch (e: Exception) {
                modMessage("Error with API. Please send this message to the Discord! ${e.message}")
                null
            } finally {
                connection?.disconnect()
            }
        }
    }
    suspend fun getUUID(ign: String = mc.thePlayer.name): String? {
        val json = request("https://api.mojang.com/users/profiles/minecraft/$ign", false) ?: return null

        return try {
            val profile = jsonParserr.decodeFromString<MojangProfile>(json)
            profile.id
        } catch (e: Exception) {
            modMessage("Error parsing JSON: ${e.message}")
            null
        }
    }
}
