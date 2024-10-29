package com.project.a65ddm_trabalho1.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.model.api.MedicamentoAPI

class MedicamentoAPIAdapter(private val medicamentos: MutableList<MedicamentoAPI>) :
    RecyclerView.Adapter<MedicamentoAPIAdapter.MedicamentoViewHolder>() {

    inner class MedicamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val produtoNome: TextView = itemView.findViewById(R.id.produtoNome)
        val produtoApresentacao: TextView = itemView.findViewById(R.id.produtoApresentacao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicamento_api, parent, false)
        return MedicamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        val medicamento = medicamentos[position]
        holder.produtoNome.text = medicamento.nomeProduto
        holder.produtoApresentacao.text = medicamento.razaoSocial
    }

    override fun getItemCount(): Int = medicamentos.size

    fun adicionarMedicamentos(novosMedicamentos: List<MedicamentoAPI>) {
        medicamentos.addAll(novosMedicamentos)
        notifyDataSetChanged()
    }
}
