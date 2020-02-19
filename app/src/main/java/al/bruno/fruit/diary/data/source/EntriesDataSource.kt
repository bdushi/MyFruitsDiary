package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.model.Entries
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*

interface EntriesDataSource {
    suspend fun entries() : Response<List<Entries>>

    suspend fun delete() : Response<ResponseBody>

    suspend fun delete(entryId: Long) : Response<ResponseBody>

    suspend fun entries(date: Map<String, Date>) : Response<ResponseBody>

    suspend fun entries(entryId: Long?,fruitId: Long?, nrOfFruit: Int?) : Response<ResponseBody>
}