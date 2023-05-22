package org.d3if3152.riconvert.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.FragmentHitungBinding
import org.d3if3152.riconvert.model.HasilKonversi

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { convertCurrency() }
        binding.buttonReset.setOnClickListener { resetNominal() }

        viewModel.getHasilKonversi().observe(viewLifecycleOwner, { hasilKonversi ->
            showResult(hasilKonversi)
        })
        binding.buttonShare.setOnClickListener { shareData() }

        binding.buttonKursView.setOnClickListener {
            findNavController().navigate(R.id.action_hitungFragment_to_kursFragment)
        }
    }

    private fun showResult(hasilKonversi: HasilKonversi?) {
        if (hasilKonversi != null) {
            binding.hasilConvert.text = getString(R.string.formatKonversi, hasilKonversi.hasil)
            binding.buttonGroup.visibility = View.VISIBLE
        } else {
            binding.buttonGroup.visibility = View.GONE
        }
    }

    private fun shareData() {
        val selectedId = binding.uang.selectedItem

        val message = getString(R.string.bagikan_template,
            selectedId,
            binding.nominal.text
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }


    private fun resetNominal() {
        binding.nominal.setText(null)
        binding.hasilConvert.setText(null)
        binding.buttonGroup.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun convertCurrency() {
        val nominal = binding.nominal.text.toString()
        val selectedCurrency = binding.uang.selectedItem.toString()

        if (TextUtils.isEmpty(nominal)) {
            Toast.makeText(context, R.string.nominal_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.convertCurrency(nominal, selectedCurrency)
    }
}

