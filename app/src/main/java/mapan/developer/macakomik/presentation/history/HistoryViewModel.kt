package mapan.developer.macakomik.presentation.history

import android.content.Context
import android.util.Log
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
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.domain.usecase.history.DeleteAllComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetListComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetSearchListComicHistory
import mapan.developer.macakomik.domain.usecase.home.GetHome
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 11/01/2024
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getListComicHistory: GetListComicHistory,
    private val getSearchListComicHistory: GetSearchListComicHistory,
    private val deleteAllComicHistory : DeleteAllComicHistory,
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<MutableList<ComicHistoryEntity>>> = MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<MutableList<ComicHistoryEntity>>> = _uiState

    var search:String? = null
    var stillLoad = false

    fun getData(search:String? = null){
        if(!stillLoad){
            stillLoad = true
            this.search = search
            _uiState.value = UiState.Loading()
            if(search == null){
                getList()
            }else {
                getSearchList(search)
            }

        }
    }

    fun getList(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getListComicHistory.execute(Unit)
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getSearchListComicHistory.execute(search)
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

    fun deleteAllHistory(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteAllComicHistory.execute(Unit).apply {
                getData()
            }
        }
    }


}