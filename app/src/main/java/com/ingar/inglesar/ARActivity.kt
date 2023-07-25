package com.ingar.inglesar

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.content.ContextCompat.startActivity
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ARActivity : AppCompatActivity() {

  
    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)

        ModelAR()

    }


    private fun ModelAR(){
        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
        
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor: Anchor = hitResult.createAnchor()
            val uri: Uri = android.net.Uri.parse(
                "android.resource://" + packageName + "/assets/lowpoly_novel"
            )

            ModelRenderable.builder()
                .setSource(this, uri)
                .build()
                .thenAccept{modelRenderable -> addModelToScene(anchor, modelRenderable)}
        }
        
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW);
        
        val uri: Uri? = android.net.Uri.parse(
            "https://arvr.google.com/scene-viewer/1.0"
        ).buildUpon()
            .appendQueryParameter("file", "android.resource://" + packageName + "/assets/lowpoly_novel")
            .appendQueryParameter("mode", "ar_only")
            .build()

        sceneViewerIntent.setData(uri)
        sceneViewerIntent.setPackage("com.google.ar.core")
        startActivity(sceneViewerIntent)
    }

    private fun addModelToScene(anchor: Anchor, modelRenderable: ModelRenderable){

        val node = AnchorNode(anchor)
        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.setParent(node)
        transformableNode.renderable = modelRenderable

        arFragment.arSceneView.scene.addChild(node)
        transformableNode.select()

    }
}