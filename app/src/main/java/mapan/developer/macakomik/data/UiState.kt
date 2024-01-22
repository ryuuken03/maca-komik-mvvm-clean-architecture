package mapan.developer.macakomik.data

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
sealed class UiState<T>(val data:T?=null,val message:String?=null){
    class Loading<T>(): UiState<T>()

    class Success<T>(data:T): UiState<T>(data = data)

    class Error<T>(message:String): UiState<T>(message = message)
}