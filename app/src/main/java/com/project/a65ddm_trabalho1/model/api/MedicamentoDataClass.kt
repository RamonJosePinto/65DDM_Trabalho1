package com.project.a65ddm_trabalho1.model.api

data class Medicamento(
    val idProduto: Int,
    val numeroRegistro: String,
    val nomeProduto: String,
    val expediente: String,
    val razaoSocial: String,
    val cnpj: String,
    val numeroTransacao: String,
    val data: String,
    val numProcesso: String,
    val idBulaPacienteProtegido: String,
    val idBulaProfissionalProtegido: String,
    val dataAtualizacao: String
)

data class ApiResponse(
    val content: List<Medicamento>,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val numberOfElements: Int,
    val first: Boolean,
    val sort: Any?,
    val size: Int,
    val number: Int
)