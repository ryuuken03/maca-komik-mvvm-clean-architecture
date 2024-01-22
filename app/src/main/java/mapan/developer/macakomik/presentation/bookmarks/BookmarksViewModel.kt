package mapan.developer.macakomik.presentation.bookmarks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.domain.usecase.bookmarks.DeleteUrlComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetListComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetSearchListComicSave
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 12/01/2024
 */
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getListComicSave: GetListComicSave,
    private val getSearchListComicSave: GetSearchListComicSave,
    private val deleteUrlComicSave: DeleteUrlComicSave,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<MutableList<ComicSaveEntity>>> = MutableStateFlow(
        UiState.Loading())
    val uiState: StateFlow<UiState<MutableList<ComicSaveEntity>>> = _uiState

    var search:String? = null
    var deleteId:Long = -1
    var deleteUrl:String? = null

    fun getList(){
        search = null
        _uiState.value = UiState.Loading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getListComicSave.execute(Unit)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { list ->
                        _uiState.value = UiState.Success(list)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getSearchList(search:String){
        this.search = search
        _uiState.value = UiState.Loading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getSearchListComicSave.execute(search)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { list ->
                        _uiState.value = UiState.Success(list)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun deleteSave(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteUrlComicSave.execute(deleteUrl!!).apply {
                getList()
//                deleteId = -1.toLong()
                deleteUrl = null
            }
        }
    }

}