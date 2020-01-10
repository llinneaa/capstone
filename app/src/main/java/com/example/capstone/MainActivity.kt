package com.example.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.google.gson.Gson ...... serializing into JSON
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

import android.content.Context
//import android.util.Log

import android.graphics.Bitmap
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Info
//import android.R



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

//    override fun onStart() {
//        super.onStart()
//
//        val connectionParams = ConnectionParams.Builder(clientId)
//            .setRedirectUri(redirectUri)
//            .showAuthView(true)
//            .build()
//
//        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
//            override fun onConnected(appRemote: SpotifyAppRemote) {
//                spotifyAppRemote = appRemote
//                Log.d("MainActivity", "Connected! Yay!")
//                // Now you can start interacting with App Remote
//                connected()
//            }
//
//            override fun onFailure(throwable: Throwable) {
//                Log.e("MainActivity", throwable.message, throwable)
//                // Something went wrong when attempting to connect! Handle errors here
//            }
//        })
//    }
//
//    private fun connected() {
////        spotifyAppRemote.playerApi.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
//
//        spotifyAppRemote.playerApi.subscribeToPlayerState().setEventCallback {
//            val track: Track = it.track
//            Log.d("MainActivity", track.name + " by " + track.artist.name)
//        }
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//        spotifyAppRemote?.let {
//            SpotifyAppRemote.disconnect(it)
//        }
//
//    }
}
