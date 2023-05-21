package org.d3if3152.riconvert

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.d3if3152.riconvert.databinding.ActivityMainBinding
import org.d3if3152.riconvert.model.HasilKonversi
import org.d3if3152.riconvert.ui.KursViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: KursViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(KursViewModel::class.java)

        binding.button.setOnClickListener { convertCurrency() }
        binding.buttonReset.setOnClickListener { resetNominal() }
        viewModel.getHasilKonversi().observe(this, {showResult(it)})
    }

    private fun showResult(result: HasilKonversi?) {
        if (result == null) return

        binding.hasilConvert.text = getString(R.string.hasil_convert, result.hasil)
    }

    private fun resetNominal() {
        binding.nominal.setText(null)
        binding.hasilConvert.setText(null)
    }

    private fun convertCurrency() {
        val nominal = binding.nominal.text.toString()
        val selectedCurrency = binding.uang.selectedItem.toString()

        if (TextUtils.isEmpty(nominal)) {
            Toast.makeText(this, R.string.nominal_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.convertCurrency(
            nominal, selectedCurrency
        )
    }
}
