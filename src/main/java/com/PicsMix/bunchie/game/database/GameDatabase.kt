package com.PicsMix.bunchie.game.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.PicsMix.bunchie.game.Game

@Database(entities = [ Game::class ], version=1, exportSchema = false)
@TypeConverters(GameTypeConverters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}