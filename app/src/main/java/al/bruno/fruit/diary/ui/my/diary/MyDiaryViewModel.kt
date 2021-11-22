package al.bruno.fruit.diary.ui.my.diary

import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.data.source.Result
import al.bruno.fruit.diary.model.Entries
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MyDiaryViewModel @Inject constructor(
    private val entriesRepository: EntriesRepository
) : ViewModel() {
    private val _entries =
        MutableStateFlow<Result<List<Entries>>>(Result.Success<List<Entries>>(arrayListOf()))
    val entries: StateFlow<Result<List<Entries>>> = _entries

    private val _entriess =
        MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val entriess: StateFlow<Result<Unit>> = _entriess

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    var loading: StateFlow<Boolean> = _loading

    fun entries() {
        viewModelScope.launch(Dispatchers.IO) {
            _entries.value = entriesRepository.entries()
        }
    }
    fun entries(date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            _entriess.value = entriesRepository.entries(date = mapOf("date" to date))
        }
    }
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