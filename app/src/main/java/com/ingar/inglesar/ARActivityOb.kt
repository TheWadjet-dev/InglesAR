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
import com.google.firebase.storage.internal.Sleeper

class ARActivityOb : AppCompatActivity() {

    private var renderable: ModelRenderable? = null
    private lateinit var arFragment3: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arob)

        arFragment3 =  supportFragmentManager.findFragmentById(R.id.arFragment3) as ArFragment

        initUI()
        backRefresh()
    }

    private fun backRefresh() {
        findViewById<FloatingActionButton>(R.id.fab_regresar).setOnClickListener{
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
        }

        findViewById<FloatingActionButton>(R.id.fab_refresh).setOnClickListener {
            val refresh = Intent(this, ARActivityOb::class.java)
            startActivity(refresh)
        }
    }

    private fun initUI() {
        findViewById<ImageView>(R.id.imageView0).setOnClickListener{
            downloadModel("models/lowpoly_novel.glb")
            Snackbar.make(findViewById(R.id.imageView0), "La traducción de Libro es Book", 6000).show()

            Snackbar.make(findViewById(R.id.imageView0), "The bible is a very old book", 4000).show()
            arFragment()
        }

        findViewById<ImageView>(R.id.imageView1).setOnClickListener{
            downloadModel("models/modern_chair.glb")
            Snackbar.make(findViewById(R.id.imageView1), "La traducción de Silla es Chair", 6000).show()
            Snackbar.make(findViewById(R.id.imageView1), "The school chairs are new", 6000).show()
            arFragment()
        }

        findViewById<ImageView>(R.id.imageView2).setOnClickListener{
            downloadModel("models/m9_bayonet.glb")
            Snackbar.make(findViewById(R.id.imageView2), "La traducción de Plato es Dish", 6000).show()
            Snackbar.make(findViewById(R.id.imageView2), "The knife is used to cut the bread", 6000).show()
            arFragment()
        }
    }


    private fun arFragment(){
        arFragment3.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (renderable == null){return@setOnTapArPlaneListener}
            val anchor: Anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment3.arSceneView.scene)
            val node = TransformableNode(arFragment3.transformationSystem)
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
            .setSource(this, Uri.parse(URL_ruta), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.CENTER)
            .build()

        ModelRenderable.builder()
            .setSource(this, renderableSource)
            .build()
            .thenAccept { modelRenderable ->
                renderable = modelRenderable
                Toast.makeText(this@ARActivityOb, "Carga completa toque una superficie", Toast.LENGTH_LONG).show()
            }
            .exceptionally { throwable ->
                Toast.makeText(this@ARActivityOb, "No se puedo cargar el elemento 3D", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }
    }

}