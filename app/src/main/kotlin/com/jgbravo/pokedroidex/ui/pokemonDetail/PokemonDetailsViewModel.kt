package com.jgbravo.pokedroidex.ui.pokemonDetail

import androidx.lifecycle.ViewModel
import com.jgbravo.pokedroidex.data.remote.responses.Pokemon
import com.jgbravo.pokedroidex.data.repository.PokemonRepository
import com.jgbravo.pokedroidex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return getPokemonInfo(pokemonName)
    }
}