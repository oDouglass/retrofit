import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Call
import org.jsoup.Jsoup

fun main() {
    println("Digite o código após da ação após a barra (Ex: acoes/petr4/):")
    val codigo = readLine() ?: ""

    if (codigo.isNotEmpty()) {
        buscar(codigo)
    } else {
        println("Código html inválido.")
    }
}

interface ApiService {
    @GET
    fun getHtmlFromUrl(@Url url: String): Call<String>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://investidor10.com.br/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

fun buscar(codigo: String) {
    val url = "https://investidor10.com.br/$codigo"

    val call = apiService.getHtmlFromUrl(url)

    call.enqueue(object : retrofit2.Callback<String> {
        override fun onResponse(call: retrofit2.Call<String>, response: retrofit2.Response<String>) {
            if (response.isSuccessful) {
                val html = response.body()

                if (html != null) {
                    val document = Jsoup.parse(html)
                    val elemento = document.select(".last-value")
                    val conteudo = elemento.text()
                    val elementoDividendo = document.select(".visible-odd")
                    val conteudoDividendo = elementoDividendo.text()

                    println("Cotação: $conteudo")

                    val pagamentos = processarDividendo(conteudoDividendo)

                    pagamentos.forEach { pagamento ->
                        println("Tipo: ${pagamento.tipo}, Anunciado em: ${pagamento.dataAnuncio}, Pago em: ${pagamento.dataPagamento}, Valor: ${pagamento.valor}")
                    }

                    //println("Dividendos: $conteudoDividendo")
                }
            } else {
                println("Erro: ${response.code()}")
            }
        }

        override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
            println("Erro na requisição: ${t.message}")
        }
    })
}
