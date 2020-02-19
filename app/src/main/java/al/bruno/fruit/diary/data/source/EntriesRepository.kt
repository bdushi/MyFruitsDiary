package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.model.Entries
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class EntriesRepository @Inject constructor(private val entriesDataSource: EntriesDataSource) {
    suspend fun entries() : Response<List<Entries>> {
        return entriesDataSource.entries()
    }

    suspend fun entries(date: Map<String, Date>): Response<ResponseBody> {
        return entriesDataSource.entries(date)
    }

    suspend fun delete(entryId: Long): Response<ResponseBody> {
        return entriesDataSource.delete(entryId)
    }


    suspend fun entries(entryId: Long?,fruitId: Long?, nrOfFruit: Int?) : Response<ResponseBody> {
        return entriesDataSource.entries(entryId, fruitId, nrOfFruit)
    }
}