package com.example.musicqapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private fun append(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    companion object{
        var songQueue: Array<String> = emptyArray()
        val songsArray = arrayOf("Zenzenzense","Goshintai","Sakusen Kaigi","Nandemonaiya","Sparkle","Katawaredoki", "Toshokan"
            ,"Mitsuha Theme","Far Away","GO!!!","Swaying","Wind"
            ,"Grand Escape","Saving Hina","Sea in the Sky","Rain Again","We'll be Alright","Is there still anything that love can do","Voices of the Win","Celebration","Sky Clearing Up","Two Confessions")
        val albumArray = arrayOf("Kimi No Nawa","Naruto","Weathering with You","Your Lie in April")
        val albumPics = arrayOf(R.drawable.your_name,R.drawable.naruto,R.drawable.weathering_with_you,R.drawable.yourlie)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songList = findViewById<ListView>(R.id.songList)
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
        inflater.inflate(R.menu.item_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.add_to_queue ->{
                songQueue = append(songQueue, songsArray[info.position])
                Toast.makeText(this, "${songsArray[info.position]} moved to queue", Toast.LENGTH_LONG).show()
                return true
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
                startActivity(Intent(this, QueueActivity::class.java))
                true
            }
            R.id.songs ->{
                true
            }
            R.id.album ->{
                startActivity(Intent(this, AlbumActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}