package com.example.aulaapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulaapi.api.EnderecoAPI
import com.example.aulaapi.api.PostagemAPI
import com.example.aulaapi.api.RetrofitHelper
import com.example.aulaapi.api.RetrofitHelper.Companion.retrofit
import com.example.aulaapi.databinding.ActivityMainBinding
import com.example.aulaapi.model.Comentario
import com.example.aulaapi.model.Endereco
import com.example.aulaapi.model.Postagem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnIniciar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //recuperarEndereco()
                //recuperarPostagens()
                //recuperarPostagemUnica()
                //recuperarComentatiosParaPostagem()
                salvarPostagem()
            }
        }
    }

    private suspend fun salvarPostagem() {
        var retorno: Response<Postagem>? = null

        val postagem = Postagem(
            "Corpo da postagem",
            -1,
            "Titulo da postagem",
            1090
        )

        try {
            val postagemAPI = retrofit.create( PostagemAPI::class.java )

            retorno = postagemAPI.salvarPostagem( postagem ) //Query
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "erro ao recuperar")        }

        if ( retorno != null ){
            if( retorno.isSuccessful ){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val idUsuario = postagem?.userId

                var resultado = "id: $id - T:$titulo - U$idUsuario"

                    withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }


            }

        }
    }

    private suspend fun recuperarComentatiosParaPostagem() {
        var retorno: Response<List<Comentario>>? = null

        try {
            val postagemAPI = retrofit.create( PostagemAPI::class.java )
            //retorno = postagemAPI.recuperarComentariosParaPostagem( 1 ) Path
            retorno = postagemAPI.recuperarComentariosParaPostagemQuery( 1 ) //Query
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "erro ao recuperar")
        }

        if ( retorno != null ){

            if( retorno.isSuccessful ){

                val listaPostagens = retorno.body()

                var resultado = ""
                listaPostagens?.forEach { comentario ->
                    val idComentario = comentario.id
                    val email = comentario.email
                    val comentarioResultado = "$idComentario - $email \n"
                    resultado += comentarioResultado

                    Log.i("info_jsonplace", "$idComentario - $email")
                }

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }


            }

        }
    }

    private suspend fun recuperarPostagemUnica() {
        var retorno: Response<Postagem>? = null

        try {
            val postagemAPI = retrofit.create( PostagemAPI::class.java )
            retorno = postagemAPI.recuperarPostagemUnica( 1 )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "erro ao recuperar")
        }

        if ( retorno != null ){

            if( retorno.isSuccessful ){

                val postagem = retorno.body()
                val resultado = "${postagem?.id} - ${postagem?.title}"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado

                Log.i("info_jsonplace", resultado)
            }


            }

        }
    }

    private suspend fun recuperarPostagens() {
        var retorno: Response<List<Postagem>>? = null

        try {
            val postagemAPI = retrofit.create( PostagemAPI::class.java )
            retorno = postagemAPI.recuperarPostagens()
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "erro ao recuperar")
        }

        if ( retorno != null ){

            if( retorno.isSuccessful ){

                val listaPostagens = retorno.body()
                listaPostagens?.forEach { postagem ->
                    val id = postagem.id
                    val title = postagem.title
                    Log.i("info_jsonplace", "$id - $title") }


            }

        }
    }

    private suspend fun  recuperarEndereco(){

        var retorno: Response<Endereco>? = null
        val cepDigitadoUsuario = "01001000"

        try {
            val enderecoAPI = retrofit.create( EnderecoAPI::class.java )
            retorno = enderecoAPI.recuperarEndereco( cepDigitadoUsuario )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_endereco", "erro ao recuperar")
        }

        if ( retorno != null ){

            if( retorno.isSuccessful ){

                val endereco = retorno.body()
                val rua = endereco?.logradouro
                val cidade = endereco?.localidade
                val cep = endereco?.cep
                Log.i("info_endereco", "endereco: $rua, $cidade, $cep")

            }

        }

    }
}