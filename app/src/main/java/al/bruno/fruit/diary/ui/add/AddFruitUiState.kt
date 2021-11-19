package al.bruno.fruit.diary.ui.add

sealed class AddFruitUiState {
    data class Success(val success: String): AddFruitUiState()
    data class Error(val exception: String?): AddFruitUiState()
}