package Adapter

import Interface.CardListener
import Model.Hewan
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2vp_0706012110001.R
import com.example.week2vp_0706012110001.databinding.CardlayoutBinding

class HewanListAdapter (val listhewan: ArrayList<Hewan>, val cardListener: CardListener):
    RecyclerView.Adapter<HewanListAdapter.viewHolder>(){
    class viewHolder (val itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView) {

        val binding = CardlayoutBinding.bind(itemView)
        fun setData(data:Hewan){
            binding.namahewan.text = data.nama
            binding.jenishewan.text = data.jenis
            binding.usiahewan.text = "Usia: " + data.usia.toString()
            if(data.imageUri.isNotEmpty()) {
                binding.gambarhewan.setImageURI(Uri.parse(data.imageUri))
            }
            binding.editbutton.setOnClickListener{
                cardListener1.onCardClick("edit", adapterPosition)
            }

            binding.deletebutton.setOnClickListener{
                cardListener1.onCardClick("delete",adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cardlayout, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listhewan[position])
    }

    override fun getItemCount(): Int {
        return listhewan.size
    }

}