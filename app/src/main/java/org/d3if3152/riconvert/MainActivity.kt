package org.d3if3152.riconvert

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if3152.riconvert.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { hitungKonversi() }
        binding.buttonReset.setOnClickListener { resetNominal() }

    }

    private fun resetNominal() {
        binding.nominal.setText(null)
        binding.hasilConvert.setText(null)
    }

    private fun hitungKonversi(){
        val rupiah = binding.nominal.text.toString()

        if (TextUtils.isEmpty(rupiah)) {
            Toast.makeText(this, R.string.nominal_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val selectedValue = binding.uang.selectedItem.toString()
        if(selectedValue == "Dollar"){
            val dollarRp = 15059
            val hasil = rupiah.toFloat() / dollarRp
            binding.hasilConvert.text = NumberFormat.getCurrencyInstance(Locale.US).format(hasil)

        }else if(selectedValue == "Euro"){
            val euroRp = 16371
            val hasil = rupiah.toFloat() / euroRp
            binding.hasilConvert.text = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(hasil)

        }
    }
}