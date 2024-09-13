package edu.udb.desafio2dsm.models

data class CarritoItem(
    val comida: Comidas,
    var cantidad: Int = 1
)