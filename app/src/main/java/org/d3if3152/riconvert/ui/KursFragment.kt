package org.d3if3152.riconvert.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.FragmentKursBinding
import org.d3if3152.riconvert.model.Kurs

class KursFragment : Fragment() {
    private lateinit var binding: FragmentKursBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selectedCurrency = viewModel.getSelectedCurrency().value ?: "Dollar"
        val kurs = createKursObject(selectedCurrency)
        updateUI(kurs)

        viewModel.getSelectedCurrency().observe(viewLifecycleOwner, { currency ->
            val kurs = createKursObject(currency)
            updateUI(kurs)
        })
    }

    private fun createKursObject(currency: String): Kurs {
        return when (currency) {
            "Dollar" -> Kurs(name = "Dollar", rate = 15059f)
            "Euro" -> Kurs(name = "Euro", rate = 13250f)
            else -> Kurs(name = "", rate = 0f)
        }
    }


    private fun updateUI(kurs: Kurs) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Kurs ${kurs.name}"

        when (kurs.name) {
            "Dollar" -> {
                binding.imageView.setImageResource(R.drawable.dollar)
                binding.kursDollarNow.text = kurs.rate.toString()
            }
            "Euro" -> {
                binding.imageView.setImageResource(R.drawable.euro)
                binding.kursDollarNow.text = kurs.rate.toString()
            }
            else -> {

            }
        }
    }

}