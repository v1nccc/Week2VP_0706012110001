package Model

import Database.GlobalVar

class Kambing (nama:String,usia:Int,jenis:String,id:Int): Hewan(nama,usia,jenis,id) {



    override fun interaksi(): String {
        return "Mbek Mbek Mbek"
    }
}