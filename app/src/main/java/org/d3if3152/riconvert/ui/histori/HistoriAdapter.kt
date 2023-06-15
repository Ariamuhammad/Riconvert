package org.d3if3152.riconvert.ui.histori

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.ItemHistoriBinding
import org.d3if3152.riconvert.db.KonversiDb
import org.d3if3152.riconvert.db.KonversiEntity
import org.d3if3152.riconvert.model.convertCurrency
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter() :
//    ListAdapter<KonversiEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    RecyclerView.Adapter<HistoriAdapter.ViewHolder>(){

    var data: List<KonversiEntity?> = listOf();

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<KonversiEntity>() {
                override fun areItemsTheSame(
                    oldData: KonversiEntity, newData: KonversiEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: KonversiEntity, newData: KonversiEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: KonversiEntity) = with(binding) {
            Log.d("Bind function", item.toString())
            val hasilKonversi = item.convertCurrency()
            binding.buttonHapusData.setOnClickListener {
                hapusData(item.id, binding.root.context)
            }

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            kursTextView.text = root.context.getString(R.string.kurs_x,
                item.nominal.toFloat(), item.selectedCurrency.toString())
            hasilTextView.text = root.context.getString(R.string.hasil_x,
                hasilKonversi.hasil)
        }

        private fun hapusData(id: Long, context: Context) {
            val db = KonversiDb.getInstance(context)
            val KonversiDao = db.dao
            MaterialAlertDialogBuilder(context)
                .setMessage(context.getString(R.string.konfirmasi_hapus_satu))
                .setPositiveButton(context.getString(R.string.hapus)) { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.IO) {
                            KonversiDao.deleteHistory(id)
                        }
                    }
                }
                .setNegativeButton(context.getString(R.string.batal)) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position]?.let {
            holder.bind(it)
        }
    }


}
