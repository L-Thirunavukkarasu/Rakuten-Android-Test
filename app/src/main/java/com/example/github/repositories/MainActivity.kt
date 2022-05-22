package com.example.github.repositories

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.github.repositories.utils.NetworkConnection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this))

        //check device internet status
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->

            if (isConnected) {
                //if internet is connected - show the actual screen
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, MainFragment())
                    .commit()
            } else {
                //else - show the error screen
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, InternetStatusFragment())
                    .commit()
            }
        })
    }
}