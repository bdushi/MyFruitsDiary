package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.util.ErrorHandler
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class EntriesRepository @Inject constructor(
    private val entriesDataSource: EntriesDataSource,
    private val errorHandler: ErrorHandler
) {
    suspend fun entries() =
        try {
            Result.Loading
            val response = entriesDataSource.entries()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(errorHandler.parseError(response = response).message)
            }
        } catch (ex: Exception) {
            Result.Error(ex.message)
        }

    suspend fun entries(date: Map<String, Date>) =
        try {
            Result.Loading
            val response = entriesDataSource.entries(date)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(errorHandler.parseError(response = response).message)
            }
        } catch (ex: Exception) {
            Result.Error(ex.message)
        }

    suspend fun delete(entryId: Long): Response<ResponseBody> {
        return entriesDataSource.delete(entryId)
    }


    suspend fun entries(entryId: Long?, fruitId: Long?, nrOfFruit: Int?): Response<ResponseBody> {
        return entriesDataSource.entries(entryId, fruitId, nrOfFruit)
    }
}