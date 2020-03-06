package al.bruno.fruit.diary.data.source.remote

import al.bruno.fruit.diary.data.source.FruitDataSource
import al.bruno.fruit.diary.data.source.remote.service.FruitService
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ApiResponse
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class FruitRemoteDataSource @Inject constructor(private val fruitService: FruitService) : FruitDataSource {
    override suspend fun fruit(): Response<List<Fruit>> {
        return fruitService.fruit()
    }

    override fun fruits(): Flow<List<Fruit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insert(fruits: List<Fruit>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(): LiveData<List<Fruit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(fruits: List<Fruit>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFruit(): LiveData<ApiResponse<List<Fruit>>> {
        return fruitService.getFruit()
    }
}