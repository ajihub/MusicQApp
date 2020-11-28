package com.example.musicqapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.musicqapp.models.Album
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.album_entry.view.*

class AlbumsActivity : AppCompatActivity() {
    companion object {
        var adapter: AlbumAdapter? = null
        lateinit var albumsTableHandler: AlbumsTableHandler
        lateinit var albums: MutableList<Album>
        lateinit var albumGrid: GridView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        albumsTableHandler = AlbumsTableHandler(this)
        albums = albumsTableHandler.read()
        adapter = AlbumAdapter(this, albums)
        albumGrid = findViewById<GridView>(R.id.albumList)
        albumGrid.adapter = adapter
        registerForContextMenu(albumGrid)
        albumGrid.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(applicationContext, AlbumDetailsActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }
        val addBtn: View = findViewById(R.id.AddIconAlbum)
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddAlbum::class.java))
        }
        val refreshBtn: View = findViewById(R.id.RefreshIconAlbum)
        refreshBtn.setOnClickListener {
            startActivity(Intent(this, AlbumsActivity::class.java))
            Toast.makeText(this, "Album List Refreshed", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.album_options_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.edit_album -> {
                val albumId = albums[info.position].id
                val intent = Intent(applicationContext, EditAlbumActivity::class.java)
                intent.putExtra("albumId", albumId)
                startActivity(intent)
                true
            }
            R.id.delete_album ->{
                val album = albums[info.position]
                if(albumsTableHandler.delete(album)){
                    albums.removeAt(info.position)
                    adapter?.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Album Deleted", Toast.LENGTH_LONG).show()
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
        inflater.inflate(R.menu.albums_sub_menu, menu)
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
            else -> super.onOptionsItemSelected(item)
        }
    }
    class AlbumAdapter : BaseAdapter {
        var context: Context? = null
        val albsList : MutableList<Album>
        constructor(context: Context, albumList: MutableList<Album>) : super() {
            this.context = context
            this.albsList = albumList
        }
        override fun getCount(): Int {
            return albsList.size }
        override fun getItem(position: Int): Any {
            return albsList[position] }
        override fun getItemId(position: Int): Long {
            return position.toLong() }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val album = this.albsList[position]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var albsview = inflater.inflate(R.layout.album_entry, null)
            albsview.isEnabled = false
            albsview.AlbumView.setOnClickListener {
                val intent = Intent(context, AlbumDetailsActivity::class.java)
                intent.putExtra("name", album.toString())
                context!!.startActivity(intent) }
            albsview.AlbumView.setImageResource(MainActivity.strictures[position])
            albsview.name.text = album.toString()
            return albsview
        }
    }
}