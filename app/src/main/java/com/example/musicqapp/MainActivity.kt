package com.example.musicqapp

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private fun append(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }
    private fun remove(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }
    companion object{
        val albumNames = arrayOf("Kimi No Nawa","Naruto","Weathering with You","Your Lie in April", "Spirited Away", "Darling in the Franxx")
        val sourcepictures = arrayOf(R.drawable.your_name,R.drawable.naruto,R.drawable.weathering_with_you,R.drawable.yourlie,R.drawable.spirited_away,R.drawable.darling)
        var songQueue: Array<String> = emptyArray()
        val songsArray = arrayOf("Zenzenzense","Goshintai","Sakusen Kaigi","Nandemonaiya","Sparkle","Katawaredoki", "Toshokan","Mitsuha Theme",
                "Far Away","GO!!!","Swaying","Wind",
                "Grand Escape","Saving Hina","Sea in the Sky","Rain Again","We'll be Alright","Is there still anything that love can do","Voices of the Win","Celebration","Sky Clearing Up","Two Confessions",
                "Will you ever forget", "You see, I am a Violinist", "She is Beautiful", "The Scent of Spring", "Violence Over",
                "One Summer's Day", "A Road to somewhere", "The Empty Restaurant", "The Dragon Boy", "Sootballs", "No Face",
                "Vanquish", "Miel", "Dropping","CODE:002","VICTORIA","Torikago") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songList = findViewById<ListView>(R.id.allSongs)
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
        inflater.inflate(R.menu.queue_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.add_to_queue ->{
                songQueue = append(songQueue, songsArray[info.position])
                val snackbar = Snackbar.make(findViewById<ListView>(R.id.allSongs), "${songsArray[info.position]} moved to queue", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("GO TO QUEUE", {
                    startActivity(Intent(applicationContext, QueuedSongsActivity::class.java))
                })
                snackbar.show()
                return true
            }
            R.id.remove_from_queue ->{
                songQueue = remove(songQueue, songsArray[info.position])
                Toast.makeText(this, "${songsArray[info.position]} removed", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> super.onContextItemSelected(item) }
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}