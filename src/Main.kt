// Criando a matriz
val matriz2D = Array(10) { CharArray(10) { '.' } }
var barcos = 3;

// o Main vai ser utilizado para fazer a sequência do minigame e dar opção de jogar novamente
fun main() {
    do {
    setupBoard()
    setupBoats()
    printBoard()
    playGame()
    // Fazer opção de jogar novamente
    println("Jogar novamente? (1 para SIM, outro número para sair)")
    val jogarNovamente = readLine()?.toIntOrNull() ?: 0
}   while (jogarNovamente == 1)
    println("Obrigado por jogar!")
}

// Inicializar a matriz com '.'
fun setupBoard() {
    // Jogando informação para o terminal
    for ( i in 0 until 10) {
        for ( j in 0 until 10){
            // Mostrar '.' no tabuleiro
            matriz2D[i][j] = '.'
        }
    }
    printBoard()
    }

fun setupBoats() {
    var barcoCount = 0
    while (barcoCount < barcos) {
        val row = (0 until 10).random()
        val col = (0 until 10).random()

        if (matriz2D[row][col] == '.') {
            matriz2D[row][col] = '#'
            barcoCount++
        }
    }
}

    // Imprime o estado atual da matriz
    fun printBoard(mostrarBarcos: Boolean = false) {
        print("  ")
        (1..10).forEach { print("$it ") } // Esses são os números de cima do jogo
        println()

        matriz2D.forEachIndexed {i, linha ->
            print("${(i + 65).toChar()} ")
            linha.forEach { cell ->
                if (cell == '#' && !mostrarBarcos) {
                    print(". ")  // Oculta os navios durante o jogo
                } else {
                    print("$cell ")
                }
            }
            println()
        }
    }

fun playGame() {
    var hits = 0
    var attempts = 0

    while (hits < barcos) {
        print("Digite a coordenada (ex: A1): ")
        val input = readLine()?.uppercase() ?: ""

        if (input.isNotEmpty()) {
            val row = input[0].code - 65  // Converte 'A'-'J' em 0-9
            val col = input.substring(1).toIntOrNull()?.minus(1) ?: -1  // Converte '1'-'10' em 0-9

            if (row in 0.. 10 && col in 0 .. 10) {
                attempts++
                when (matriz2D[row][col]) {
                    '#' -> {
                        println("Acertou!")
                        matriz2D[row][col] = 'X'
                        hits++
                    }
                    '.' -> {
                        println("Água!")
                        matriz2D[row][col] = '~'
                    }
                    else -> println("Você já atacou aqui!")
                }
            } else {
                println("Coordenada inválida!")
            }

            printBoard()
        } else {
            println("Entrada inválida!")
        }
    }

    println("Parabéns! Você afundou todos os navios em $attempts tentativas.")
    printBoard(mostrarBarcos = true)  // Mostra os navios no final do jogo
}



// Não sei porque raios o until funciona e não o ".." mas tá ai