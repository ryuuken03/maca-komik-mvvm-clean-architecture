package mapan.developer.macakomik.presentation.genre

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.domain.usecase.genre.GetGenre
import mapan.developer.macakomik.util.Constants
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@HiltViewModel
class GenreViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getGenre: GetGenre,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ArrayList<ComicFilter>>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<ArrayList<ComicFilter>>> = _uiState
    var sources = context.resources.getStringArray(R.array.source_website_url)
    var index = -1
    var url = ""
    var isTheme = false

    fun setInit(ind : Int,isTheme :Boolean = false){
        this.index = ind
        this.isTheme = isTheme
        this.url = getGenreUrl()
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

    fun resetData(){
        _uiState.value = UiState.Loading()
    }

    fun getGenre(){
        CoroutineScope(Dispatchers.IO).launch {
            if(isTheme){
                var list = ArrayList<ComicFilter>()
//                if(index == 4){
                if(index == 0){
                    var themes = Constants.getThemes(index)
                    themes.forEach {
                        var filter = ComicFilter()
                        filter.name = it
                        filter.value = it.lowercase().replace(" ","-")
                        list.add(filter)
                    }
                }
                _uiState.value = UiState.Success(list)
            }else{
                try {
                    getGenre.execute(url)
                        .catch {
                            _uiState.value = UiState.Error(it.message.toString())
                        }
                        .collect { response ->
                            _uiState.value = UiState.Success(response)
                        }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(e.message.toString())
                }
            }
        }
    }
}