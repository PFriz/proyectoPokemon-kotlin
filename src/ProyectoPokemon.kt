import Clases.Ataque
import Clases.Pokemon
import java.util.*

enum class Pokemones(val id: Int) {
    BULBASOUR(1),
    CHARMANDER(2),
    SQUIRTLE(3),
    PIKACHU(4),
}

fun main(args: Array<String>) {

    println("Selecciona un pokemon")

    for ((index, p) in Pokemones.values().withIndex()) {
        println("${index + 1} - $p")
    }

    val scanner = Scanner(System.`in`)
    val opcion = scanner.nextInt()

    val pokemon = elejirPokemon(opcion)

    val random = Random()
    val numeroAlAzar = 1 + random.nextInt(4)
    val pokemonSalvaje = elejirPokemon(numeroAlAzar)

    mostrarDatos(pokemon)

    println("Un ${pokemonSalvaje.nombre} Salvaje a aparecido")

    do {

        println("${pokemon.nombre} HP: ${pokemon.hp} || ${pokemonSalvaje.nombre} HP: ${pokemonSalvaje.hp}")
        println("Elige un ataque")
        for ((index, p) in pokemon.listaAtaques.withIndex()) {
            println("$index - ${p.nombre}/${p.poder}")
        }
        val ataqueSeleccionado = scanner.nextInt()

        if (procesarAtaque(pokemon, pokemonSalvaje, ataqueSeleccionado)) {
            break
        }

        val ataqueAleatorio: Int=1+ random.nextInt(pokemonSalvaje.listaAtaques.size)

        if (procesarAtaque(pokemonSalvaje, pokemon, ataqueAleatorio)) {
            break
        }
        else println("Ambos pokemon siguen de pie!! \n Continuar!!")

    } while (pokemon.hp > 0 && pokemonSalvaje.hp > 0)

}

fun procesarAtaque(pokemonAtacante: Pokemon, pokemonDefensor: Pokemon, ataqueSeleccionado: Int): Boolean {

    println("${pokemonAtacante.nombre} a usado ${pokemonAtacante.obtenerAtaque(ataqueSeleccionado).nombre}")

    val danoCalculado = calcularDano(pokemonAtacante, pokemonDefensor, pokemonAtacante.obtenerAtaque(ataqueSeleccionado))

    println("${pokemonDefensor.nombre} ha recibido $danoCalculado puntos de daño!")
    pokemonDefensor.hp -=danoCalculado

    if (pokemonDefensor.hp <= 0) {
        println("${pokemonDefensor.nombre} se agotó!")
        println("${pokemonAtacante.nombre} ganó la batalla!")
        return true
    }
    return false


}

fun calcularDano(pokemonAtacante: Pokemon, pokemonDefensor: Pokemon, obtenerAtaque: Ataque): Int =
        ((((2 * 1 + 10.0) / 250) * (pokemonAtacante.ataque / pokemonDefensor.defensa) * obtenerAtaque.poder) * 1.5).toInt()

fun elejirPokemon(opcion: Int): Pokemon = when (opcion) {
    Pokemones.BULBASOUR.id -> Pokemon(
            "Bulbasour", 45, 49, 49, arrayOf(
            Ataque("latigo sepa", 45),
            Ataque("Placaje", 40)))
    Pokemones.CHARMANDER.id -> Pokemon(
            "Charmander", 45, 45, 55, arrayOf(
            Ataque("Placaje", 40),
            Ataque("Latigo", 30)))
    Pokemones.SQUIRTLE.id -> Pokemon(
            "Squirtle", 45, 65, 30, arrayOf(
            Ataque("Placaje", 40),
            Ataque("Burbujas", 50)))
    Pokemones.PIKACHU.id -> Pokemon(
            "Pikachu", 35, 35, 60, arrayOf(
            Ataque("Ataque rápido", 45),
            Ataque("Impactrueno", 40)))
    else -> Pokemon(
            "MISSINGNO", 50, 1, 1, arrayOf(
            Ataque("Dia de Pago", 5),
            Ataque("Salpicar", 0)))
}

fun mostrarDatos(pokemon: Pokemon) {

    println("Has elegido a ${pokemon.nombre}")
    println("HP: ${pokemon.hp}")
    println("Ataque: ${pokemon.ataque}")
    println("Defensa: ${pokemon.defensa}")
}
