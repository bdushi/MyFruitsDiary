package al.bruno.fruit.diary.data.source.remote

import al.bruno.fruit.diary.data.source.EntriesDataSource
import al.bruno.fruit.diary.data.source.remote.service.EntriesService
import al.bruno.fruit.diary.model.Entries
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class EntriesRemoteDataSource @Inject constructor(private val entriesService: EntriesService) : EntriesDataSource {
    override suspend fun entries(): Response<List<Entries>> {
        return entriesService.entries()
    }

    override suspend fun entries(date: Map<String, Date>): Response<ResponseBody> {
        return entriesService.entries(date)
    }

    override suspend fun entries(entryId: Long?, fruitId: Long?, nrOfFruit: Int?): Response<ResponseBody> {
        return entriesService.entries(entryId, fruitId, nrOfFruit)
    }

    override suspend fun delete(): Response<ResponseBody> {
        return entriesService.delete()
    }

    override suspend fun delete(entryId: Long): Response<ResponseBody> {
        return entriesService.delete(entryId)
    }
}