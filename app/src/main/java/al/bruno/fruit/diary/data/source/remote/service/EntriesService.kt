package al.bruno.fruit.diary.data.source.remote.service

import al.bruno.fruit.diary.model.Entries
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface EntriesService {
    @GET("entries")
    suspend fun entries() : Response<List<Entries>>

    @DELETE("entries")
    suspend fun delete() : Response<ResponseBody>

    @DELETE("entries/{entryId}")
    suspend fun delete(@Path("entryId") entryId: Long) : Response<ResponseBody>

    @JvmSuppressWildcards
    @POST("entries")
    suspend fun entries(@Body date: Map<String, Date>) : Response<ResponseBody>

    @POST("entry/{entryId}/fruit/{fruitId}")
    suspend fun entries(
        @Path("entryId") entryId: Long?,
        @Path("fruitId") fruitId: Long?,
        @Query("amount") nrOfFruit: Int?) : Response<ResponseBody>
}