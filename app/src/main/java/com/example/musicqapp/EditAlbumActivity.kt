package com.example.musicqapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.musicqapp.models.Album

class EditAlbumActivity : AppCompatActivity() {
    lateinit var editAlbumBtn: Button
    lateinit var titleTxt: EditText
    lateinit var releaseDateTxt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)
        val albumId = intent.getIntExtra("albumId", 0)
        val databaseHandler = AlbumsTableHandler(this)
        val album = databaseHandler.readOne(albumId)
        titleTxt = findViewById(R.id.editAlbumTitleTxt)
        releaseDateTxt = findViewById(R.id.editReleaseDateTxt)
        editAlbumBtn = findViewById(R.id.editAlbumBtn)

        titleTxt.setText(album.title)
        releaseDateTxt.setText(album.releaseDate)

        editAlbumBtn.setOnClickListener {
            val title = titleTxt.text.toString()
            val artist = releaseDateTxt.text.toString()

            val newAlbum = Album(id = albumId, title = title, releaseDate = artist)
            if (databaseHandler.update(newAlbum)) {
                Toast.makeText(applicationContext, "Album was Updated", Toast.LENGTH_LONG).show()
                AlbumsActivity.adapter = AlbumsActivity.AlbumAdapter(this, AlbumsActivity.albums)
                AlbumsActivity.albumGrid.adapter = AlbumsActivity.adapter
                clearFields()
            } else {
                Toast.makeText(applicationContext, "Oops something went wrong!", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun clearFields() {
        titleTxt.text.clear()
        releaseDateTxt.text.clear()
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