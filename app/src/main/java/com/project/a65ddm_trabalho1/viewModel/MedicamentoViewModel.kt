package com.project.a65ddm_trabalho1.viewModel

import android.app.Application
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

    fun cadastrarMedicamento(nome: String, dosagem: String) {
        viewModelScope.launch {
            val medicamento = Medicamento(nome = nome, dosagem = dosagem)
            medicamentoDAO.inserir(medicamento)
            listarMedicamentos()
        }
    }

    fun listarMedicamentos() {
        viewModelScope.launch {
            _medicamentos.value = medicamentoDAO.listarMedicamentos()
        }
    }
}
