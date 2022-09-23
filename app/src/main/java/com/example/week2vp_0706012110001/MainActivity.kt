package com.example.week2vp_0706012110001

import Adapter.HewanListAdapter
import Database.GlobalVar
import Model.Hewan
import Interface.CardListener
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardListener {


    private val adapter = HewanListAdapter(GlobalVar.listDataHewan, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CheckPermissions()
//        addDummyData()
        setupRecyclerView()
        Listener()

    }
    private fun setupRecyclerView(){

        val layoutManager = GridLayoutManager(baseContext,1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter= adapter
        if (GlobalVar.listDataHewan.size>0){
            addhewantext.isVisible = false
        }
    }

    private fun addDummyData(){
        GlobalVar.listDataHewan.add(Hewan("A",1,"AA",))
        GlobalVar.listDataHewan.add(Hewan("B",2,"BB",))
        GlobalVar.listDataHewan.add(Hewan("C",3,"CC",))
        GlobalVar.listDataHewan.add(Hewan("D",4,"DD",))

        adapter.notifyDataSetChanged()
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        addhewantext.isVisible = GlobalVar.listDataHewan.size <= 0

    }
    private fun Listener(){
        addhewan.setOnClickListener(){
            val intent = Intent(this, InputActivity::class.java).apply {
                putExtra("position", -1)
            }
            startActivity(intent)


        }

    }
    private fun CheckPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCardClick(change: String, position: Int) {
        if(change=="edit"){
            val intent = Intent(this, InputActivity::class.java).apply {
                putExtra("position", position)
            }
            startActivity(intent)}

        if(change=="delete"){

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Hapus Hewan")
            builder.setMessage("Yakin ingin hapus hewan anda ini?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                GlobalVar.listDataHewan.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()





        }


    }

}