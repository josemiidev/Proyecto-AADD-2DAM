package dev.josemii.proyectoaadd2damretrofit.UI

import dev.josemii.proyectoaadd2damretrofit.Clases.Cliente
import retrofit2.Response
import retrofit2.http.*

interface APIServiceCliente {
    // Método para crear una clientes
    @POST("clientes")
    suspend fun createCliente(@Body cliente: Cliente): Response<Cliente>

    // Método para obtener todas las clientes
    @GET("clientes")
    suspend fun getCliente(): Response<List<Cliente>>

    // Método para obtener una sucursal por su ID
    @GET("clientes/{id}")
    suspend fun getCliente(@Path("id") id: Int): Response<Cliente>

    // Método para actualizar una clientes
    @PUT("clientes/{id}")
    suspend fun updateCliente(@Path("id") id: Int, @Body clientes: Cliente): Response<Cliente>

    // Método para eliminar una clientes
    @DELETE("clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Int): Response<Void>
}