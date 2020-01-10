package com.example.capstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

enum class PlayingState {
    PAUSED, PLAYING, STOPPED
}

object SpotifyService {
    private const val CLIENT_ID = "6febf7768beb40168b374e267504ec16"
    private const val  REDIRECT_URI = "com.example.capstone://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()
    fun connect(context: Context, handler: (connected: Boolean) -> Unit) {
        if (spotifyAppRemote?.isConnected == true) {
            handler(true)
            return
        }
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                this@SpotifyService.spotifyAppRemote = spotifyAppRemote
                handler(true)
            }
            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyService", throwable.message, throwable)
                handler(false)
            }

        }
        fun play(uri: String) {
            spotifyAppRemote?.playerApi?.play(uri)
        }

        fun resume() {
            spotifyAppRemote?.playerApi?.resume()
        }

        fun pause() {
            spotifyAppRemote?.playerApi?.pause()
        }

        fun playingState(handler: (PlayingState) -> Unit) {
            spotifyAppRemote?.playerApi?.playerState?.setResultCallback { result ->
                if (result.track.uri == null) {
                    handler(PlayingState.STOPPED)
                } else if (result.isPaused) {
                    handler(PlayingState.PAUSED)
                } else {
                    handler(PlayingState.PLAYING)
                }
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }
}
