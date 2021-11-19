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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFruitViewModel @Inject constructor(
    private val fruitRepository: FruitRepository,
    private val entriesRepository: EntriesRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {
    /**
     * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     * https://github.com/Kotlin/kotlinx.coroutines/issues/2515
     */
    private val _addFruitUiState = MutableSharedFlow<AddFruitUiState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    // resetReplayCache()
    val addFruitUiState: SharedFlow<AddFruitUiState> = _addFruitUiState

    lateinit var onItemSelectedListener: (fruit: Fruit) -> Unit

    val adapter = CustomListAdapter(
        R.layout.add_fruit_single_item,
        object : BindingData<Fruit, AddFruitSingleItemBinding> {
            override fun bindData(t: Fruit?, vm: AddFruitSingleItemBinding) {
                vm.fruit = t
                vm.onItemClick = object : OnItemClickListener<Fruit> {
                    override fun onItemClick(t: Fruit) {
                        onItemSelectedListener.invoke(t)
                    }

                    override fun onLongItemClick(t: Fruit): Boolean {
                        TODO("Not yet implemented")
                    }

                }
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
                if (it.isSuccessful) {
                    _addFruitUiState.emit(AddFruitUiState.Success(it.message()))
                    _addFruitUiState.resetReplayCache()
                } else {
                    _addFruitUiState.emit(AddFruitUiState.Error(errorHandler.parseError(it).message))
                    _addFruitUiState.resetReplayCache()
                }
            }.onFailure {
                _addFruitUiState.emit(AddFruitUiState.Error(it.message))
                _addFruitUiState.resetReplayCache()
            }
        }
    }
}