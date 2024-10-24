package com.project.a65ddm_trabalho1.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.a65ddm_trabalho1.R

class LembreteAdapter(
    private val lembretes: List<Lembrete>,
    private val onEditClick: (Lembrete) -> Unit,
    private val onSwitchChange: (Lembrete, Boolean) -> Unit
) : RecyclerView.Adapter<LembreteAdapter.LembreteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LembreteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lembrete, parent, false)
        return LembreteViewHolder(view)
    }

    override fun onBindViewHolder(holder: LembreteViewHolder, position: Int) {
        val lembrete = lembretes[position]
        holder.bind(lembrete, onEditClick, onSwitchChange)
    }

    override fun getItemCount(): Int = lembretes.size

    class LembreteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val horaTextView: TextView = itemView.findViewById(R.id.text_view_hora_lembrete)
        private val descricaoTextView: TextView = itemView.findViewById(R.id.text_view_descricao_lembrete)
        private val editarButton: ImageButton = itemView.findViewById(R.id.button_editar_lembrete)
        private val ativarSwitch: Switch = itemView.findViewById(R.id.switch_ativar_lembrete)

        fun bind(
            lembrete: Lembrete,
            onEditClick: (Lembrete) -> Unit,
            onSwitchChange: (Lembrete, Boolean) -> Unit
        ) {
            horaTextView.text = lembrete.dataLembrete.toString()
            descricaoTextView.text = lembrete.mensagem
            ativarSwitch.isChecked = true

            editarButton.setOnClickListener {
                onEditClick(lembrete)
            }

            ativarSwitch.setOnCheckedChangeListener { _, isChecked ->
                onSwitchChange(lembrete, isChecked)
            }
        }
    }
}
