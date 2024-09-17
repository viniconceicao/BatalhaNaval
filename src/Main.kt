import kotlin.math.abs

// Criando a matriz com tamanho 10x10
val matriz2D = Array(10) { CharArray(10) { '.' } }

// Valores para a matriz
val portaAvioes = 10
val cruzador = 1
val rebocadores = 2
val tentativas = 15
var barcos = portaAvioes + cruzador + rebocadores // Soma o total de navios
var pontuacao = 0

// Cores
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
        // Fazer opção de jogar novamente
        println("Jogar novamente? (1 para SIM, outro número para sair)")
        val jogarNovamente = readLine()?.toIntOrNull() ?: 0
    } while (jogarNovamente == 1)
    println("Obrigado por jogar!")
}

// Inicializar a matriz com '.'
fun setupBoard() {
    for (i in 0 until 10) {
        for (j in 0 until 10) {
            matriz2D[i][j] = '.' // Mostrar '.' no tabuleiro
        }
    }
}

// Posiciona os barcos: porta-aviões, cruzador, rebocadores
fun setupBoats() {
    // Função para evitar sobreposição e garantir que cada tipo de barco seja colocado adequadamente
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
    posicionarBarco('P', portaAvioes) // Porta-aviões
    posicionarBarco('C', cruzador)    // Cruzador
    posicionarBarco('R', rebocadores) // Rebocadores
}

// Imprime o estado atual da matriz, destacando no final do jogo
fun printBoard(mostrarBarcos: Boolean = false, fimDeJogo: Boolean = false) {
    print("  ")
    (1..10).forEach { print("$it ") } // Exibir os números no topo
    println()

    matriz2D.forEachIndexed { i, linha ->
        print("${(i + 65).toChar()} ") // Exibir as letras à esquerda
        linha.forEach { cell ->
            when {
                fimDeJogo && cell == 'X' -> print("${vermelho}X${reset} ") // Navios afundados em vermelho no final do jogo
                fimDeJogo && cell == 'P' -> print("${azul}P${reset} ")     // Porta-aviões restantes em azul
                fimDeJogo && cell == 'C' -> print("${azul}C${reset} ")     // Cruzador restante em azul
                fimDeJogo && cell == 'R' -> print("${azul}R${reset} ")     // Rebocador restante em azul
                fimDeJogo && cell == '~' -> print("${verde}~${reset} ")    // Água em verde no final
                !mostrarBarcos && cell == 'P' -> print(". ")               // Ocultar Porta-aviões durante o jogo
                !mostrarBarcos && cell == 'C' -> print(". ")               // Ocultar Cruzador durante o jogo
                !mostrarBarcos && cell == 'R' -> print(". ")               // Ocultar Rebocador durante o jogo
                else -> print("$cell ")                                    // Exibir qualquer outra coisa (X, ~, etc.)
            }
        }
        println()
    }
}

// Verifica a distância de acerto, se o tiro foi próximo a algum navio
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

    // Se menorDistancia não foi definida, retorne -1 para indicar que não há navios próximos
    return menorDistancia ?: -1
}


fun playGame() {
    var hits = 0
    var attempts = 0
    val jogadas = mutableSetOf<String>() // Guarda as tentativas para evitar repetições

    while (hits < barcos && attempts < tentativas) {
        print("Digite a coordenada (ex: A1): ")
        val input = readLine()?.uppercase() ?: ""

        if (input.isNotEmpty() && !jogadas.contains(input)) {
            jogadas.add(input) // Armazena o tiro
            val row = input[0].code - 65  // Converte 'A'-'J' em 0-9
            val col = input.substring(1).toIntOrNull()?.minus(1) ?: -1  // Converte '1'-'10' em 0-9

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
                            matriz2D[row][col] = '~' // Marca água quando o erro é próximo
                        } else {
                            println("${verde}Errou por muito!${reset}")
                            matriz2D[row][col] = 'M' // Marca M quando o erro é distante
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

    // Fim de jogo - Exibe o tabuleiro com os navios e as marcas coloridas
    println("Fim das tentativas!")
    printBoard(mostrarBarcos = true, fimDeJogo = true) // Mostra os navios no final com destaque
    println("Sua pontuação final: $pontuacao pontos.")
}

