data class Pagamento(
    val tipo: String,
    val dataAnuncio: String,
    val dataPagamento: String,
    val valor: String
)

fun processarDividendo(texto: String): List<Pagamento> {
    val regex = Regex("(Dividendos|JSCP|Rend\\. Tributado) (\\d{2}/\\d{2}/\\d{4}) (\\d{2}/\\d{2}/\\d{4}) ([\\d,]+)")
    val pagamentos = mutableListOf<Pagamento>()

    regex.findAll(texto).forEach { matchResult ->
        val tipo = matchResult.groupValues[1]
        val dataAnuncio = matchResult.groupValues[2]
        val dataPagamento = matchResult.groupValues[3]
        val valor = matchResult.groupValues[4]

        pagamentos.add(Pagamento(tipo, dataAnuncio, dataPagamento, valor))
    }

    return pagamentos
}
