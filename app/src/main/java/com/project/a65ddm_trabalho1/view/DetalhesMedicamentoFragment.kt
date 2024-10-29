package com.project.a65ddm_trabalho1.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.model.Medicamento

class DetalhesMedicamentoFragment : Fragment() {

    private val args: DetalhesMedicamentoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalhes_medicamento, container, false)

        val imageMedicamento: ImageView = view.findViewById(R.id.image_medicamento)
        val textNomeMedicamento: TextView = view.findViewById(R.id.text_nome_medicamento)
        val textDosagemMedicamento: TextView = view.findViewById(R.id.text_dosagem_medicamento)

        val medicamento: Medicamento = args.medicamento

        textNomeMedicamento.text = medicamento.nome
        textDosagemMedicamento.text = medicamento.dosagem

        medicamento.fotoCaminho?.let { caminhoFoto ->
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            imageMedicamento.setImageBitmap(bitmap)
        }

        return view
    }
}
