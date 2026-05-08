package com.example.datapasien.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datapasien.R
import com.example.datapasien.model.PasienModel

class PasienAdapter(
    private val onEditClick: (PasienModel) -> Unit,    // Lambda untuk handle klik Edit
    private val onDeleteClick: (PasienModel) -> Unit   // Lambda untuk handle klik Delete
) : RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    private val pasien = mutableListOf<PasienModel>()

    fun setData(newVehicles: List<PasienModel>) {
        pasien.clear()
        pasien.addAll(newVehicles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        holder.bind(pasien[position])

    }

    override fun getItemCount(): Int = pasien.size

    inner class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        private val tvTanggalLahir: TextView = itemView.findViewById(R.id.tvTanggalLahir)
        private val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        private val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
        private val tvTelepon: TextView = itemView.findViewById(R.id.tvTelepon)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(pasien: PasienModel) {
            tvNama.text = pasien.nama
            tvTanggalLahir.text = pasien.tanggal_lahir
            tvJenisKelamin.text = pasien.jenis_kelamin
            tvAlamat.text = pasien.alamat
            tvTelepon.text = pasien.no_telepon


            // Handle klik tombol Edit
            btnEdit.setOnClickListener {
                onEditClick(pasien)
            }

            // Handle klik tombol Delete
            btnDelete.setOnClickListener {
                onDeleteClick(pasien)
            }
        }
    }
}