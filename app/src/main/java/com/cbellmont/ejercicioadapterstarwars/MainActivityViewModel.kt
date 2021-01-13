package com.cbellmont.ejercicioadapterstarwars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivityViewModel  : ViewModel() {

    private val films = mutableListOf(
        Film(1, "La Amenaza Fantasma", "aaaa"),
        Film(2, "El Ataque de los Clones", "aaaa"),
        Film(3, "La Venganza de los Sith", "aaaa"),
        Film(4, "Una Nueva Esperanza", "aaaa"),
        Film(5, "El Imperio Contraataca", "aaaa"),
        Film(6, "El Retorno del Jedi", "aaaa")
    )

    suspend fun getFilms(): MutableList<Film> {
        delay(2000)
        return films
    }


    suspend fun getOldFilms() : MutableList<Film> {
        delay(2000)
        return films.subList(3,6)
    }

    suspend fun getNewFilms() : MutableList<Film> {
        delay(2000)
        return films.subList(0,3)
    }
}