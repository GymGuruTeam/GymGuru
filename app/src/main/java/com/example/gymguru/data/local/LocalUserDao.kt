package com.example.gymguru.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gymguru.data.model.UserWeightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWeight(userWeightEntity: UserWeightEntity)

    @Query("SELECT * FROM user_weight_table ORDER BY id DESC")
    fun observeAllUserWeights(): Flow<List<UserWeightEntity>>

    @Query("DELETE FROM user_weight_table WHERE id = :weightId")
    suspend fun deleteUserWeightById(weightId: Int)
}
