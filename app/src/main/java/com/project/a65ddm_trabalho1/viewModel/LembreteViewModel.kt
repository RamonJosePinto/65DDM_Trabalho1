package com.project.a65ddm_trabalho1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.project.a65ddm_trabalho1.model.AppDataBase
import com.project.a65ddm_trabalho1.model.Lembrete
import com.project.a65ddm_trabalho1.model.LembreteDAO
import kotlinx.coroutines.launch

class LembreteViewModel(application: Application) : AndroidViewModel(application) {
    private val lembreteDAO: LembreteDAO = AppDataBase
        .getDatabase(application).lembreteDAO()

    fun cadastrarLembrete(medicamentoId: Int, dataLembrete: Long, mensagem: String, repeticao: String) {
        viewModelScope.launch {
            val lembrete = Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = dataLembrete,
                mensagem = mensagem,
                repeticao = repeticao
            )
            lembreteDAO.inserirLembrete(lembrete)
        }
    }

    fun listarLembretesPorMedicamento(medicamentoId: Int): LiveData<List<Lembrete>> {
        return liveData {
            emit(lembreteDAO.listarLembretesPorMedicamento(medicamentoId))
        }
    }

    fun listarTodosLembretes(): LiveData<List<Lembrete>> {
        return liveData {
            emit(lembreteDAO.listarTodosLembretes())
        }
    }
}
