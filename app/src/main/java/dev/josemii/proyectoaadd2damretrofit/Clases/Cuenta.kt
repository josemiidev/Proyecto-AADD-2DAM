package dev.josemii.proyectoaadd2damretrofit.Clases

data class Cuenta(
    var id : Int,
    var ccc : String,
    var saldo : Float,
    var enAlta : Boolean,
    var clienteByIdCliente: Cliente
)
