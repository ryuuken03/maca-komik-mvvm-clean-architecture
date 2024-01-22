package mapan.developer.macakomik.data.datasource.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Entity
data class ComicSaveEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long?= null,

    @ColumnInfo(name = "title")
    var title: String?= null,

    @ColumnInfo(name = "type")
    var type: String?= null,

    @ColumnInfo(name = "genre")
    var genre: String?= null,

    @ColumnInfo(name = "imgSrc")
    var imgSrc: String?= null,

    @ColumnInfo(name = "urlDetail")
    var urlDetail: String?= null,

    @ColumnInfo(name = "chapter")
    var chapter: String?= null,

    @ColumnInfo(name = "urlChapter")
    var urlChapter: String?= null,

    @ColumnInfo(name = "pageChapter")
    var pageChapter: Int?= null,
)
