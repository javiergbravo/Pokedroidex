package com.jgbravo.pokedroidex.data.repository

import com.jgbravo.pokedroidex.data.remote.responses.Pokemon
import com.jgbravo.pokedroidex.data.remote.responses.PokemonList
import com.jgbravo.pokedroidex.util.Resource

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>
}