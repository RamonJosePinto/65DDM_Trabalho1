package com.project.a65ddm_trabalho1.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.project.a65ddm_trabalho1.model.AppDataBase
import com.project.a65ddm_trabalho1.model.Lembrete
import com.project.a65ddm_trabalho1.model.LembreteDAO
import kotlinx.coroutines.launch

class LembreteViewModel(application: Application) : AndroidViewModel(application) {
    private val lembreteDAO: LembreteDAO = AppDataBase
        .getDatabase(application).lembreteDAO()

    private val _lembretes = MutableLiveData<List<Lembrete>>()

    val lembretes: LiveData<List<Lembrete>> = _lembretes

    init {
        listarTodosLembretes()
    }

    fun cadastrarLembrete(medicamentoId: Int, dataLembrete: Long, mensagem: String, repeticao: String) {
        viewModelScope.launch {
            val lembrete = Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = dataLembrete,
                mensagem = mensagem,
                repeticao = repeticao
            )
            Log.d("TAG", "todos os lembretes: ${lembreteDAO.listarTodosLembretes()}")
            lembreteDAO.inserirLembrete(lembrete)
        }
    }

    fun listarLembretesPorMedicamento(medicamentoId: Int): LiveData<List<Lembrete>> {
        return liveData {
            emit(lembreteDAO.listarLembretesPorMedicamento(medicamentoId))
        }
    }

    fun listarTodosLembretes() {
        viewModelScope.launch {
            _lembretes.value = lembreteDAO.listarTodosLembretes()
        }
    }

//    fun ativarDesativarLembrete(lembrete: Lembrete, ativado: Boolean) {
//        viewModelScope.launch {
//            lembrete.ativado = ativado
//            lembreteDAO.atualizarLembrete(lembrete)
//        }
//    }
}
