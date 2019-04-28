package com.example.yparakh.disastermanagement

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener
import kotlinx.android.synthetic.main.activity_notification_receiver.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException


class MainActivity : AppCompatActivity() {

    private lateinit var mMessageListener: MessageListener
    private lateinit var mMessage: Message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_receiver)
        mMessageListener = MyMessageListener()
        val client = ActiveMqClient(this)
        client.setupConnection()
        client.connect("tcp://10.1.122.128:1883", "androidAdmin", "floor1")
    }


    override fun onStart() {
        super.onStart()
        subscribe()
    }

    override fun onDestroy() {
        unPublish()
        unSubscribe()
        super.onDestroy()
    }

    inner class MyMessageListener : MessageListener() {

        override fun onFound(message: Message) {
            error_msg.text = "ADMIN: ${String(message.content)}" //remove this line of code
//            TODO show the user what you have received
        }

        override fun onLost(message: Message) {
            error_msg.text = ""
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


    inner class ActiveMqClient(private val context: Context) {

        private lateinit var client: MqttAndroidClient
        private lateinit var connectOptions: MqttConnectOptions

        fun setupConnection() {
            connectOptions = MqttConnectOptions()
            connectOptions.userName = "admin"
            connectOptions.password = "admin".toCharArray()
            connectOptions.isAutomaticReconnect = true

        }

        fun connect(url: String, clientId: String, topic: String) {

            client = MqttAndroidClient(context, url, clientId)
            try {
                client.connect(connectOptions, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        subscribe(topic)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken, e: Throwable) {
                        e.printStackTrace()
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }

        }


        private fun subscribe(subscribeTopic: String) {
            try {
                client.subscribe(subscribeTopic, 0) { _, message ->
                    mMessage = Message(message.toString().toByteArray())
                    runOnUiThread {
                        publish()
                        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    Handler().post {
                        publish()
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
            }

        }
    }

}
