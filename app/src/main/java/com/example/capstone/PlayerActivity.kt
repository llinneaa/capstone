package com.example.capstone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*
import java.lang.System

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupViews()
        setupListeners()
    }

    override fun onStop() {
        super.onStop()
        SpotifyService.disconnect()
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

    fun showPauseButton() {
        pauseButton.visibility = View.VISIBLE
    }

    fun showPlayButton() {
        playButton.visibility = View.VISIBLE
    }

    fun showResumeButton() {
        resumeButton.visibility = View.VISIBLE
    }

    private fun setupListeners() {
        playButton.setOnClickListener {
            SpotifyService.play("spotify:track:5TV5x7u5Tnx2i47ccKXVex")
            showPauseButton()
//            SpotifyAPI()
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
