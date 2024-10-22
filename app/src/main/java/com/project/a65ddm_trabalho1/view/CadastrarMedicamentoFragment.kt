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
    private lateinit var nomeEditText: EditText
    private lateinit var dosagemEditText: EditText
    private lateinit var botaoCadastrar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_medicamento, container, false)

        // Inicialize os campos de texto e botão
        nomeEditText = view.findViewById(R.id.nome_medicamento)
        dosagemEditText = view.findViewById(R.id.dosagem_medicamento)
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar)

        // Conecte o ViewModel
        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        // Configura a ação do botão
        botaoCadastrar.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val dosagem = dosagemEditText.text.toString()

            // Chama a função de cadastrar medicamento do ViewModel
            if (nome.isNotEmpty() && dosagem.isNotEmpty()) {
                viewModel.cadastrarMedicamento(nome, dosagem)
                Toast.makeText(requireContext(), "Medicamento cadastrado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

