package al.bruno.fruit.diary.ui.add

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.adapter.BindingData
import al.bruno.fruit.diary.adapter.CustomListAdapter
import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.data.source.FruitRepository
import al.bruno.fruit.diary.databinding.AddFruitSingleItemBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ErrorHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

class AddFruitViewModel @Inject constructor(
    private val fruitRepository: FruitRepository,
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    lateinit var onItemSelectedListener: OnItemClickListener<Fruit>
    val adapter = CustomListAdapter(
        R.layout.add_fruit_single_item,
        object : BindingData<Fruit, AddFruitSingleItemBinding> {
            override fun bindData(t: Fruit?, vm: AddFruitSingleItemBinding) {
                vm.fruit = t
                vm.onItemClick = onItemSelectedListener
            }
        },
        object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem == newItem
        })

    fun fruits() {
        viewModelScope.launch {
            fruitRepository.fruits().collect {
                adapter.submitList(it)
            }
        }
    }

    fun entries(entryId: Long?, fruitId: Long?, nrOfFruit: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = entriesRepository.entries(entryId, fruitId, nrOfFruit)
                if(response.isSuccessful) {
                    _success.postValue(response.message())
                } else {
                    _error.postValue(errorHandler.parseError(response).message)
                }
            } catch (ex: HttpException) {
                _error.postValue(ex.message)
            }
        }
    }
}