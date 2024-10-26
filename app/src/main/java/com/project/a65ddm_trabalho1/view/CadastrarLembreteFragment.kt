package com.project.a65ddm_trabalho1.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CadastrarLembreteFragment : Fragment() {

    private lateinit var viewModel: LembreteViewModel
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var dosagemEditText: EditText
    private lateinit var radioGroupRepeticao: RadioGroup
    private lateinit var botaoCadastrar: Button
    private lateinit var spinnerMedicamentos: Spinner
    private lateinit var horarioEditText: EditText
    private lateinit var selectedDateTime: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_lembrete, container, false)

        // Inicializa os componentes da interface
        horarioEditText = view.findViewById(R.id.edit_text_horario)
        dosagemEditText = view.findViewById(R.id.edit_text_dosagem)
        radioGroupRepeticao = view.findViewById(R.id.repeticao_group)
        botaoCadastrar = view.findViewById(R.id.button_cadastrar_lembrete)
        spinnerMedicamentos = view.findViewById(R.id.spinner_medicamentos)
        selectedDateTime = Calendar.getInstance()

        horarioEditText.setOnClickListener {
            showDateTimePicker()
        }

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
                R.id.radio_todos_dias -> "Todos os dias"
                R.id.radio_unica_data -> "Data unica"
                R.id.radio_dias_semana -> "dias da semana"
                else -> "Nenhuma opção"
            }

            // Chama o método do ViewModel para salvar o lembrete
            viewModel.cadastrarLembrete(
                medicamentoId = obterIdMedicamentoPorNome(medicamentoSelecionado),
                dataLembrete =  selectedDateTime.timeInMillis, // Exemplo de uso do timestamp atual
                mensagem = "Lembrete: $medicamentoSelecionado - $dosagem, Repetição: $tipoRepeticao",
                repeticao = tipoRepeticao
            )

            Toast.makeText(requireContext(), "Lembrete cadastrado", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun showDateTimePicker() {
        // Obtém a data atual para inicializar o DatePicker
        val currentDate = Calendar.getInstance()

        // Exibe o DatePickerDialog para o usuário escolher a data
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            // Define a data selecionada no Calendar
            selectedDateTime.set(year, month, dayOfMonth)

            // Após selecionar a data, exibe o TimePickerDialog para o usuário escolher a hora
            TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                // Define a hora e o minuto selecionados no Calendar
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedDateTime.set(Calendar.MINUTE, minute)

                // Formata a data e a hora para exibição no campo de texto
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                horarioEditText.setText(dateFormat.format(selectedDateTime.time))

            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show() // true = formato 24 horas

        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show()
    }


    private fun obterIdMedicamentoPorNome(nome: String): Int {
        val medicamento = medicamentoViewModel.medicamentos.value?.find { it.nome == nome }
        return medicamento?.id ?: 0 // Retorna o ID do medicamento ou 0 se não encontrado
    }
}
