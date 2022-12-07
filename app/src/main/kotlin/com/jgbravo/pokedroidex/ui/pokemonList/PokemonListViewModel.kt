package com.jgbravo.pokedroidex.ui.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.jgbravo.pokedroidex.data.repository.PokemonRepository
import com.jgbravo.pokedroidex.models.PokedexListEntry
import com.jgbravo.pokedroidex.util.Constants.IMG_POKEMON_URL
import com.jgbravo.pokedroidex.util.Constants.PAGE_SIZE
import com.jgbravo.pokedroidex.util.Resource.Error
import com.jgbravo.pokedroidex.util.Resource.Loading
import com.jgbravo.pokedroidex.util.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0
    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
            when (result) {
                is Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count!!
                    val pokedexEntries = result.data.results!!.mapIndexed { index, entry ->
                        val pokemonId = if (entry!!.url!!.endsWith("/")) {
                            entry.url!!.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url!!.takeLastWhile { it.isDigit() }
                        }
                        val url = "$IMG_POKEMON_URL/$pokemonId.png"
                        PokedexListEntry(
                            entry.name!!.replaceFirstChar { it.titlecase(Locale.ROOT) },
                            url,
                            pokemonId.toInt()
                        )
                    }
                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Loading -> Unit
            }
        }
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isBlank()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            } else {
                val result = listToSearch.filter {
                    it.pokemonName.contains(query.trim(), ignoreCase = true) || it.number.toString() == query.trim()
                }
                if (isSearchStarting) {
                    cachedPokemonList = pokemonList.value
                    isSearchStarting = false
                }
                pokemonList.value = result
                isSearching.value = true
            }
        }
    }

    fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}