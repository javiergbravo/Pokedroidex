package com.jgbravo.pokedroidex.data.repository

import com.jgbravo.pokedroidex.data.remote.PokeApi
import com.jgbravo.pokedroidex.data.remote.responses.Pokemon
import com.jgbravo.pokedroidex.data.remote.responses.PokemonList
import com.jgbravo.pokedroidex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
) : PokemonRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error ocurred")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error ocurred")
        }
        return Resource.Success(response)
    }
}