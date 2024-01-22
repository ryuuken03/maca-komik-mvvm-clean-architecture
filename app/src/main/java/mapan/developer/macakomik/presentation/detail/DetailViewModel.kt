package mapan.developer.macakomik.presentation.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.domain.usecase.bookmarks.DeleteUrlComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.InsertComicSave
import mapan.developer.macakomik.domain.usecase.detail.GetDetail
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetail: GetDetail,
    private val insertComicSave: InsertComicSave,
    private val deleteUrlComicSave: DeleteUrlComicSave,
    private val getComicSave: GetComicSave,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ComicDetail>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<ComicDetail>> = _uiState

    var url = ""
    var imgSrc = ""
    var bookmarkData : ComicSaveEntity?= null
    private val _isAddedBookmark = MutableStateFlow(false)
    val isAddedBookmark = _isAddedBookmark.asStateFlow()

    fun changeUrl(s:String, image:String){
        url = s
        imgSrc = image
        checkBookmarks()
        getDetail()
    }

    fun getDetail(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getDetail.execute(url)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { comicDetail ->
                        setBookmark(comicDetail)
                        _uiState.value = UiState.Success(comicDetail)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun setBookmark(detail : ComicDetail){
        var save = ComicSaveEntity()
        save.urlDetail = url
        save.imgSrc = imgSrc
        save.title = detail.title
        save.genre = detail.genre
        save.type = detail.type
        bookmarkData = save
    }

    fun checkBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            getComicSave.execute(url)
                .catch {
                    _isAddedBookmark.value = false
                }
                .collect{ comic->
                    if(comic != null){
                        _isAddedBookmark.value = true
                    }else{
                        _isAddedBookmark.value = false
                    }
                }
        }
    }

    fun addBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            getComicSave.execute(url)
                .catch {
                    insertComicSave.execute(bookmarkData!!)
                    _isAddedBookmark.value = true
                }
                .collect{ comic->
                    if(comic != null){
                        deleteUrlComicSave.execute(url).apply {
                            insertComicSave.execute(bookmarkData!!)
                        }
                    }else{
                        insertComicSave.execute(bookmarkData!!)
                    }
                    _isAddedBookmark.value = true
                }
        }
    }
}