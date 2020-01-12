package com.example.capstone
import com.google.gson.Gson

class SpotifyAPI {
    val url = "https://api.spotify.com/v1/search"
    val verb = "GET"
    val key1 = "market"
    val val1 = "US"
    val key2 = "limit"
    val val2 = "50"
    val key3 = "offset"
    val val3 = "5"
    val key4 = "Token"
    private val val4 = "BQB1ouxm_6Rfs3HQMIAeXK7iNxmLvuTGAFD63FmziCEJLIYuSb5FyBSq8b8bSTlT_jdhS4VfajE3Rfl2SL9I2n2MEfpSySpZTCFeQYcwhGvQzy3_z9x41bpV8Erygxh73veQVDP0Kh3ta9-4LDBFJ_-c0w7BSByTjdJw"

    fun onCreate(): String? {
        val response : khttp.responses.Response = khttp.get(
            url = url,
            params = mapOf(
                key1 to val1, key2 to val2, key3 to val3, key4 to val4
            )
        );
        val parsed = Gson().toJson(response).toString()
        return parsed
    }
}