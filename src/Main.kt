// Criando a matriz
val matriz2D = Array(10) { CharArray(10) { '.' } }

// o Main vai ser utilizado para fazer a sequência do minigame e dar opção de jogar novamente
fun main() {
        setupBoard()
}

fun setupBoard() {
    // Jogando informação para o terminal
    for ( i in 0 until 10) {
        for ( j in 0 until 10){
            // Posicionar algo
            matriz2D[i][j] = '.'
        }
    }
    // Saída
    printBoard()
    }

    fun printBoard() {
        for ( i in 0 until 10) {
            for ( j in 0 until 10 ) {
                print("${matriz2D[i][j]} ")
            }
            println()
        }
    }


// Não sei porque raios o until funciona e não o ".." mas tá ai