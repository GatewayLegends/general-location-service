package com.gateway.gls

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gateway.gls.di.GLSInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testGLSLibrary()
    }

    private fun testGLSLibrary() {
        val glsManager = GLSInitializer(applicationContext).create()

        CoroutineScope(Dispatchers.IO).launch {
            glsManager.requestLocationUpdates().also {
                Timber.d("\nLOCATIONS: ${it.toData}\n")
            }
        }
    }
}
