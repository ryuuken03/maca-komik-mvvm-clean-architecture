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
import mapan.developer.macakomik.data.model.ComicChapter
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
    var search = ""
    var bookmarkDataSet : ComicSaveEntity?= null
    private val _isAddedBookmark = MutableStateFlow(false)
    private val _bookmarkData:MutableStateFlow<ComicSaveEntity?> = MutableStateFlow(null)
    val isAddedBookmark = _isAddedBookmark.asStateFlow()
    val bookmarkData = _bookmarkData.asStateFlow()
    var listChapter = ArrayList<ComicChapter>()

    fun searchChapter(s:String){
        if(_uiState.value is UiState.Success){
            this.search = s
            var chapters = ArrayList<ComicChapter>()
            listChapter.forEach {
                var isAdd = false
                if(s.equals("")){
                    isAdd = true
                }else{
                    if(it.name!!.lowercase().contains(s.lowercase())){
                        isAdd = true
                    }
                }
                if(isAdd){
                    chapters.add(it)
                }
            }
            var detail = uiState.value.data
            detail!!.list = chapters
            _uiState.value = UiState.Success(detail)
        }
    }
    fun setLoading(){
        _uiState.value = UiState.Loading()
    }
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
                        listChapter.clear()
                        listChapter.addAll(comicDetail.list!!)
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
        bookmarkDataSet = save
    }

    fun checkBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            getComicSave.execute(url)
                .catch {
                    _isAddedBookmark.value = false
                }
                .collect{ comic->
                    if(comic != null){
                        _bookmarkData.value = comic
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
                    insertComicSave.execute(bookmarkDataSet!!)
                    _isAddedBookmark.value = true
                }
                .collect{ comic->
                    if(comic != null){
                        deleteUrlComicSave.execute(url).apply {
                            insertComicSave.execute(bookmarkDataSet!!)
                        }
                    }else{
                        insertComicSave.execute(bookmarkDataSet!!)
                    }
                    _bookmarkData.value = bookmarkDataSet
                    _isAddedBookmark.value = true
                }
        }
    }
}