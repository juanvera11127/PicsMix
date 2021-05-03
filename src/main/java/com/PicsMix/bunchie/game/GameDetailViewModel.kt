package com.PicsMix.bunchie.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class GameDetailViewModel() : ViewModel() {
    private val gameRepository = GameRepository.get()
    private val gameIdLiveData = MutableLiveData<UUID>()
    var gameLiveData: LiveData<Game?> =
        Transformations.switchMap(gameIdLiveData) { gameId ->
            gameRepository.getGame(gameId)
        }
    fun loadCrime(crimeId: UUID) {
        gameIdLiveData.value = crimeId
    }

    fun saveCrime(crime: Game) {
        gameRepository.updateCrime(crime)
    }
}