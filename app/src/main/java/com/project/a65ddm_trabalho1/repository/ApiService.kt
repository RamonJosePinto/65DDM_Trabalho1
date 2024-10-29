package com.project.a65ddm_trabalho1.repository

import com.project.a65ddm_trabalho1.model.api.RespostaAPI
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    @GET("pesquisar")
//    suspend fun pesquisarMedicamentos(
//        @Query("nome") nome: String,
//        @Query("pagina") pagina: Int
//    ): ApiResponse

    @GET("pesquisar")
    fun pesquisarMedicamentos(
        @Query("nome") nome: String,
        @Query("pagina") pagina: Int
    ): Call<RespostaAPI>

}