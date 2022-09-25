package Model

abstract class Hewan (
    var nama:String?,
    var usia:Int?,
    var jenis:String?,
    var id:Int,

)
{
    fun makan(makan: String): String {
        return "Kamu memberi makan hewan dengan $makan"
    }

    fun <Int> memberimakan(makan: Int): String {
        return "Kamu memberi makan hewan dengan Rerumputan"
    }
    abstract fun interaksi(): String
    var imageUri: String = ""



}