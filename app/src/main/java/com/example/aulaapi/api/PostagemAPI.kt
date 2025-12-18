package com.example.aulaapi.api

import com.example.aulaapi.model.Comentario
import com.example.aulaapi.model.Postagem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostagemAPI {

    //https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    suspend fun recuperarPostagens(): Response<List<Postagem>>

    @GET("posts/{id}")
    suspend fun recuperarPostagemUnica(
        @Path("id") id: Int
    ): Response<Postagem>

    @GET("posts/{id}/comments")
    suspend fun recuperarComentariosParaPostagem(
        @Query("id") id: Int
    ): Response<List<Comentario>>

    @GET("comments")
    suspend fun recuperarComentariosParaPostagemQuery(
        @Query("postId") id: Int
    ): Response<List<Comentario>>

    @POST("posts")
    suspend fun salvarPostagem(
        @Body postagem: Postagem
    ): Response<Postagem>



}


