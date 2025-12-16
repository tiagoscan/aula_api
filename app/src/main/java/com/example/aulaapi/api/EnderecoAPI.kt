package com.example.aulaapi.api

import com.example.aulaapi.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoAPI {


    /*BASE URL: "https://viacep.com.br/ + ws/01001000/json/

                                        postagens
                                        salvarpostagem
                                        saldo
                                        trasnferencia
                                        extrato

     */
    //GET,POST, PUT, PATCH e DELETE

    @GET("ws/{cep}/json/")
    suspend fun recuperarEndereco(
        @Path("cep") cep: String
    ) : Response<Endereco>


}