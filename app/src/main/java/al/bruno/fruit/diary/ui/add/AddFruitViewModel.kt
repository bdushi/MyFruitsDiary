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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFruitViewModel @Inject constructor(
    private val fruitRepository: FruitRepository,
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler) : ViewModel() {

    private val _addFruitUiState = MutableStateFlow<AddFruitUiState>(AddFruitUiState.Success(""))
    val addFruitUiState: StateFlow<AddFruitUiState> = _addFruitUiState

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
            runCatching {
                entriesRepository.entries(entryId, fruitId, nrOfFruit)
            }.onSuccess {
                if(it.isSuccessful) {
                    _addFruitUiState.value = AddFruitUiState.Success(it.message())
                } else {
                    _addFruitUiState.value = AddFruitUiState.Error(errorHandler.parseError(it).message)
                }
            }.onFailure {
                _addFruitUiState.value = AddFruitUiState.Error(it.message)
            }
        }
    }
}