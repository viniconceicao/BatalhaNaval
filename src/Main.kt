fun main() {

    // Criando a matriz
    val matriz2D = Array(10) {Array(10){0} }

    // Jogando informação para o terminal
    for ( informacao in matriz2D) {
        for ( elemento in informacao){
            print("$elemento")
        }
        println()
    }

}