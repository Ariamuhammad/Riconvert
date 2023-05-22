package org.d3if3152.riconvert.ui.histori

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3152.riconvert.R
import org.d3if3152.riconvert.databinding.FragmentHistoriBinding
import org.d3if3152.riconvert.db.KonversiDb
import org.d3if3152.riconvert.db.KonversiEntity

class HistoriFragment : Fragment() {

    private val  viewModel: HistoriViewModel by lazy {
        val db = KonversiDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this,factory)[HistoriViewModel::class.java]
    }

    private lateinit var  binding: FragmentHistoriBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)

        val mainAdapter = HistoriAdapter();

        viewModel.data.observe(viewLifecycleOwner) {
            mainAdapter.data = it;
            mainAdapter.notifyDataSetChanged();

            if(it.size > 0) {
                binding.emptyView.visibility = GONE
                binding.recyclerView.visibility = VISIBLE

            } else {
                binding.recyclerView.visibility = GONE
                binding.emptyView.visibility = VISIBLE

            }
//            Log.d("Data", it.toString())
        }

        with(binding) {
            recyclerView.adapter = mainAdapter
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.data.observe(viewLifecycleOwner, {
//            Log.d("HistoriFragment", "Jumlah data: ${it.size}")
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            clearData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.clearData()
            }
            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}