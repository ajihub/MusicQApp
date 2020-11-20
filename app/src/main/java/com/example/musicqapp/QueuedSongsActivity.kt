package com.example.musicqapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class QueuedSongsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.songQueue)
        val qList = findViewById<ListView>(R.id.addedSongs)
        qList.adapter = adapter
    }
}