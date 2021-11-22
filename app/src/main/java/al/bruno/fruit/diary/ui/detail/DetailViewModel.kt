package al.bruno.fruit.diary.ui.detail

import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.util.ErrorHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    fun delete(entryId: Long) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.Main) {
            _loading.postValue(false)
            try {
                val response = entriesRepository.delete(entryId)
                if (response.isSuccessful) {
                    _success.postValue(response.message())
                } else {
                    _error.postValue(errorHandler.parseError(response = response).message)
                }
            } catch (ex: HttpException) {
                _error.postValue(ex.message)
            }
        }
    }
}