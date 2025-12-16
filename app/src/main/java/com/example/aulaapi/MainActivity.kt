package com.example.aulaapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulaapi.api.EnderecoAPI
import com.example.aulaapi.api.RetrofitHelper
import com.example.aulaapi.databinding.ActivityMainBinding
import com.example.aulaapi.model.Endereco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                recuperarEndereco()
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