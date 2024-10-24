package com.project.a65ddm_trabalho1.repository

import com.project.a65ddm_trabalho1.model.api.ApiResponse

class MedicamentoRepository(private val apiService: ApiService) {
    suspend fun buscarMedicamentos(nome: String, pagina: Int): ApiResponse {
        return apiService.pesquisarMedicamentos(nome, pagina)
    }
}