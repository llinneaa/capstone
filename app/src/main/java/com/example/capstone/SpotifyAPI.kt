package com.example.capstone
import com.google.gson.Gson
import khttp.responses.Response
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import kotlin.reflect.typeOf

class SpotifyAPI {
    val url = "https://api.spotify.com/v1/me/tracks"
    //    val verb = "GET"
    val key1 = "market"
    val val1 = "US"
    val key2 = "limit"
    val val2 = "1"
    val key3 = "offset"
    val val3 = "5"
    private val val4 =
        "Bearer BQCjn57wzfv71io7CUmDw-aD44ld9YutbleqpqH7AdsMWEmY1A3bvNWDefnUpz1qSzrkBYKfz2Kz-emrlmmys6nim9RWBIRYeCdxjX54ASphhcrEGXGvovWS2OTEnmAcBttUhZGMqLmMHpllWWqz2YBxLg70H6cCYl9U"

    fun onCreate(): String {
        var responseJson = "";
        var requestUrl = url;
        requestUrl += "?";
        requestUrl += key1;
        requestUrl += "=";
        requestUrl += val1;
        requestUrl += "&";
        requestUrl += key2;
        requestUrl += "=";
        requestUrl += val2;
        requestUrl += "&";
        requestUrl += key3;
        requestUrl += "=";
        requestUrl += val3;

        val headerMap = mutableMapOf<String, String>();
        headerMap.put("Authorization", val4);
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");

        var finished = false;
        runBlocking {
                GlobalScope.launch {
                    var response = khttp.get(url = requestUrl, headers = headerMap);
                    var track = response.jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("track").getString("uri");
                    responseJson = track;
                    finished = true;
                }
            }

            while(!finished){
                Thread.sleep(100);
            }

            return responseJson;

    }
}