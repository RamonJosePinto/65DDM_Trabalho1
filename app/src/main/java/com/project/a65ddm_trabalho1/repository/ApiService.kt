package com.project.a65ddm_trabalho1.repository

import com.project.a65ddm_trabalho1.model.api.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pesquisar")
    suspend fun pesquisarMedicamentos(
        @Query("nome") nome: String,
        @Query("pagina") pagina: Int
    ): ApiResponse

}