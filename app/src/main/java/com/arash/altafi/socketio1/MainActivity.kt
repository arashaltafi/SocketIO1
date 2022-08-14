package com.arash.altafi.socketio1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import io.socket.client.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var btnCounter: MaterialButton
    private lateinit var tvCounter: MaterialTextView
    private lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        init()
    }

    private fun bindViews() {
        btnCounter = findViewById(R.id.counterBtn)
        tvCounter = findViewById(R.id.countTextView)
    }

    private fun init() {
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        mSocket = SocketHandler.getSocket()

        btnCounter.setOnClickListener {
            mSocket.emit("counter")
        }

        mSocket.on("counter") { args ->
            if (args[0] != null) {
                val counter = args[0] as Int
                runOnUiThread {
                    tvCounter.text = counter.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.closeConnection()
    }

}