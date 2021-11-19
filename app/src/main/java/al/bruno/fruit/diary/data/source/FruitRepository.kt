package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.data.source.local.FruitLocalDataSource
import al.bruno.fruit.diary.data.source.remote.FruitRemoteDataSource
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ApiResponse
import al.bruno.fruit.diary.util.NetworkBoundResource
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class FruitRepository @Inject constructor(
    private val fruitRemoteDataSource: FruitRemoteDataSource,
    private val fruitLocalDataSource: FruitLocalDataSource) {

    suspend fun fruitRemote(): Response<List<Fruit>> {
        return fruitRemoteDataSource.fruit()
    }

    suspend fun insert(fruits: List<Fruit>) {
        return fruitLocalDataSource.insert(fruits)
    }

    fun fruits(): Flow<List<Fruit>> {
        // Check fruits for updates
        return fruitLocalDataSource.fruits()
    }

    fun fruit() : LiveData<List<Fruit>> {
        return object : NetworkBoundResource<List<Fruit>, List<Fruit>>() {
            override fun saveCallResult(item: List<Fruit>) {
                fruitLocalDataSource.save(item)
            }

            override fun shouldFetch(data: List<Fruit>?): Boolean {
                return if(data != null) { data.size < 0 } else { true }
            }

            override fun loadFromDb(): LiveData<List<Fruit>> {
                return fruitLocalDataSource.load()
            }

            override fun createCall(): LiveData<ApiResponse<List<Fruit>>> {
                return fruitRemoteDataSource.getFruit()
            }

        }.asLiveData()
    }
}