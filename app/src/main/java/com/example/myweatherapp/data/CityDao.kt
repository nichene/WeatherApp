package com.example.myweatherapp.data

import androidx.room.*
import com.example.myweatherapp.entity.Favorite

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)


    @Delete()
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM TB_FAVORITE WHERE id= :id")
    fun favoriteById(id: Int): Favorite


    @Query("SELECT * FROM TB_FAVORITE")
    fun allFavorite(): List<Favorite>

}