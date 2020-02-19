package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.model.Fruit
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FruitDataSource {
    suspend fun fruit() : Response<List<Fruit>>
    fun fruits(): Flow<List<Fruit>>
    suspend fun insert(fruits: List<Fruit>)
}