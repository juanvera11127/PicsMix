package com.PicsMix.bunchie.game.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.PicsMix.bunchie.game.Game
import java.util.*

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getGames(): LiveData<List<Game>>
    @Query("SELECT * FROM game WHERE id=(:id)")
    fun getGame(id: UUID): LiveData<Game?>
    @Update
    fun updateCrime(game: Game)
    @Insert
    fun addPicture(game: Game)
    @Insert
    fun addGuess(game: Game)
}