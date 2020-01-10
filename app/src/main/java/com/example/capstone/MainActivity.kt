package com.example.capstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// app imports
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectButton.setOnClickListener {
            SpotifyService.connect(this) {
                val intent = Intent(this, PlayerActivity::class.java)
                startActivity(intent)
            }
        }

    }
}

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


class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupViews()
        setupListeners()
    }

    private fun setupViews () {
        SpotifyService.playingState {
            when(it) {
                PlayingState.PLAYING -> showPauseButton()
                PlayingState.STOPPED -> showPlayButton()
                PlayingState.PAUSED -> showResumeButton()
            }
        }
    }

    private fun setupListeners() {
        playButton.setOnClickListener {
            SpotifyService.play("spotify:track:5HkW47BxKNgkW2bSNghlNa")
            showPauseButton()
        }

        pauseButton.setOnClickListener {
            SpotifyService.pause()
            showResumeButton()
        }

        resumeButton.setOnClickListener {
            SpotifyService.resume()
            showPauseButton()
        }
    }
}
