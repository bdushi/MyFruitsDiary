package al.bruno.fruit.diary.util

suspend fun <T: Any> handleRequest(requestFunc: suspend () -> T) {

}