package com.example.yparakh.disastermanagement

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener( OnCompleteListener{ task ->
            if (!task.isSuccessful) {
                Log.w("", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            // Get new Instance ID token
            val token = task.result?.token

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
//            Log.d("", msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }
        )
        sendRegistrationToServer(p0)
    }

//    override fun onNewToken(token: String?) {
//        Log.d("", "Refreshed token: $token")
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(token)
//    }

    private fun sendRegistrationToServer(token: String?){
        token.let {
            val request = StringRequest(Request.Method.POST, "",
                Response.Listener { _it ->
                    Log.d("success: ", _it)
                },
                Response.ErrorListener { _it ->
                    Log.d("error: ", _it.message)
                })
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue
        }
    }
}
