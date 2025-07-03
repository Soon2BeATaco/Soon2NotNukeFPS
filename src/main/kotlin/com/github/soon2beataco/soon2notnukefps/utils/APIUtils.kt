package com.github.soon2beataco.soon2notnukefps.utils

import com.github.soon2beataco.soon2notnukefps.utils.ChatUtils.modMessage
import com.github.soon2beataco.soon2notnukefps.utils.GeneralUtils.mc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

object APIUtils {
    private const val baseUrl = "https://tacoaddons.tacoskyblockapi.workers.dev/"

    /*
    * API request util, usage: val response = APIUtils.request("cata?username=Soon2BeAtaco_")
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
                connection.setRequestProperty("User-Agent", "Mozilla/5.0") // todo: stop this shit from being suspend so i dont have to use fucking coroutines on every instance
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
    suspend fun getUUID(ign: String = mc.thePlayer?.name ?: return): String? {
        val json = request("https://api.mojang.com/users/profiles/minecraft/$ign", false)
        return json?.let {
            JSONObject(it).getString("id")
        }
}
