package ca.qc.cstj.monpokedex.models

import com.github.kittinunf.fuel.android.core.Json

data class Pokemon(val json: Json) {
    val pokemonNo = json.obj().getString("pokedexNo")
    val nom = json.obj().getString("name")

}