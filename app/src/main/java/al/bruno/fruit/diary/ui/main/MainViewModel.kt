package al.bruno.fruit.diary.ui.main

import al.bruno.fruit.diary.data.source.FruitRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val fruitRepository: FruitRepository) : ViewModel() {

    public fun syncFruit() {
        viewModelScope.launch(Dispatchers.IO) {
            val fruit = fruitRepository.fruitRemote()
            if(fruit.isSuccessful) {
                fruit.body()?.let { fruitRepository.insert(it) }
            }
        }
    }
}