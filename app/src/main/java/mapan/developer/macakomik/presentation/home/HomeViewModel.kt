package mapan.developer.macakomik.presentation.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.domain.usecase.home.GetHome
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.remote.model.SourceFB
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.domain.usecase.genre.GetGenre
import mapan.developer.macakomik.domain.usecase.home.GetComicSource
import mapan.developer.macakomik.domain.usecase.home.UpdateComicSource
import mapan.developer.macakomik.util.Constants
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext mContext: Context,
    private val getHome: GetHome,
    private val updateComicSource: UpdateComicSource,
    private val getComicSource: GetComicSource,
    private val getGenre: GetGenre,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ComicHome>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<ComicHome>> = _uiState

    private val _uiGenreState: MutableStateFlow<UiState<ArrayList<ComicFilter>>> = MutableStateFlow(UiState.Loading())
    val uiGenreState: StateFlow<UiState<ArrayList<ComicFilter>>> = _uiGenreState

    private val _uiThemeState: MutableStateFlow<UiState<ArrayList<ComicFilter>>> = MutableStateFlow(UiState.Loading())
    val uiThemeState: StateFlow<UiState<ArrayList<ComicFilter>>> = _uiThemeState

    var sources = mContext.resources.getStringArray(R.array.source_website_url)
    var sourceTitles = mContext.resources.getStringArray(R.array.source_website_title)

    private var _sourceFBState = MutableStateFlow<List<SourceFB>>(emptyList())
    val sourceFBState = _sourceFBState.asStateFlow()

    var index = 0
    var url = ""
    var appName = mContext.resources.getString(R.string.app_name2)

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _canBrowse = MutableStateFlow(true)
    val canBrowse = _canBrowse.asStateFlow()

    init {
        getSource()
        getFromFirebase()
    }

    fun getIcon() : Int{
        var icon = 0
        when(index){
            0 -> { icon = R.drawable.ic_src_komikindo }
            1 -> { icon = R.drawable.ic_src_westmanga }
            2 -> { icon = R.drawable.ic_src_ngomik }
            3 -> { icon = R.drawable.ic_src_shinigami }
            4 -> { icon = R.drawable.ic_src_komikcast }
            else -> { icon = -1 }
        }
        return icon
    }
    fun setIndexSource(ind:Int){
        if(canBrowse.value){
            index = ind
            url = sources[index]
            _title.value = appName+" "+sourceTitles[index]
            setLoading()
            addSource()
        }
    }

    fun setSourceUrl(data : SourceFB){
        if(canBrowse.value){
            index = (data.id!!-1).toInt()
            url = data.url!!
            _title.value = appName+" "+data.title
            setLoading()
            addSource()
        }
    }
    fun setLoading(){
        _uiState.value = UiState.Loading()
    }

    fun getBrowse(){
        if(!url.equals("")){
            if(canBrowse.value){
                _canBrowse.value = false
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        getGenre.execute(getGenreUrl())
                            .catch {
                                _uiGenreState.value = UiState.Error(it.message.toString())
                            }
                            .collect { list ->
                                _uiGenreState.value = UiState.Success(list)
                            }
                    } catch (e: Exception) {
                        _uiGenreState.value = UiState.Error(e.message.toString())
                    }
//                if(index == 4){
                    if(index == 0){
                        var listThemes = ArrayList<ComicFilter>()
                        var themes = Constants.getThemes(index)
                        themes.forEach {
                            var filter = ComicFilter()
                            filter.name = it
                            filter.value = it.lowercase().replace(" ","-")
                            listThemes.add(filter)
                        }
                        _uiThemeState.value = UiState.Success(listThemes)
                    }else{
                        _uiThemeState.value = UiState.Success(arrayListOf())
                    }
                    try {
                        getHome.execute(url)
                            .catch {
                                _canBrowse.value = true
                                _uiState.value = UiState.Error(it.message.toString())
                            }
                            .collect { comicHome ->
                                _canBrowse.value = true
                                _uiState.value = UiState.Success(comicHome)
                            }
                    } catch (e: Exception) {
                        _canBrowse.value = true
                        _uiState.value = UiState.Error(e.message.toString())
                    }
                }
            }
        }
    }

    fun getGenreUrl():String{
        var resultUrl = sources[index]
        when(index){
            4 ->{
                resultUrl+= "daftar-komik/page/1?sortby=update"
            }
            1 ->{
                resultUrl+= "manga?page=1&order=update"
            }
            2 ->{
                resultUrl+= "manga?page=1&order=update"
            }
            else ->{
                resultUrl = sources[index]
            }
        }
        return resultUrl
    }

    fun getSource(){
        CoroutineScope(Dispatchers.IO).launch {
            getComicSource.execute(Unit)
                .catch {
                    setIndexSource(index)
                }
                .collect{ source->
                    setIndexSource(source)
                }
        }
    }

    fun addSource() {
        CoroutineScope(Dispatchers.IO).launch {
            updateComicSource.execute(index)
        }
    }

    fun getFromFirebase(){
        var db = Firebase.firestore

        val data = db.collection("source")
        data.orderBy("id",Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }

                if(value != null){
                    _sourceFBState.value = value.toObjects()
                }
            }
    }
}