package dev.josemii.proyectoaadd2damretrofit.Clases

import java.util.Date

data class Cliente(
    var id : Int,
    var dni : String,
    var nombre : String,
    var apellidos : String,
    var fechaMacimiento : Date,
    var sucursalByIdSucursal: Sucursal
)
