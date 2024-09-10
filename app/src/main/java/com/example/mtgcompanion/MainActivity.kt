package com.example.mtgcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar BarajasFragment al iniciar la app
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, BarajasFragment())
            .commit()
    }
}