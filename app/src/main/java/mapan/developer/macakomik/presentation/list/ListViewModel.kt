package mapan.developer.macakomik.presentation.list

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
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.data.model.ComicThumbnail
import mapan.developer.macakomik.domain.usecase.list.GetList
import mapan.developer.macakomik.domain.usecase.list.GetListProject
import mapan.developer.macakomik.domain.usecase.list.GetListSearch
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    @ApplicationContext mContext: Context,
    private val getList: GetList,
    private val getListSearch : GetListSearch,
    private val getListProject : GetListProject,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<ComicList>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<ComicList>> = _uiState
    var sources = mContext.resources.getStringArray(R.array.source_website_url)
    var appName = mContext.resources.getString(R.string.app_name2)
    var titles = mContext.resources.getStringArray(R.array.source_website_title)
    var title = ""
    var index = -1
    var icon = -1
    var url = ""
    var genre = ""
    var type = ""
    var orderby = ""
    var search:String? = null
    var page = 1
    var isMax = false
    var stillLoad = false
    var pathUrl = "-"

    var listComic =  ArrayList<ComicThumbnail>()

    fun setInit(ind : Int, path :String = "-"){
        index = ind
        pathUrl = path
        title = appName+ " " +titles[index]
        if(!path.equals("-")){
            if(pathUrl.contains("project")||pathUrl.contains("pj")){
                title = "Proyek "+titles[index]
            }else if(path.contains("genre")){
                var split = path.split("/")
                title ="Genre "
                if(split.size > 0) {
                    var genre = split[1].split("-")
                    genre.forEach {
                        title += it.replaceFirstChar(Char::titlecase)+" "
                    }

                }
            }
        }
        when(index){
            0 -> { icon = R.drawable.ic_src_komikcast }
            1 -> { icon = R.drawable.ic_src_westmanga }
            2 -> { icon = R.drawable.ic_src_ngomik }
            3 -> { icon = R.drawable.ic_src_shinigami }
            else -> { icon = -1 }
        }
        changeUrl()
    }

    fun changeUrl(){
        //typeList = 0 -> By Project
        //typeList = 1 -> By Genre
        //else -> normal or search
        url = sources[index]
        when(index){
            0 ->{
                if(!pathUrl.equals("-")){
                    url+= pathUrl+"/page/"+page.toString()
                }else{
                    if(search!=null){
                        url+= "page/"+page.toString()+"?s="+search
                    }else{
                        url+= "daftar-komik/page/"+page.toString()+getOtherQuery()
                    }
                }
            }
            1 ->{
                if(!pathUrl.equals("-")){
                    url+= pathUrl+"/page/"+page.toString()
                }else{
                    if(search!=null){
                        url+= "page/"+page.toString()+"?s="+search
                    }else{
                        url+= "manga?page="+page+"&"+getOtherQuery()
                    }
                }
            }
            2 ->{
                if(!pathUrl.equals("-")){
                    url+= pathUrl+"/page/"+page.toString()
                }else{
                    if(search!=null){
                        url+= "page/"+page.toString()+"?s="+search
                    }else{
                        url+= "manga?page="+page+"&"+getOtherQuery()
                    }
                }
            }
            else ->{
                url = sources[index]
            }
        }
    }

    fun getOtherQuery() : String{
        var result = ""
        if(index != 3){
            when(index){
                0 ->{
                    result = "?"
                    if(genre.equals("") && type.equals("") && orderby.equals("")){
                        result += "sortby=update"
                    }else{
                        if(!genre.equals("")){
                            result += "genre%5B%5D="+genre + "&"
                        }
                        result += "status="
                        result += "&type="
                        if(!type.equals("")){
                            result += type
                        }
                        result += "&orderby="
                        if(!orderby.equals("")){
                            if(orderby.equals("update")){
                                result.replace("orderby","sortby")
                            }
                            result += orderby
                        }
                    }
                }
                1 ->{
                    if(genre.equals("") && type.equals("") && orderby.equals("")){
                        result += "order=update"
                    }else{

                    }
                }
                2 ->{
                    if(genre.equals("") && type.equals("") && orderby.equals("")){
                        result += "order=update"
                    }else{

                    }
                }
            }
        }
        return result
    }

    fun getData(){
        if(!stillLoad){
            stillLoad = true
            if(!pathUrl.equals("-")){
                getListProject()
            }else{
                if(search == null){
                    getList()
                }else{
                    getListSearch()
                }
            }
        }
    }

    fun resetData(){
        listComic.clear()
        isMax = false
        page = 1
        changeUrl()
        _uiState.value = UiState.Loading()
    }

    fun getList(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var params = listOf(url,page.toString())
                getList.execute(params)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { response ->
                        var list = response.list
                        if(list!=null){
                            if(list.size == 0){
                                isMax = true
                            }
                        }else{
                            isMax = true
                        }
                        if(list!=null){
                            listComic.addAll(list)
                        }
                        response.list = listComic
                        _uiState.value = UiState.Success(response)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getListSearch(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var params = listOf(url,search!!,page.toString())
                getListSearch.execute(params)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { response ->
                        var list = response.list
                        if(list!=null){
                            if(list.size == 0){
                                isMax = true
                            }
                        }else{
                            isMax = true
                        }
                        list?.forEach { comic ->
                            listComic.add(comic)
                        }
                        response.list = listComic
                        _uiState.value = UiState.Success(response)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getListProject(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var params = listOf(url,page.toString())
                getListProject.execute(params)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { response ->
                        var list = response.list
                        if(list!=null){
                            if(list.size == 0){
                                isMax = true
                            }
                        }else{
                            isMax = true
                        }
                        list?.forEach { comic ->
                            listComic.add(comic)
                        }
                        response.list = listComic
                        _uiState.value = UiState.Success(response)
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun loadMore(){
        page++
        changeUrl()
        getData()
    }
}