package com.project.a65ddm_trabalho1.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.a65ddm_trabalho1.R

class MedicamentoAdapter(
    private val medicamentos: List<Medicamento>,
    private val onEditClick: (Medicamento) -> Unit,
    private val onDeleteClick: (Medicamento) -> Unit,
    private val onDetailClick: (Medicamento) -> Unit
) : RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicamento, parent, false)
        return MedicamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        val medicamento = medicamentos[position]
        holder.bind(medicamento, onEditClick, onDeleteClick, onDetailClick)
    }

    override fun getItemCount(): Int = medicamentos.size

    class MedicamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeMedicamentoTextView: TextView = itemView.findViewById(R.id.text_view_nome_medicamento)
        private val editarButton: ImageButton = itemView.findViewById(R.id.button_editar)
        private val deletarButton: ImageButton = itemView.findViewById(R.id.button_deletar)

        fun bind(
            medicamento: Medicamento,
            onEditClick: (Medicamento) -> Unit,
            onDeleteClick: (Medicamento) -> Unit,
            onDetailClick: (Medicamento) -> Unit
        ) {
            nomeMedicamentoTextView.text = medicamento.nome

            editarButton.setOnClickListener {
                onEditClick(medicamento)
            }

            deletarButton.setOnClickListener {
                onDeleteClick(medicamento)
            }

            itemView.setOnClickListener{ onDetailClick(medicamento) }
        }
    }
}
