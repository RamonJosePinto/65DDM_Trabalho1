package com.project.a65ddm_trabalho1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel

class CadastrarMedicamentoFragment : Fragment() {

    private lateinit var viewModel: MedicamentoViewModel
    private lateinit var nomeMedicamento: EditText
    private lateinit var dosagemMedicamento: EditText
    private lateinit var botaoCadastrar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_medicamento, container, false)

        nomeMedicamento = view.findViewById(R.id.nome_medicamento)
        dosagemMedicamento = view.findViewById(R.id.dosagem_medicamento)
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar)

        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        botaoCadastrar.setOnClickListener {
            val nome = nomeMedicamento.text.toString()
            val dosagem = dosagemMedicamento.text.toString()
            viewModel.cadastrarMedicamento(nome, dosagem)
            Toast.makeText(requireContext(), "Medicamento cadastrado", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
