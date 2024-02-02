package mapan.developer.macakomik.presentation.setting

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.domain.usecase.setting.BackupDataDB
import mapan.developer.macakomik.domain.usecase.setting.RestoreDataDB
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 01/02/2024
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext var mContext: Context,
    private val backupDataDB: BackupDataDB,
    private val restoreDataDB: RestoreDataDB,
) : ViewModel(){
    private val _versionName = MutableStateFlow("")
    val versionName = _versionName.asStateFlow()

    private val _stillBackup = MutableStateFlow(false)
    val stillBackup = _stillBackup.asStateFlow()

    private val _stillRestore = MutableStateFlow(false)
    val stillRestore = _stillRestore.asStateFlow()

    private val _messageToast = MutableStateFlow("")
    val messageToast = _messageToast.asStateFlow()

    init {
        try {
            _versionName.value = mContext.getPackageManager()
                .getPackageInfo(mContext.getPackageName(), 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun resetMessageToast(){
        _messageToast.value = ""
    }

    fun backupOffline(){
        _stillBackup.value = true
        CoroutineScope(Dispatchers.IO).launch {
            backupDataDB.execute(Unit)
            .catch {
                _stillBackup.value = false
                _messageToast.value = it.message.toString()
            }
            .collect {
                _stillBackup.value = false
                if(it == 0){
                    _messageToast.value = "Gagal Backup Data"
                }else{
                    _messageToast.value = "Backup Data Telah Sukses"
                }
            }
        }
    }

    fun restoreOffline(){
        _stillRestore.value = true
        CoroutineScope(Dispatchers.IO).launch {
            restoreDataDB.execute(true)
            .catch {
                _stillRestore.value = false
                _messageToast.value = it.message.toString()
            }
            .collect {
                _stillRestore.value = false
                if(it == -1){
                    _messageToast.value = "Anda tidak memiliki Data Backup"
                }else if(it == 0){
                    _messageToast.value = "Gagal Restore Data"
                }else{
                    _messageToast.value = "Restore Data Telah Sukses"
                }
            }
        }
    }
}