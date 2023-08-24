package com.ingar.inglesar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.ui.res.integerArrayResource
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.integrity.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.ingar.inglesar.databinding.ActivityHomeBinding
import com.ingar.inglesar.databinding.ActivityShowPdfBinding

private lateinit var drawer: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle
private lateinit var listView: ListView
private lateinit var databaseReference: DatabaseReference
private lateinit var uploads: List<UploadPDF>
lateinit var binding: ActivityShowPdfBinding

class ShowPDF : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_pdf)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout_pdf)

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

        val navigationView: NavigationView = findViewById(R.id.nav_view_pdf)
        navigationView.setNavigationItemSelectedListener(this)

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            var pdfupload: UploadPDF = uploads.get(i)
            var intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.setType("PDFs/Uploads")
            intent.setData(Uri.parse(pdfupload.getUrl()))
            startActivity(intent)
        }
        binding.fabUpload.setOnClickListener {
            var upload = Intent(this, UploadPDF::class.java)
            startActivity(upload)
        }

        ViewAllFiles()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_home -> home()
            R.id.nav_item_one -> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "Avances", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Mensajes", Toast.LENGTH_SHORT).show()
            R.id.nav_item_four -> uploadPDF()
            R.id.nav_item_six -> logout()
        }
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun uploadPDF() {
        var pdf: Intent = Intent(this, ShowPDF::class.java)
        startActivity(pdf)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    private fun home() {
        var home: Intent = Intent(this, HomeActivity::class.java)
        startActivity(home)
    }

    private fun ViewAllFiles(){

    }
}