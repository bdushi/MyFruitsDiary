package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ApiResponse
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FruitDataSource {
    suspend fun fruit() : Response<List<Fruit>>
    fun fruits(): Flow<List<Fruit>>
    suspend fun insert(fruits: List<Fruit>)
    fun load(): LiveData<List<Fruit>>
    fun save(fruits: List<Fruit>)
    fun getFruit(): LiveData<ApiResponse<List<Fruit>>>
}