package com.jgbravo.pokedroidex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgbravo.pokedroidex.ui.pokemonDetail.PokemonDetailScreen
import com.jgbravo.pokedroidex.ui.pokemonList.PokemonListScreen
import java.util.Locale

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.PokemonListScreen.route
    ) {
        composable(route = AppScreens.PokemonListScreen.route) {
            PokemonListScreen(navController = navController)
        }
        composable(
            route = "${AppScreens.PokemonDetailScreen.route}/{dominantColor}/{pokemonName}",
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {
            val dominantColor = remember {
                val color = it.arguments?.getInt("dominantColor")
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            PokemonDetailScreen(
                dominantColor = dominantColor,
                pokemonName = pokemonName?.lowercase(Locale.getDefault()) ?: "",
                navController = navController
            )
        }
    }
}