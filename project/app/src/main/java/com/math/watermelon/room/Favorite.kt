package com.math.watermelon.room

import androidx.room.ColumnInfo
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

@Entity(tableName = "college")
data class college (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")var id: Int?,
        @ColumnInfo(name = "year")var year : String,
        @ColumnInfo(name = "qnumber")var qnumber : String,
        @ColumnInfo(name = "imgMainSrc")var imgMainSrc: String,
        @ColumnInfo(name = "imgQueSrc")var imhQueSrc: String,
        @ColumnInfo(name = "conceptId") var conceptid : Int
)
