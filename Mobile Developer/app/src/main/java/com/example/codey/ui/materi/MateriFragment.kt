package com.example.codey.ui.materi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codey.DetailMateriActivity
import com.example.codey.R
import com.example.codey.adapter.MateriAdapter


class MateriFragment : Fragment() {
    private lateinit var viewModel: MateriViewModel
    private lateinit var adapter: MateriAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_materi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rc_materi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(MateriViewModel::class.java)

        viewModel.materiList.observe(viewLifecycleOwner) { materiList ->
            adapter = MateriAdapter(materiList) { materi ->
                val intent = Intent(requireContext(), DetailMateriActivity::class.java).apply {
                    putExtra("EXTRA_MATERI", materi)
                }
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }
}
