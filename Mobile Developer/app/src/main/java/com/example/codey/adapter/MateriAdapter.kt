package com.example.codey.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codey.R
import com.example.codey.ui.materi.DataMateri

class MateriAdapter(
    private val materiList: List<DataMateri>,
    private val onClick: (DataMateri) -> Unit
) : RecyclerView.Adapter<MateriAdapter.MateriViewHolder>() {

    inner class MateriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMateri: ImageView = itemView.findViewById(R.id.iv_materi)
        val tvMateri: TextView = itemView.findViewById(R.id.tv_materi)

        fun bind(dataMateri: DataMateri) {
            tvMateri.text = dataMateri.judul
            Glide.with(itemView.context).load(dataMateri.gambar_display).into(ivMateri) // Menggunakan Glide untuk gambar

            itemView.setOnClickListener { onClick(dataMateri) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_materi, parent, false)
        return MateriViewHolder(view)
    }

    override fun onBindViewHolder(holder: MateriViewHolder, position: Int) {
        holder.bind(materiList[position])
    }

    override fun getItemCount(): Int = materiList.size
}