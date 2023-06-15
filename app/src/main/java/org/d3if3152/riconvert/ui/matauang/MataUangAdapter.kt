package org.d3if3152.riconvert.ui.matauang

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3152.riconvert.MataUang
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.ListItemBinding
import org.d3if3152.riconvert.network.MataUangApi

class MataUangAdapter : RecyclerView.Adapter<MataUangAdapter.ViewHolder>(){

    private val data = mutableListOf<MataUang>()

    fun updateData(newaData: List<MataUang>){
        data.clear()
        data.addAll(newaData)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(uang: MataUang) = with(binding){
            namaTextView.text = uang.nama
            uangTextView.text = uang.kurs
            Glide.with(imageView.context)
                .load(MataUangApi.getUangUrl(uang.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)

            root.setOnClickListener{
                val message = root.context.getString(R.string.message, uang.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}