package com.math.watermelon.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao {

    //    수학 데이터
    @Query("SELECT * FROM mathdata WHERE concept=:concept")
    fun getmathdata(concept: String): LiveData<List<mathdata>>

    @Query("SELECT * FROM mathdata WHERE id=:id")
    fun getmathdatabyid(id: Int): mathdata

    @Query("UPDATE mathdata set favorite= :favorite WHERE id=:id")
    fun changefavorite(id: Int, favorite: String?)

    @Query("SELECT * FROM mathdata WHERE concept=:concept AND favorite is not null")
    fun getfavoritdataWithConcept(concept: String): LiveData<List<mathdata>>

    @Query("SELECT * FROM mathdata WHERE favorite is not null ")
    fun getfavoritdatanotlive(): List<mathdata>

    @Query("SELECT * FROM mathdata WHERE topic Like '%' || :topic || '%'")
    fun searchtopic(topic: String): LiveData<List<mathdata>>

    //    수능 데이터
    @Query("SELECT * FROM college")
    fun getCollegeData(): List<college>
    @Query("SELECT qnumber FROM college")
    fun getCollegeqnumber(): List<String>

    @Query("SELECT * FROM college WHERE id=:id")
    fun getCollegeDataById(id: Int): college

}
