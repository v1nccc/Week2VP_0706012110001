package com.example.week2vp_0706012110001

import Database.GlobalVar
import Model.Hewan
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isEmpty
import com.example.week2vp_0706012110001.databinding.ActivityInputBinding

import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.cardlayout.*

class InputActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityInputBinding
    private lateinit var hewan: Hewan
    private var imageUris: String = ""
    private var position = -10

    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
                val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY
                if (uri != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        baseContext.getContentResolver().takePersistableUriPermission(uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                FotoHewan.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
                imageUris = uri.toString()

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        imageUris = ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        var actionBar = getSupportActionBar()
        GetIntent()
        if (actionBar != null) {
            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            if (position == -1) {
                actionBar.setTitle("Tambah Hewan")
            } else {
                actionBar.setTitle("Edit Hewan")
            }
            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        inputeddata()


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.drawable.ic_baseline_arrow_back_24 -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1) {
            AddHewanData.text = "Save Changes"
            val hewan = GlobalVar.listDataHewan[position]
            imageUris = GlobalVar.listDataHewan[position].imageUri
            display(hewan)
        }
    }

    private fun display(hewan: Hewan) {
        NamaInput.editText?.setText(hewan.nama)
        UsiaInput.editText?.setText(hewan.usia.toString())
        JenisInput.editText?.setText(hewan.jenis)
        if (imageUris != "")
            FotoHewan.setImageURI(Uri.parse(imageUris))
    }

    private fun inputeddata(){
        FotoHewan.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
    AddHewanData.setOnClickListener() {
        var usia: Int
        val nama = NamaInput.editText?.text.toString().trim()
        val jenis = JenisInput.editText?.text.toString().trim()

        if (UsiaInput.editText?.text?.isEmpty() == true) {
            usia = -1
        } else {
            usia = Integer.parseInt(UsiaInput.editText?.text.toString().trim())
        }


        hewan = Hewan(nama,usia,jenis)
        if (imageUris != "")
            hewan.imageUri = imageUris

        var isCompleted: Boolean = true
        if (hewan.nama!!.isEmpty()) {
            NamaInput.error = "Tolong isi kolom Nama Hewan!"
            isCompleted = false
        } else {
            NamaInput.error = ""
        }


        if (hewan.usia!! < 0) {
            UsiaInput.error = "Tolong isi usia yang sesuai!"
            isCompleted = false
        } else {
            UsiaInput.error = ""
        }


        if (hewan.jenis!!.isEmpty()) {
            JenisInput.error = "Tolong isi kolom Jenis"
            isCompleted = false
        } else {
            JenisInput.error = ""
        }


        if (isCompleted) {
            if (position == -1) {
                GlobalVar.listDataHewan.add(hewan)
            } else {
                GlobalVar.listDataHewan[position] = hewan
            }
            finish()
        }
    }}

}