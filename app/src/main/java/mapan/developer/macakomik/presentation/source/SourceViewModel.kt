package mapan.developer.macakomik.presentation.source

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.datasource.remote.model.SourceFB
import mapan.developer.macakomik.domain.usecase.setting.BackupDataDB
import mapan.developer.macakomik.domain.usecase.setting.RestoreDataDB
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 01/02/2024
 */
@HiltViewModel
class SourceViewModel @Inject constructor(
    @ApplicationContext var mContext: Context,
) : ViewModel(){

    private var _sourceFBState = MutableStateFlow<List<SourceFB>>(emptyList())
    val sourceFBState = _sourceFBState.asStateFlow()

    var sources = mContext.resources.getStringArray(R.array.source_website_url)
    var sourceTitles = mContext.resources.getStringArray(R.array.source_website_title)

    init {
        getFromFirebase()
    }

    fun getFromFirebase(){
        var db = Firebase.firestore

        val data = db.collection("source")
        data.orderBy("id", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }

                if(value != null){
                    _sourceFBState.value = value.toObjects()
                    if(_sourceFBState.value.size == 0){
                        val source = db.collection("source")
                        for(i in 0 .. sources.size-1){
                            var set = SourceFB()
                            set.id = (i+1).toLong()
                            set.title = sourceTitles[i]
                            set.url = sources[i]
                            source.document(i.toString()).set(set)
                        }
                    }
                }
            }
    }

//    fun updateSource(index:Int){
    fun updateSource(update:SourceFB){
        var db = Firebase.firestore

        val doc = db.collection("source").document((update.id!!-1).toString())

        doc.set(update)
            .addOnSuccessListener {
                _sourceFBState.value = emptyList()
                getFromFirebase()
            }
            .addOnFailureListener { e ->
            }
    }
}