package mapan.developer.macakomik.data.datasource.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * Created By Mohammad Toriq on 7/9/2024
 */
@Entity
data class SourceEntity (
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Long?= null,
    @ColumnInfo(name = "title")
    var title: String?= null,
    @ColumnInfo(name = "url")
    var url: String?= null,
)