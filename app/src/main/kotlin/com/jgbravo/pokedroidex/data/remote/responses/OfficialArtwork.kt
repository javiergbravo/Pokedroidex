package com.jgbravo.pokedroidex.data.remote.responses


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)