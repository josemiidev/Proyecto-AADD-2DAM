package dev.josemii.proyectoaadd2damretrofit.UI

import dev.josemii.proyectoaadd2damretrofit.Clases.Cuenta
import retrofit2.Response
import retrofit2.http.*

interface APIServiceCuenta {
    // Método para crear una cuenta
    @POST("cuentas")
    suspend fun createCuenta(@Body cuenta: Cuenta): Response<Cuenta>

    // Método para obtener todas las cuentas
    @GET("cuentas")
    suspend fun getCuentas(): Response<List<Cuenta>>

    // Método para obtener una cuenta por su ID
    @GET("cuentas/{id}")
    suspend fun getCuenta(@Path("id") id: Int): Response<Cuenta>

    // Método para actualizar una cuenta
    @PUT("cuentas/{id}")
    suspend fun updateCuenta(@Path("id") id: Int, @Body cuenta: Cuenta): Response<Cuenta>

    // Método para eliminar una cuenta
    @DELETE("cuentas/{id}")
    suspend fun deleteCuental(@Path("id") id: Int): Response<Void>
}