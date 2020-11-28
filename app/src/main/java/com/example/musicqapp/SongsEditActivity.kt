package com.example.musicqapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.musicqapp.models.Song

class SongsEditActivity : AppCompatActivity() {
    lateinit var editSongBtn: Button
    lateinit var titleTxt: EditText
    lateinit var artistTxt: EditText
    lateinit var albumTxt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)
        val songId = intent.getIntExtra("songId", 0)
        val databaseHandler = SongsTableHandler(this)
        val song = databaseHandler.readOne(songId)

        titleTxt = findViewById(R.id.editTitleTxt)
        artistTxt = findViewById(R.id.editArtistTxt)
        albumTxt = findViewById(R.id.editAlbumTxt)
        editSongBtn = findViewById(R.id.editSongBtn)

        titleTxt.setText(song.title)
        artistTxt.setText(song.artist)
        albumTxt.setText(song.album)
        editSongBtn.setOnClickListener {
            //set the fields from the form
            val title = titleTxt.text.toString()
            val artist = artistTxt.text.toString()
            val album = albumTxt.text.toString()

            val newSong = Song(id = songId, title = title, artist = artist, album = album)
            if(databaseHandler.update(newSong)){
                Toast.makeText(applicationContext, "Song was Updated", Toast.LENGTH_LONG).show()
                MainActivity.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.songs)
                MainActivity.songList.adapter = MainActivity.adapter
                clearFields()
            }else{
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun clearFields() {
        titleTxt.text.clear()
        artistTxt.text.clear()
        albumTxt.text.clear()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sub_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.queue ->{
                startActivity(Intent(this, QueuedSongsActivity::class.java))
                true
            }
            R.id.songs ->{
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.album ->{
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}