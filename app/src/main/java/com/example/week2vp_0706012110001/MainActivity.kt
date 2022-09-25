package com.example.week2vp_0706012110001

import Adapter.HewanListAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.*
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


    private var adapter = HewanListAdapter(GlobalVar.listDataHewan, this)
    private var adapter2 = HewanListAdapter(GlobalVar.listDataHewan2, this)
    var lastfilter= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CheckPermissions()
        addDummyData()
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
        if(GlobalVar.listDataHewan.size<1) {
            GlobalVar.listDataHewan.add(Sapi("Cow", 1, "Sapi",0))
            GlobalVar.listDataHewan.add(Ayam("Chicken", 2, "Ayam",1))
            GlobalVar.listDataHewan.add(Kambing("Lamb", 3, "Kambing",2))
        }
        adapter.notifyDataSetChanged()
    }
    override fun onResume() {
        super.onResume()
        if (GlobalVar.listDataHewan.size>0){
            addhewantext.isVisible = false
        }
        GlobalVar.listDataHewan2.clear()
        if (lastfilter == "ayam"){
            for (i in GlobalVar.listDataHewan) {
                if (i is Ayam) {
                    GlobalVar.listDataHewan2.add(i)
                }

            }
            recyclerView.adapter = adapter2
        }

        else  if (lastfilter == "kambing"){

            for (i in GlobalVar.listDataHewan) {
                if (i is Kambing) {
                    GlobalVar.listDataHewan2.add(i)
                }

            }
            recyclerView.adapter = adapter2
        }

        else  if (lastfilter == "sapi"){
            for (i in GlobalVar.listDataHewan) {
                if (i is Sapi) {
                    GlobalVar.listDataHewan2.add(i)
                }

            }
            recyclerView.adapter = adapter2

        }
        else  if (lastfilter == "all"){
            for (i in GlobalVar.listDataHewan) {

                    GlobalVar.listDataHewan2.add(i)
            }
            recyclerView.adapter = adapter2

        }
        adapter.notifyDataSetChanged()
        adapter2.notifyDataSetChanged()
        if(GlobalVar.listDataHewan.size <= 0)
        addhewantext.isVisible = true

    }
    private fun Listener(){
        addhewan.setOnClickListener(){
            val intent = Intent(this, InputActivity::class.java).apply {
                putExtra("position", -1)
            }
            startActivity(intent)


        }

        showayam.setOnClickListener(){
                GlobalVar.listDataHewan2.clear()

                    for (i in GlobalVar.listDataHewan) {
                        if (i is Ayam) {
                            GlobalVar.listDataHewan2.add(i)
                        }

            }
            recyclerView.adapter = adapter2

            lastfilter ="ayam"
        }


        showkambing.setOnClickListener(){
            GlobalVar.listDataHewan2.clear()

            for (i in GlobalVar.listDataHewan) {
                if (i is Kambing) {
                    GlobalVar.listDataHewan2.add(i)
                }

            }
            recyclerView.adapter = adapter2

            lastfilter ="kambing"
        }

        showsapi.setOnClickListener(){
            GlobalVar.listDataHewan2.clear()

            for (i in GlobalVar.listDataHewan) {
                if (i is Sapi) {
                    GlobalVar.listDataHewan2.add(i)
                }
            }
            recyclerView.adapter = adapter2

            lastfilter ="sapi"
        }

        showall.setOnClickListener(){
            GlobalVar.listDataHewan2.clear()

            for (i in GlobalVar.listDataHewan) {
                    GlobalVar.listDataHewan2.add(i)

            }
            recyclerView.adapter = adapter2
            lastfilter="all"

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
        if (change == "edit") {
            val intent = Intent(this, InputActivity::class.java).apply {
                putExtra("position", position)
            }
            startActivity(intent)
        }

        if (change == "delete") {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Hapus Hewan")
            builder.setMessage("Yakin ingin hapus hewan anda ini?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

                for(i in 0..GlobalVar.listDataHewan.size-1){
                    if(GlobalVar.listDataHewan[i].id == position){
                        GlobalVar.listDataHewan.removeAt(i)
                        break
                    }
                }
              onResume()

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()


        }

        if (change == "feed") {
            for (i in 0..GlobalVar.listDataHewan.size - 1) {
                if (GlobalVar.listDataHewan[i].id == position) {
                    if (GlobalVar.listDataHewan[i] is Ayam) {
                        Toast.makeText(
                            this,
                            GlobalVar.listDataHewan[i].makan("Biji"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            GlobalVar.listDataHewan[i].memberimakan<Int>(1),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    break
                }
            }

        }

        if (change == "interact") {
            for (i in 0..GlobalVar.listDataHewan.size - 1) {
                if (GlobalVar.listDataHewan[i].id == position) {
                    Toast.makeText(
                        this,
                        GlobalVar.listDataHewan[i].interaksi(),
                        Toast.LENGTH_SHORT
                    ).show()
                    break
                }
            }


        }
    }
}