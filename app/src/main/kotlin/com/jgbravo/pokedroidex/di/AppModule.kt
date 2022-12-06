package com.jgbravo.pokedroidex.di

import com.jgbravo.pokedroidex.data.remote.PokeApi
import com.jgbravo.pokedroidex.data.repository.PokemonRepository
import com.jgbravo.pokedroidex.data.repository.PokemonRepositoryImpl
import com.jgbravo.pokedroidex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ): PokemonRepository = PokemonRepositoryImpl(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}