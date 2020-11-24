package com.example.musicqapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class AlbumDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        val bundle = intent.extras
        songOrganizer = MainActivity.mainList[bundle?.getInt("position")!!]
        findViewById<ImageView>(R.id.albumImg).setImageResource(MainActivity.strictures[bundle?.getInt("position")!!])
        findViewById<TextView>(R.id.albumNameTxt).setText(bundle.getString("name"))
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songOrganizer)
        val songList = findViewById<ListView>(R.id.albummainList)
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
        inflater.inflate(R.menu.album_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val bundle = intent.extras
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.remove_from_albums ->{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are you sure you want to remove this song from ${bundle?.getString("name")} Album?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", DialogInterface.OnClickListener{
                            _, _ ->
                            MainActivity.mainList[bundle?.getInt("position")!!] = remove(MainActivity.mainList[bundle.getInt("position")], info.position)
                            songOrganizer = MainActivity.mainList[bundle.getInt("position")]
                            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songOrganizer)
                            val songList = findViewById<ListView>(R.id.albummainList)
                            songList.adapter = adapter
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener{
                            dialog, _ ->
                            dialog.cancel()
                        })
                val alert = dialogBuilder.create()
                alert.setTitle("Remove from albums")
                alert.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    private lateinit var songOrganizer: Array<String>
    private lateinit var adapter: ArrayAdapter<String>
    private fun remove(arr: Array<String>, element: Int): Array<String>{
        if(element < 0 || element >= arr.size){
            return arr }
        val result = arr.toMutableList()
        result.removeAt(element)
        return result.toTypedArray()
    }
}