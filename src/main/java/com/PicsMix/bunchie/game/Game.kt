package com.PicsMix.bunchie.game

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false)