package com.example.musicqapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class QueuedSongsActivity : AppCompatActivity() {

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.queue_queue_menu, menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queueList)
        var qList = findViewById<ListView>(R.id.addedSongs)
        qList.adapter = adapter
        registerForContextMenu(qList)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        var info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.remove ->{
                MainActivity.queueList = remove(MainActivity.queueList, info.position)
                var adapter:ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queueList)
                adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queueList)
                val newQueue = findViewById<ListView>(R.id.addedSongs)
                newQueue.adapter = adapter
                Toast.makeText(this, "Song removed from Queue", Toast.LENGTH_SHORT).show()

                if(MainActivity.queueList.size == 0){
                    val intent = Intent(this, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel = NotificationChannel(
                                channelId,description, NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.BLACK
                        notificationChannel.enableVibration(true)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(this,channelId).setContentTitle("QUEUE EMPTY").setContentText("Add Songs to your Queue Now!")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                    }else{
                        builder = Notification.Builder(this)
                                .setContentTitle("Notifications Example")
                                .setContentText("This is a notification message")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(1234,builder.build())
                }
                return true
            }
            else -> super.onContextItemSelected(item) }
    }
    private fun remove(arr: Array<String>, element: Int): Array<String>{
        if(element < 0 || element >= arr.size){
            return arr
        }
        val list = arr.toMutableList()
        list.removeAt(element)
        return list.toTypedArray()
    }
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
}