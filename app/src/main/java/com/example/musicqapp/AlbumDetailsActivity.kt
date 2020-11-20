package com.example.musicqapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        val bundle = intent.extras
        var songOrganizer: Array<String> = emptyArray()
        if (bundle != null) {
            when (bundle.getInt("position")){
                0 -> songOrganizer = MainActivity.songsArray.sliceArray(0..7)
                1 -> songOrganizer = MainActivity.songsArray.sliceArray(8..11)
                2 -> songOrganizer = MainActivity.songsArray.sliceArray(12..21)
                3 -> songOrganizer = MainActivity.songsArray.sliceArray(22..26)
                4 -> songOrganizer = MainActivity.songsArray.sliceArray(27..31)
                5 -> songOrganizer = MainActivity.songsArray.sliceArray(32..37)
            }
        }
        findViewById<ImageView>(R.id.albumImg).setImageResource(MainActivity.sourcepictures[bundle?.getInt("position")!!])
        findViewById<TextView>(R.id.albumNameTxt).setText(bundle.getString("name"))
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songOrganizer)
        val songList = findViewById<ListView>(R.id.albumSongList)
        songList.adapter = adapter
    }
}