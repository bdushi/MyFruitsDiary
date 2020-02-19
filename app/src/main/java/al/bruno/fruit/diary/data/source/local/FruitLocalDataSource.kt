package al.bruno.fruit.diary.data.source.local

import al.bruno.fruit.diary.data.source.FruitDataSource
import al.bruno.fruit.diary.data.source.local.dao.FruitDao
import al.bruno.fruit.diary.model.Fruit
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class FruitLocalDataSource @Inject constructor(private val fruitDao: FruitDao) : FruitDataSource {
    override suspend fun fruit(): Response<List<Fruit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fruits(): Flow<List<Fruit>> {
        return fruitDao.fruits()
    }

    override suspend fun insert(fruits: List<Fruit>) {
        return fruitDao.insert(fruits)
    }

}