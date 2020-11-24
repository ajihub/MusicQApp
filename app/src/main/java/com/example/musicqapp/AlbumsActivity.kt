package com.example.musicqapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import kotlinx.android.synthetic.main.album_entry.view.*

class AlbumsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        adapter = AlbumAdapter(this)
        findViewById<GridView>(R.id.albumList).adapter = adapter }
    class AlbumAdapter : BaseAdapter {
        var context: Context? = null
        val albsList = MainActivity.albumNames
        constructor(context: Context) : super(){
            this.context = context }
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
            albsview.AlbumView.setOnClickListener {
                val intent = Intent(context, AlbumDetailsActivity::class.java)
                intent.putExtra("name", album)
                intent.putExtra("songList", MainActivity.mainList)
                intent.putExtra("position", position)
                context!!.startActivity(intent) }
            albsview.AlbumView.setImageResource(MainActivity.strictures[position])
            albsview.name.text = album
            return albsview
        }
    }
    var adapter: AlbumAdapter? = null
}