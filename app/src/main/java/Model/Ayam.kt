package Model

import Database.GlobalVar


class Ayam(nama:String,usia:Int,jenis:String,id:Int): Hewan(nama,usia,jenis,id) {


    override fun interaksi(): String {
        return "Pok Pok"
    }
}