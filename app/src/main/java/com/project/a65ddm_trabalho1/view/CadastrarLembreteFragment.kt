package com.project.a65ddm_trabalho1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.viewModel.LembreteViewModel
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel

class CadastrarLembreteFragment : Fragment() {

    private lateinit var viewModel: LembreteViewModel
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var horarioEditText: EditText
    private lateinit var dosagemEditText: EditText
    private lateinit var radioGroupRepeticao: RadioGroup
    private lateinit var botaoCadastrar: Button
    private lateinit var spinnerMedicamentos: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_lembrete, container, false)

        // Inicializa os componentes da interface
        horarioEditText = view.findViewById(R.id.edit_text_horario)
        dosagemEditText = view.findViewById(R.id.edit_text_dosagem)
        radioGroupRepeticao = view.findViewById(R.id.radio_group_repeticao)
        botaoCadastrar = view.findViewById(R.id.button_cadastrar_lembrete)
        spinnerMedicamentos = view.findViewById(R.id.spinner_medicamentos)

        viewModel = ViewModelProvider(this).get(LembreteViewModel::class.java)
        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        // Carregar os medicamentos no Spinner
        medicamentoViewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                medicamentos.map { it.nome } // Mapeia a lista de Medicamento para os nomes
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMedicamentos.adapter = adapter
        }

        // Lógica do botão de cadastro
        botaoCadastrar.setOnClickListener {
            val horario = horarioEditText.text.toString()
            val dosagem = dosagemEditText.text.toString()
            val medicamentoSelecionado = spinnerMedicamentos.selectedItem as String

            val tipoRepeticao = when (radioGroupRepeticao.checkedRadioButtonId) {
                R.id.radio_todos_os_dias -> "Todos os dias"
                R.id.radio_dia_especifico -> "Dia específico"
                R.id.radio_data_especifica -> "Data específica"
                else -> "Nenhuma opção"
            }

            // Chama o método do ViewModel para salvar o lembrete
            viewModel.cadastrarLembrete(
                medicamentoId = obterIdMedicamentoPorNome(medicamentoSelecionado),
                dataLembrete = System.currentTimeMillis(), // Exemplo de uso do timestamp atual
                mensagem = "Lembrete: $medicamentoSelecionado - $dosagem, Repetição: $tipoRepeticao",
                repeticao = tipoRepeticao
            )

            Toast.makeText(requireContext(), "Lembrete cadastrado", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun obterIdMedicamentoPorNome(nome: String): Int {
        val medicamento = medicamentoViewModel.medicamentos.value?.find { it.nome == nome }
        return medicamento?.id ?: 0 // Retorna o ID do medicamento ou 0 se não encontrado
    }
}
