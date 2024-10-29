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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.model.Lembrete
import com.project.a65ddm_trabalho1.viewModel.LembreteViewModel
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditarLembreteFragment : Fragment() {

    private val args: EditarLembreteFragmentArgs by navArgs()
    private lateinit var viewModel: LembreteViewModel
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var dosagemEditText: EditText
    private lateinit var radioGroupRepeticao: RadioGroup
    private lateinit var botaoAtualizar: Button
    private lateinit var spinnerMedicamentos: Spinner
    private lateinit var horarioEditText: EditText
    private lateinit var selectedDateTime: Calendar
    private lateinit var lembrete: Lembrete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_lembrete, container, false)

        horarioEditText = view.findViewById(R.id.edit_text_horario)
        dosagemEditText = view.findViewById(R.id.edit_text_dosagem)
        radioGroupRepeticao = view.findViewById(R.id.repeticao_group)
        botaoAtualizar = view.findViewById(R.id.button_atualizar_lembrete)
        spinnerMedicamentos = view.findViewById(R.id.spinner_medicamentos)

        viewModel = ViewModelProvider(this).get(LembreteViewModel::class.java)
        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        lembrete = args.lembrete
        selectedDateTime = lembrete.getCalendar()
        setupMedicamentosSpinner(lembrete.medicamentoId)

        displayLembreteData()

        horarioEditText.setOnClickListener { showDateTimePicker() }

        botaoAtualizar.setOnClickListener { atualizarLembrete() }

        return view
    }

    private fun setupMedicamentosSpinner(selectedMedicamentoId: Int) {
        medicamentoViewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                medicamentos.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMedicamentos.adapter = adapter

            val selectedIndex = medicamentos.indexOfFirst { it.id == selectedMedicamentoId }
            if (selectedIndex >= 0) {
                spinnerMedicamentos.setSelection(selectedIndex)
            }
        }
    }

    private fun displayLembreteData() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        horarioEditText.setText(dateFormat.format(selectedDateTime.time))

        dosagemEditText.setText(lembrete.mensagem)
        when (lembrete.repeticao) {
            "Todos os dias" -> radioGroupRepeticao.check(R.id.radio_todos_dias)
            "Dias da semana" -> radioGroupRepeticao.check(R.id.radio_dias_semana)
            "Data unica" -> radioGroupRepeticao.check(R.id.radio_unica_data)
        }
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            selectedDateTime.set(year, month, dayOfMonth)
            TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedDateTime.set(Calendar.MINUTE, minute)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                horarioEditText.setText(dateFormat.format(selectedDateTime.time))
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show()
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun atualizarLembrete() {
        val medicamentoSelecionado = spinnerMedicamentos.selectedItem as String
        val tipoRepeticao = when (radioGroupRepeticao.checkedRadioButtonId) {
            R.id.radio_todos_dias -> "Todos os dias"
            R.id.radio_unica_data -> "Data unica"
            R.id.radio_dias_semana -> "Dias da semana"
            else -> "Nenhuma opção"
        }

        val atualizado = lembrete.copy(
            medicamentoId = obterIdMedicamentoPorNome(medicamentoSelecionado),
            dataLembrete = selectedDateTime.timeInMillis,
            mensagem = dosagemEditText.text.toString(),
            repeticao = tipoRepeticao
        )

        viewModel.atualizarLembrete(atualizado)
        Toast.makeText(requireContext(), "Lembrete atualizado", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack()
    }

    private fun obterIdMedicamentoPorNome(nome: String): Int {
        val medicamento = medicamentoViewModel.medicamentos.value?.find { it.nome == nome }
        return medicamento?.id ?: 0
    }
}
