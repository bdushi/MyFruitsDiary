package al.bruno.fruit.diary.ui.detail

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.adapter.BindingData
import al.bruno.fruit.diary.adapter.CustomListAdapter
import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.databinding.FruitSingleItemBinding
import al.bruno.fruit.diary.model.Basket
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ErrorHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import kotlin.collections.ArrayList

class DetailViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler,
    private val basket: Basket) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val _empty = MutableLiveData<Boolean>()
    var empty: LiveData<Boolean> = _empty

    val adapter = CustomListAdapter(
        R.layout.fruit_single_item,
        object : BindingData<Fruit, FruitSingleItemBinding> {
            override fun bindData(t: Fruit?, vm: FruitSingleItemBinding) {
                vm.fruit = t
            }
        },
        object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem == newItem
        })

    fun fruit(fruits: ArrayList<Fruit>) {
        if(fruits.isEmpty()) {
            basket.fruit?.let { fruits.add(it) }
        } else {
            for (f in fruits) {
                if (f.id == basket.fruit?.id) {
                    basket.fruit?.let {
                        fruits[fruits.indexOf(f)] = it
                    }
                }
            }
        }
        adapter.submitList(fruits)
        _empty.postValue(adapter.itemCount > 0)
    }


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
            } catch (ex: TimeoutException) {
                _error.postValue(ex.message)
            }
        }
    }
}