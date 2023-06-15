package org.d3if3152.riconvert.ui.matauang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3152.riconvert.databinding.FragmentUangBinding
import org.d3if3152.riconvert.network.ApiStatus

class MataUangFragment : Fragment() {

    private val viewModel: MataUangViewModel by lazy {
        ViewModelProvider(this)[MataUangViewModel::class.java]
    }

    private lateinit var binding: FragmentUangBinding
    private lateinit var myAdapter: MataUangAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUangBinding.inflate(layoutInflater, container, false)
        myAdapter = MataUangAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context,
                RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner){
            myAdapter.updateData(it)
        }

        viewModel.getStatus().observe(viewLifecycleOwner){
            updateProgress(it)
        }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCES -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}