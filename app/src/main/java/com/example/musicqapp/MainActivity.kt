package com.example.musicqapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.musicqapp.models.Song
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object{
        val albumNames = arrayOf("Kimi No Nawa","Naruto","Weathering with You","Your Lie in April", "Spirited Away", "Darling in the Franxx")
        val strictures = arrayOf(R.drawable.your_name,R.drawable.naruto,R.drawable.weathering_with_you,R.drawable.yourlie,R.drawable.spirited_away,R.drawable.darling)
        var queueList: Array<String> = emptyArray()
        val mainList = arrayOf(arrayOf("Zenzenzense","Goshintai","Sakusen Kaigi","Nandemonaiya","Sparkle","Katawaredoki", "Toshokan","Mitsuha Theme"),
                               arrayOf("Far Away","GO!!!","Swaying","Wind"),
                               arrayOf("Grand Escape","Saving Hina","Sea in the Sky","Rain Again","We'll be Alright","Is there still anything that love can do","Voices of the Win","Celebration","Sky Clearing Up","Two Confessions"),
                               arrayOf("Will you ever forget", "You see, I am a Violinist", "She is Beautiful", "The Scent of Spring", "Violence Over"),
                               arrayOf("One Summer's Day", "A Road to somewhere", "The Empty Restaurant", "The Dragon Boy", "Sootballs", "No Face"),
                               arrayOf("Vanquish", "Miel", "Dropping","CODE:002","VICTORIA","Torikago"))

        lateinit var adapter: ArrayAdapter<Song>
        lateinit var songs: MutableList<Song>
        lateinit var songsTableHandler: SongsTableHandler
        lateinit var songList: ListView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songsTableHandler = SongsTableHandler(this)
        songs = songsTableHandler.read()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,songs)
        songList = findViewById<ListView>(R.id.allSongs)
        songList.adapter = adapter
        registerForContextMenu(songList)

        val Lowerbutton: View = findViewById(R.id.AddIcon)
        Lowerbutton.setOnClickListener {
            startActivity(Intent(this, SongsAddActivity::class.java))
        }
        val Refreshbutton: View = findViewById(R.id.RefreshIcon)
        Refreshbutton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Song List Refreshed", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.queue_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.add_to_queue ->{
                queueList = add(queueList, songs[info.position].toString())
                val snackbar = Snackbar.make(findViewById<ListView>(R.id.allSongs), "${songs[info.position].toString()} moved to queue", Snackbar.LENGTH_LONG)
                snackbar.setAction("GO TO QUEUE") { startActivity(Intent(applicationContext, QueuedSongsActivity::class.java)) }
                snackbar.show()
                return true
            }
            R.id.remove_from_queue ->{
                queueList = remove(queueList, songs[info.position].toString())
                Toast.makeText(this, "${songs[info.position].toString()} removed from Queue", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.Edit_song ->{
                val songId = songs[info.position].id
                val intent = Intent(applicationContext, SongsEditActivity::class.java)
                intent.putExtra("songId", songId)
                startActivity(intent)
                true
            }
            R.id.Delete_song ->{
                val song = songs[info.position]
                if(songsTableHandler.delete(song)){
                    songs.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Song Deleted from List", Toast.LENGTH_LONG).show()
            }else{
                    Toast.makeText(applicationContext, "Oops something went wrong!", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.queue ->{
                startActivity(Intent(this, QueuedSongsActivity::class.java))
                true }
            R.id.songs ->{
                true }
            R.id.album ->{
                startActivity(Intent(this, AlbumsActivity::class.java))
                true }
            R.id.add_song ->{
                startActivity(Intent(this, SongsAddActivity::class.java))
                true
            }
            R.id.refresh ->{
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Song List Refreshed", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun add(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }
    private fun remove(list: Array<String>, element: String): Array<String>{
        val list = list.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }
}