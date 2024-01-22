package mapan.developer.macakomik.presentation.read

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.domain.usecase.bookmarks.DeleteUrlComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.InsertComicSave
import mapan.developer.macakomik.domain.usecase.detail.GetDetail
import mapan.developer.macakomik.domain.usecase.history.DeleteUrlComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetComicHistory
import mapan.developer.macakomik.domain.usecase.history.InsertComicHistory
import mapan.developer.macakomik.domain.usecase.read.GetRead
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
@HiltViewModel
class ReadViewModel @Inject constructor(
    private val getRead: GetRead,
    private val getDetail: GetDetail,
    private val insertComicHistory: InsertComicHistory,
    private val deleteUrlComicHistory: DeleteUrlComicHistory,
    private val getComicHistory: GetComicHistory,
    private val insertComicSave: InsertComicSave,
    private val deleteUrlComicSave: DeleteUrlComicSave,
    private val getComicSave: GetComicSave,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ComicChapterPageList>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<ComicChapterPageList>> = _uiState

    var url = ""

    private val _currentChapter = MutableStateFlow("")
    val currentChapter = _currentChapter.asStateFlow()

    private val _currentPage = MutableStateFlow("-")
    val currentPage = _currentPage.asStateFlow()

    private val _maxPage = MutableStateFlow("-")
    val maxPage = _maxPage.asStateFlow()

    var urlDetail = ""
    var imgSrc = ""
    var init = false

    private val _isDetailAddedBookmark = MutableStateFlow(false)
    val isDetailAddedBookmark = _isDetailAddedBookmark.asStateFlow()

    private val _isAddedBookmark = MutableStateFlow(false)
    val isAddedBookmark = _isAddedBookmark.asStateFlow()

    fun setCurrentPageText(cPage:String){
        _currentPage.value = cPage
    }

    fun changeUrl(url1 : String, url2 : String, image : String){
        url = url1
        if(!url2.equals("")){
            urlDetail = url2
        }
        if(!image.equals("")){
            imgSrc = image
        }
        init = true
        checkBookmarks()
        _uiState.value = UiState.Loading()
    }

    fun setLoading(){
        _uiState.value = UiState.Loading()
    }

    fun getRead(){
        CoroutineScope(Dispatchers.IO).launch {
            _currentChapter.value = ""
            _currentPage.value = "-"
            _maxPage.value = "-"
            try {
                var list = listOf(url,urlDetail)
                getRead.execute(list)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { readChapter ->
                        if(readChapter.list !=null){
                            if(readChapter.list!!.size > 0){
                                _currentPage.value = "1"
                                _maxPage.value = readChapter.list?.size.toString()
                                _currentChapter.value = readChapter.currentChap!!
                            }
                        }
                        delay(2000)
                        _uiState.value = UiState.Success(readChapter)
                        var isAddHistory = true
                        if(readChapter == null){
                            isAddHistory = false
                        }else{
                            if(readChapter.urlChapter == null){
                                isAddHistory = false
                            }
                        }
                        if(isAddHistory){
                            getDetail(true)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getDetail(isAddHistory : Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getDetail.execute(urlDetail)
                    .catch {
                    }
                    .collect { detail ->
                        _uiState.value.let {uiState ->
                            if(uiState is UiState.Success){
                                var data = uiState.data
                                if(isAddHistory){
                                    var history = ComicHistoryEntity()
                                    history.urlDetail = urlDetail
                                    history.imgSrc = imgSrc
                                    history.title = detail.title
                                    history.chapter = data?.currentChap
                                    history.urlChapter = data?.urlChapter
                                    history.genre = detail.genre
                                    history.pageChapter = null
                                    history.type = detail.type
                                    addHistory(history)
                                }else{
                                    var save = ComicSaveEntity()
                                    save.urlDetail = urlDetail
                                    save.imgSrc = imgSrc
                                    save.title = detail.title
                                    save.chapter = data?.currentChap
                                    save.urlChapter = data?.urlChapter
                                    save.genre = detail.genre
                                    save.pageChapter = null
                                    save.type = detail.type
                                    addBookmark(save)
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
            }
        }
    }

    fun addHistory(comicHistory : ComicHistoryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            getComicHistory.execute(urlDetail)
                .catch {
                    insertComicHistory.execute(comicHistory)
                }
                .collect{ comic->
                    if(comic != null){
                        deleteUrlComicHistory.execute(urlDetail).apply {
                            insertComicHistory.execute(comicHistory)
                        }

                    }else{
                        insertComicHistory.execute(comicHistory)
                    }
                }
        }
    }

    fun checkBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            getComicSave.execute(urlDetail)
                .catch {
                    _isDetailAddedBookmark.value = false
                    _isAddedBookmark.value = false
                }
                .collect{ comic->
                    if(comic != null){
                        _isDetailAddedBookmark.value = true
                        if(comic.urlChapter.equals(url)){
                            _isAddedBookmark.value = true
                        }else{
                            _isAddedBookmark.value = false
                        }
                    }else{
                        _isDetailAddedBookmark.value = false
                        _isAddedBookmark.value = false
                    }
                }
        }
    }

    fun addBookmark(comicSave : ComicSaveEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            getComicSave.execute(urlDetail)
                .catch {
                    insertComicSave.execute(comicSave)
                    _isAddedBookmark.value = true
                    _isDetailAddedBookmark.value = true
                }
                .collect{ comic->
                    if(comic != null){
                        deleteUrlComicSave.execute(urlDetail).apply {
                            insertComicSave.execute(comicSave)
                        }
                    }else{
                        insertComicSave.execute(comicSave)
                    }
                    _isAddedBookmark.value = true
                    _isDetailAddedBookmark.value = true
                }
        }
    }
}