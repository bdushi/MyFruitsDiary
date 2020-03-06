package al.bruno.fruit.diary.data.source.remote.service

import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.*

interface FruitService {
    @GET("fruit")
    suspend fun fruit(): Response<List<Fruit>>

    @GET("fruit")
    fun getFruit(): LiveData<ApiResponse<List<Fruit>>>
}
