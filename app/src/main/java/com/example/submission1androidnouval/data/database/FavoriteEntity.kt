package com.example.submission1androidnouval.data.database
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.submission1androidnouval.data.response.DetailEvent
import com.example.submission1androidnouval.data.response.ListEvent
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "summary")
    var summary: String? = null,

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,

    @ColumnInfo(name = "ownerName")
    var ownerName: String? = null,

    @ColumnInfo(name = "cityName")
    var cityName: String? = null,

    @ColumnInfo(name = "quota")
    var quota: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "beginTime")
    var beginTime: String? = null,

    @ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "link")
    var link: String? = null,

    @ColumnInfo(name = "imageLogo")
    var imageLogo: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "registrants")
    var registrants: Int? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable

fun FavoriteEntity.toListEventsItem(): ListEvent {
    return ListEvent(
        id = id,
        name = name!!,
        summary = summary!!,
        description = description!!,
        beginTime = beginTime!!,
        endTime = endTime!!,
        mediaCover = mediaCover!!,
        imageLogo = imageLogo!!,
        link = link!!,
        ownerName = ownerName!!,
        cityName = cityName!!,
        category = category!!,
        quota = quota!!,
        registrants = registrants!!


    )
}

