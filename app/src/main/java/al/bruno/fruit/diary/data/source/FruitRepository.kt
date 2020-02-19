package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.data.source.local.FruitLocalDataSource
import al.bruno.fruit.diary.data.source.remote.FruitRemoteDataSource
import al.bruno.fruit.diary.model.Fruit
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class FruitRepository @Inject constructor(
    private val fruitRemoteDataSource: FruitRemoteDataSource,
    private val fruitLocalDataSource: FruitLocalDataSource) {

    suspend fun fruitRemote(): Response<List<Fruit>> {
        return fruitRemoteDataSource.fruit()
    }
    suspend fun fruitLocal() {
        fruitLocalDataSource.fruit()
    }

    suspend fun insert(fruits: List<Fruit>) {
        return fruitLocalDataSource.insert(fruits)
    }

    fun fruits(): Flow<List<Fruit>> {
        // Check fruits for updates
        return fruitLocalDataSource.fruits()
    }
}