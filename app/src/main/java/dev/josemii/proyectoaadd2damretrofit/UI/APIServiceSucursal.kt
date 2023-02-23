package dev.josemii.proyectoaadd2damretrofit.UI

import dev.josemii.proyectoaadd2damretrofit.Clases.Sucursal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIServiceSucursal{
    /*// Método para crear una sucursal
    //@POST("sucursales")
    suspend fun createSucursal(@Body sucursal: Sucursal): Call<Sucursal>
*/
    // Método para obtener todas las sucursales
    @GET("sucursales")
    suspend fun getSucursales(): Response<List<Sucursal>>

    // Método para obtener una sucursal por su ID
    @GET("sucursales/{id}")
    suspend fun getSucursal(@Path("id") id: Int): Response<Sucursal>
/*
    // Método para actualizar una sucursal
    @PUT("sucursales/{id}")
    suspend fun updateSucursal(@Path("id") id: Int, @Body sucursal: Sucursal): Call<Sucursal>

    // Método para eliminar una sucursal
    @DELETE("sucursales/{id}")
    suspend fun deleteSucursal(@Path("id") id: Int): Call<Void>*/
}