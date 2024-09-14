package edu.udb.desafio2dsm.models

data class Orden(
    val items: List<Comidas> = listOf(),
    val userId: String = ""
)