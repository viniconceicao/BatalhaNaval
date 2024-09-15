fun main() {
    // Criando a matriz
    val matriz2D = Array(10) {CharArray(10){ ' ' } }

    val cruzador = "\uD83E\uDD14"
    val c: String = cruzador

    // Jogando informação para o terminal
    for ( tabuleiro in matriz2D) {
        for ( elemento in tabuleiro){
            // Posicionar algo
            matriz2D[2][3] = c[0]
            //
            print("$elemento")
        }
        // Saída
        println()
    }


}