package com.example.yparakh.disastermanagement

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener
import kotlinx.android.synthetic.main.activity_notification_receiver.*


class MainActivity : AppCompatActivity() {

    val NotificationId = 1
    private lateinit var mMessageListener: MessageListener
    private lateinit var mMessage: Message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_receiver)
        mMessageListener = MyMessageListener()
        mMessage = Message("Hello World".toByteArray())
        createNotificationChannel()
    }


    override fun onStart() {
        super.onStart()
        subscribe()
        publish()
    }

    override fun onDestroy() {
        unPublish()
        unSubscribe()
        super.onDestroy()
    }

    inner class MyMessageListener : MessageListener() {

        override fun onFound(message: Message) {
            notifyUser(String(message.content))
        }

        override fun onLost(message: Message) {
        }
    }

    private fun subscribe() {
        Nearby.getMessagesClient(this).subscribe(mMessageListener)
    }

    private fun publish() {
        Nearby.getMessagesClient(this).publish(mMessage)
    }

    private fun unSubscribe() {
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener)
    }

    private fun unPublish() {
        Nearby.getMessagesClient(this).unpublish(mMessage)
    }

    fun notifyUser(message: String)
    {
        error_msg.text = "ADMIN: $message"
        displayNotification(message)
    }

    private fun displayNotification(message: String)
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, "2")
        builder.setSmallIcon(R.drawable.ic_error_outline_red_24dp)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_hearing))
        builder.setContentTitle("Follow Instructions")
        builder.setContentText(message)
        builder.priority = NotificationCompat.PRIORITY_MAX
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(NotificationId, builder.build())
    }

    private fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Disaster Management"
            val descriptionText = "Follow instructions to get rescued"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("2", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
