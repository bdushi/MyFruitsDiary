package al.bruno.fruit.diary.data.source.local.dao

import al.bruno.fruit.diary.model.Fruit
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {
    @Query("SELECT * FROM fruit")
    fun fruits(): Flow<List<Fruit>>

    @Query("SELECT * FROM fruit WHERE _id IN (:fruits)")
    fun loadAllByIds(fruits: IntArray): List<Fruit>

    @Query("SELECT * FROM fruit WHERE type LIKE :type LIMIT 1")
    fun findByName(type: String): Fruit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg fruits: Fruit)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruits: List<Fruit>)

    @Delete
    fun delete(fruit: Fruit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(fruits: List<Fruit>)
    @Query("SELECT * FROM fruit")
    fun load(): LiveData<List<Fruit>>
}
