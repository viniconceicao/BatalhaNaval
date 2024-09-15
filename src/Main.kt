fun main() {

   // Criando a matriz
    val matrix = arrayOf(
        arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
        arrayOf(11, 12, 13, 14, 15, 16, 17, 18, 19, 20),
        arrayOf(21, 22, 23, 24, 25, 26, 27, 28, 29, 30),
        arrayOf(31, 33, 34, 35, 36, 37, 38, 39, 40),
        arrayOf(41, 42, 43, 44, 45, 46, 47, 48, 49, 50),
        arrayOf(51, 52, 53, 54, 55, 56, 57, 58, 59, 60),
        arrayOf(61, 62, 63, 64, 65, 64, 65, 66, 67, 68, 69, 70,),
        arrayOf(71, 72, 73, 74, 75, 76, 77, 78, 79, 80),
        arrayOf(81, 82, 83, 84, 85, 86, 87, 88, 89, 90,),
        arrayOf(91, 92, 93, 94, 95, 95, 96, 97, 98, 99, 100)
    )

    // Acessar alguma informação
    println(matrix[0][5]) // da esquerda 0 = primeira linha e da direita sempre -1 para pegar o 6 por exemplo

    // Percorrendo a matriz
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            print("${matrix[i][j]} ")
        }
        println()
    }

}