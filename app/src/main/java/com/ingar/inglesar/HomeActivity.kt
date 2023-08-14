package com.ingar.inglesar


import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.ingar.inglesar.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityHomeBinding

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var video: VideoView
    private var currentVideoPosition: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        video()
        AR()
        ARFrutas()
        ARObjects()
    }

    private fun video() {
        video = findViewById<VideoView>(R.id.videoView)
        val uri: Uri = Uri.parse(
            "android.resource://" + packageName + "/raw/vidhome"
        )
        video.setVideoURI(uri)
        video.start()

        video.setOnPreparedListener { mp ->
            mediaPlayer = mp
            mediaPlayer.isLooping = true
        }
    }

    override fun onPause(){
        super.onPause()

        currentVideoPosition = mediaPlayer.currentPosition
        video.pause()
    }

    override fun onResume(){
        super.onResume()

        video.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.release()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "Avances", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Mensajes", Toast.LENGTH_SHORT).show()
            R.id.nav_item_six -> logout()
        }
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    private fun AR(){
        binding.btnStart.setOnClickListener{
            val ar = Intent(this, ARActivity::class.java)
            startActivity(ar)
        }
    }

    private fun ARFrutas(){
        binding.btnStart2.setOnClickListener{
            val ar2 = Intent(this, ARActivityFr::class.java)
            startActivity(ar2)
        }
    }

    private fun ARObjects(){
        binding.btnStart3.setOnClickListener{
            val ar3 = Intent(this, ARActivityOb::class.java)
            startActivity(ar3)
        }
    }
}