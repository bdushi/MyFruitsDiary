package al.bruno.fruit.diary.ui.my.diary

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.adapter.BindingData
import al.bruno.fruit.diary.adapter.BindingChildData
import al.bruno.fruit.diary.adapter.CustomListAdapter
import al.bruno.fruit.diary.adapter.SectionAdapter
import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.databinding.EntriesSingleItemBinding
import al.bruno.fruit.diary.databinding.FruitSingleItemBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Entries
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

class MyDiaryViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    lateinit var onItemSelectedListener: OnItemClickListener<Entries>
    private val _text = MutableLiveData<String>().apply { value = "This is dashboard Fragment" }
    val text: LiveData<String> = _text

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    fun entries() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.Main) {
            _loading.postValue(false)
            try {
                if (entriesRepository.entries().isSuccessful) {
                    entriesRepository.entries().body()?.let { adapter.submitList(it) }
                } else {
                    _error.postValue(errorHandler.parseError(response = entriesRepository.entries()).message)
                }
            } catch (ex: HttpException) {
                _error.postValue(ex.message)
            } catch (ex: TimeoutException) {
                _error.postValue(ex.message)
            }
        }

    }

    fun entries(date: Date) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                if (entriesRepository.entries(date = mapOf("date" to date)).isSuccessful) {
                    entries()
                } else {
                    _loading.postValue(false)
                    _error.postValue(errorHandler.parseError(response = entriesRepository.entries()).message)
                }
            }
            catch (ex: HttpException) {
                _loading.postValue(false)
                _error.postValue(ex.message)
            } catch (ex: TimeoutException) {
                _error.postValue(ex.message)
            }
        }
    }

    val adapter = CustomListAdapter(
        R.layout.entries_single_item,
        object : BindingData<Entries, EntriesSingleItemBinding> {
            override fun bindData(t: Entries?, vm: EntriesSingleItemBinding) {
                vm.entries = t
                vm.onItemClick = onItemSelectedListener
            }
        },
        object : DiffUtil.ItemCallback<Entries>() {
            override fun areItemsTheSame(oldItem: Entries, newItem: Entries): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Entries, newItem: Entries): Boolean =
                oldItem == newItem
        })


//    val adapter = SectionAdapter(
//        R.layout.entries_single_item,
//        R.layout.fruit_single_item,
//        object : BindingData<Entries, EntriesSingleItemBinding> {
//            override fun bindData(t: Entries?, vm: EntriesSingleItemBinding) {
//                vm.entries = t
//                vm.onItemClick = onItemSelectedListener
//            }
//        }, object : BindingChildData<Entries, Fruit, FruitSingleItemBinding> {
//            override fun bindData(t: Entries?, c: Fruit?, vm: FruitSingleItemBinding) {
//                vm.fruit = c
//            }
//
//        })

}