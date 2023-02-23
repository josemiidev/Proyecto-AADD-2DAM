package dev.josemii.proyectoaadd2damretrofit.Clases

data class Sucursal(
    var id : Int,
    var poblacion : String,
    var provincia : String,
    var referencia : String
){
    override fun toString(): String = "$referencia - $poblacion ($provincia)"
}
