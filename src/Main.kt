// Nome dos Alunos:
// Marcos Vinicius Arruda Vandresen
// Rodrigo Vaisam Bastos
// Vinícius de Liz da Conceição

import kotlin.math.abs

// Criando a matriz com tamanho 10x10
val matriz2D = Array(10) { CharArray(10) { '.' } }

// Valores para a matriz
val portaAvioes = 10
val cruzador = 1
val rebocadores = 2
val tentativas = 15
var barcos = portaAvioes + cruzador + rebocadores
var pontuacao = 0

// Cores para destacar os acertos e erros
val reset = "\u001B[0m"
val vermelho = "\u001B[31m"
val verde = "\u001B[32m"
val azul = "\u001B[34m"

// O Main vai ser utilizado para fazer a sequência do minigame e dar opção de jogar novamente
fun main() {
    do {
        setupBoard()
        setupBoats()
        printBoard()
        playGame()
        println("Jogar novamente? (1 para SIM, outro número para sair)")
        val jogarNovamente = readLine()?.toIntOrNull() ?: 0
    } while (jogarNovamente == 1)
    println("Obrigado por jogar!")
}

// Inicializar a matriz com '.'
fun setupBoard() {
    for (i in 0 until 10) {
        for (j in 0 until 10) {
            matriz2D[i][j] = '.'
        }
    }
}

// Posiciona os barcos
fun setupBoats() {
    fun posicionarBarco(tipo: Char, quantidade: Int) {
        var barcoCount = 0
        while (barcoCount < quantidade) {
            val row = (0 until 10).random()
            val col = (0 until 10).random()

            if (matriz2D[row][col] == '.') {
                matriz2D[row][col] = tipo
                barcoCount++
            }
        }
    }

    // Posicionando os barcos
    posicionarBarco('P', portaAvioes)
    posicionarBarco('C', cruzador)
    posicionarBarco('R', rebocadores)
}

fun printBoard(mostrarBarcos: Boolean = false, fimDeJogo: Boolean = false) {
    print("  ")
    (1..10).forEach { print("$it ") }
    println()

    matriz2D.forEachIndexed { i, linha ->
        print("${(i + 65).toChar()} ")
        linha.forEach { cell ->
            when {
                fimDeJogo && cell == 'X' -> print("${vermelho}X${reset} ")
                fimDeJogo && cell == 'P' -> print("${azul}P${reset} ")
                fimDeJogo && cell == 'C' -> print("${azul}C${reset} ")
                fimDeJogo && cell == 'R' -> print("${azul}R${reset} ")
                fimDeJogo && cell == '~' -> print("${verde}~${reset} ")
                fimDeJogo && cell == 'M' -> print("${verde}M${reset} ")
                !mostrarBarcos && cell in listOf('P', 'C', 'R') -> print(". ")
                cell == 'X' -> print("${vermelho}X${reset} ")
                cell == '~' -> print("${verde}~${reset} ")
                cell == 'M' -> print("${verde}M${reset} ")
                else -> print("$cell ")
            }
        }
        println()
    }
}

fun verificarDistancia(row: Int, col: Int): Int {
    var menorDistancia: Int? = null

    for (distancia in 1..3) {
        for (i in -distancia..distancia) {
            for (j in -distancia..distancia) {
                val novaRow = row + i
                val novaCol = col + j
                if (novaRow in 0 until 10 && novaCol in 0 until 10) {
                    if (matriz2D[novaRow][novaCol] in listOf('P', 'C', 'R')) {
                        menorDistancia = distancia
                    }
                }
            }
        }
    }

    return menorDistancia ?: -1
}

fun playGame() {
    var hits = 0
    var attempts = 0
    val jogadas = mutableSetOf<String>()

    while (hits < barcos && attempts < tentativas) {
        print("Digite a coordenada (ex: A1): ")
        val input = readLine()?.uppercase() ?: ""

        if (input.isNotEmpty() && !jogadas.contains(input)) {
            jogadas.add(input)
            val row = input[0].code - 65
            val col = input.substring(1).toIntOrNull()?.minus(1) ?: -1

            if (row in 0 until 10 && col in 0 until 10) {
                when (matriz2D[row][col]) {
                    'P' -> {
                        println("${vermelho}Acertou um Porta-Aviões!${reset}")
                        matriz2D[row][col] = 'X'
                        pontuacao += 5
                        hits++
                    }
                    'C' -> {
                        println("${vermelho}Acertou um Cruzador!${reset}")
                        matriz2D[row][col] = 'X'
                        pontuacao += 15
                        hits++
                    }
                    'R' -> {
                        println("${vermelho}Acertou um Rebocador!${reset}")
                        matriz2D[row][col] = 'X'
                        pontuacao += 10
                        hits++
                    }
                    '.' -> {
                        val distancia = verificarDistancia(row, col)
                        if (distancia > 0) {
                            println("${verde}Água! Você errou por $distancia casas.${reset}")
                            matriz2D[row][col] = '~'
                        } else {
                            println("${verde}Errou por muito!${reset}")
                            matriz2D[row][col] = 'M'
                        }
                    }
                    else -> println("Você já atacou aqui!")
                }
                attempts++
            } else {
                println("Coordenada inválida!")
            }
        } else {
            println("Entrada inválida ou repetida!")
        }
        printBoard()
    }

    // Fim de jogo
    println("Fim de jogo! Sua pontuação final foi: $pontuacao pontos.")
    printBoard(mostrarBarcos = true, fimDeJogo = true)
}
