package com.jgbravo.pokedroidex.ui.navigation

sealed class AppScreens(val route: String) {
    object PokemonListScreen : AppScreens("pokemon_list_screen")
    object PokemonDetailScreen : AppScreens("pokemon_detail_screen")
}
