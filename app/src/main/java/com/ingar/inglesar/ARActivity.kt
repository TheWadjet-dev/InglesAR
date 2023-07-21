package com.ingar.inglesar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class ARActivity : AppCompatActivity() {

    private var renderable: ModelRenderable? = null
    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

    }
}