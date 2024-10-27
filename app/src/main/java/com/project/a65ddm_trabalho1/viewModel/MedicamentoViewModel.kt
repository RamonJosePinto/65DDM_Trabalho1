package com.project.a65ddm_trabalho1.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.a65ddm_trabalho1.model.AppDataBase
import com.project.a65ddm_trabalho1.model.Medicamento
import com.project.a65ddm_trabalho1.model.MedicamentoDAO
import kotlinx.coroutines.launch

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {
    private val medicamentoDAO: MedicamentoDAO = AppDataBase.getDatabase(application).medicamentoDAO()

    private val _medicamentos = MutableLiveData<List<Medicamento>>()
    val medicamentos: LiveData<List<Medicamento>> = _medicamentos

    init {
        listarMedicamentos()
    }

    fun cadastrarMedicamento(nome: String, dosagem: String, fotoCaminho: String?) {
        viewModelScope.launch {
            val medicamento = Medicamento(nome = nome, dosagem = dosagem, fotoCaminho = fotoCaminho)
            medicamentoDAO.inserir(medicamento)
            listarMedicamentos()
            Log.d("MedicamentoViewModel", "Medicamento cadastrado: $medicamento")
        }
    }


    fun listarMedicamentos() {
        viewModelScope.launch {
            val listaMedicamentos = medicamentoDAO.listarMedicamentos()
            _medicamentos.value = listaMedicamentos
            Log.d("TAG","Medicamentos carregados: $listaMedicamentos")
        }
    }

    fun deletarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            medicamentoDAO.deletarMedicamento(medicamento)
            listarMedicamentos() // Atualiza a lista após a deleção
        }
    }
}
