package org.d3if3152.riconvert.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.FragmentHitungBinding
import org.d3if3152.riconvert.model.HasilKonversi


class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun  onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { convertCurrency() }
        binding.buttonReset.setOnClickListener { resetNominal() }
        viewModel.getHasilKonversi().observe(requireActivity(), {showResult(it)})
    }


    private fun showResult(result: HasilKonversi?) {
        if (result == null) return

        binding.hasilConvert.text = getString(R.string.formatKonversi, result.hasil)
    }

    private fun resetNominal() {
        binding.nominal.setText(null)
        binding.hasilConvert.setText(null)
    }

    private fun convertCurrency() {
        val nominal = binding.nominal.text.toString()
        val selectedCurrency = binding.uang.selectedItem.toString()

        if (TextUtils.isEmpty(nominal)) {
            Toast.makeText(context, R.string.nominal_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.convertCurrency(
            nominal, selectedCurrency
        )
    }
}
