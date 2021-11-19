package al.bruno.fruit.diary.util

import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject
import retrofit2.Converter
import retrofit2.Response
import al.bruno.fruit.diary.model.Error

class ErrorHandler @Inject constructor(private val retrofit: Retrofit) {
    fun parseError(response: Response<*>): Error {
        return try {
            if (response.errorBody() != null) {
                retrofit
                    .responseBodyConverter<Error>(
                        Error::class.java,
                        arrayOfNulls(0)
                    )
                    .convert(response.errorBody()) ?: Error(400, "Error Body is Null")
            } else {
                Error(400, "Error Body is Null")
            }
        } catch (e: Exception) {
            Error(400, e.message)
        }
    }
}