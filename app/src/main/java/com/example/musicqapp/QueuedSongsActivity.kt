package com.example.musicqapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class QueuedSongsActivity : AppCompatActivity() {
    private fun remove(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.remove(element)
        return list.toTypedArray()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.songQueue)
        val qList = findViewById<ListView>(R.id.addedSongs)
        qList.adapter = adapter
    }
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
//        return when (item.itemId){
//            R.id.remove_song ->{
//                MainActivity.songsArray.drop(info.position)
//                Toast.makeText(this, "${MainActivity.songsArray[info.position]} removed", Toast.LENGTH_SHORT).show()
//                return true
//            }
//            else -> super.onContextItemSelected(item) }
//    }

}