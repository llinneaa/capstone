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

