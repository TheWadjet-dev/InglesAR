package com.ingar.inglesar

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class ARActivity : AppCompatActivity() {

    private var renderable: ModelRenderable? = null
    private lateinit var arFragment: ArFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)

        arFragment =  supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        initUI()
        backRefresh()
    }

    private fun backRefresh() {
        findViewById<FloatingActionButton>(R.id.fab_regresar).setOnClickListener{
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
        }

        findViewById<FloatingActionButton>(R.id.fab_refresh).setOnClickListener {
            val refresh = Intent(this, ARActivity::class.java)
            startActivity(refresh)
        }
    }

    private fun initUI() {
        findViewById<ImageView>(R.id.imageView0).setOnClickListener{
            downloadModel("models/dog.glb")
            Snackbar.make(findViewById(R.id.imageView0), "La traducción de Perro es Dog", 6000).show()
            arFragment()
        }

        findViewById<ImageView>(R.id.imageView1).setOnClickListener{
            downloadModel("models/duck.glb")
            Snackbar.make(findViewById(R.id.imageView1), "La traducción de Pato es Duck", 6000).show()
            arFragment()
        }

        findViewById<ImageView>(R.id.imageView2).setOnClickListener{
            downloadModel("models/cat.glb")
            Snackbar.make(findViewById(R.id.imageView2), "La traducción de Gato es Cat", 6000).show()
            arFragment()
        }
    }


    private fun arFragment(){
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (renderable == null){return@setOnTapArPlaneListener}
            val anchor: Anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            val node = TransformableNode(arFragment.transformationSystem)
            node.renderable = renderable
            node.scaleController.minScale = 0.3f
            node.scaleController.maxScale = 0.8f
            node.worldScale = Vector3(0.5f, 0.5f, 0.5f)
            node.setParent(anchorNode)
            node.select()
        }
    }

    private fun downloadModel(URL_ruta: String?) {
        var renderableSource = RenderableSource.builder()
            .setSource(this, Uri.parse(URL_ruta),RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.CENTER)
            .build()

        ModelRenderable.builder()
            .setSource(this, renderableSource)
            .build()
            .thenAccept { modelRenderable ->
                renderable = modelRenderable
                Toast.makeText(this@ARActivity, "Carga completa toque una superficie", Toast.LENGTH_LONG).show()
            }
            .exceptionally { throwable ->
                Toast.makeText(this@ARActivity, "No se puedo cargar el elemento 3D", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }
    }


}