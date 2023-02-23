package dev.josemii.proyectoaadd2damretrofit.UI

import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import retrofit2.Response
import retrofit2.http.*

interface APIServiceCuenta {
    // Método para crear una cuenta
    @POST("cuentas")
    suspend fun createSucursal(@Body cuenta: Cuenta): Response<Cuenta>

    // Método para obtener todas las cuentas
    @GET("cuentas")
    suspend fun getSucursales(): Response<List<Cuenta>>

    // Método para obtener una cuenta por su ID
    @GET("cuentas/{id}")
    suspend fun getSucursal(@Path("id") id: Int): Response<Cuenta>

    // Método para actualizar una cuenta
    @PUT("cuentas/{id}")
    suspend fun updateSucursal(@Path("id") id: Int, @Body cuenta: Cuenta): Response<Cuenta>

    // Método para eliminar una cuenta
    @DELETE("cuentas/{id}")
    suspend fun deleteSucursal(@Path("id") id: Int): Response<Void>
}