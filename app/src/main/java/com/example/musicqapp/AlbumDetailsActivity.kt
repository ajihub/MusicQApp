package com.example.musicqapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
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
                0 -> songOrganizer = MainActivity.mainList.sliceArray(0..7)
                1 -> songOrganizer = MainActivity.mainList.sliceArray(8..11)
                2 -> songOrganizer = MainActivity.mainList.sliceArray(12..21)
                3 -> songOrganizer = MainActivity.mainList.sliceArray(22..26)
                4 -> songOrganizer = MainActivity.mainList.sliceArray(27..31)
                5 -> songOrganizer = MainActivity.mainList.sliceArray(32..37)
            }
        }
        findViewById<ImageView>(R.id.albumImg).setImageResource(MainActivity.strictures[bundle?.getInt("position")!!])
        findViewById<TextView>(R.id.albumNameTxt).setText(bundle.getString("name"))
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songOrganizer)
        val songList = findViewById<ListView>(R.id.albumSongList)
        songList.adapter = adapter
        registerForContextMenu(songList)
    }
    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater

        //Add the menu items for the context menu
        inflater.inflate(R.menu.album_menu, menu)
    }
}