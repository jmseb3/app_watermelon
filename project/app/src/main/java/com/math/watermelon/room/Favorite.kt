package com.math.watermelon.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class mathdata (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var concept: String,
    var topic: String,
    var ans: Int,
    var imgsrc: String,
    var qimgsrc: String,
    var imgsrcend: String,
    var favorite: String? = null
)
