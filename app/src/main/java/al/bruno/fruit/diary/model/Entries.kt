package al.bruno.fruit.diary.model

import al.bruno.fruit.diary.section.recycler.view.Section
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


data class Entries(val id: Long, val date: Date, val fruit: List<Fruit>?) : Section<Entries, Fruit>, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        Date(parcel.readLong()),
        parcel.createTypedArrayList(Fruit)
    )

    override fun items(): List<Fruit> {
        return fruit ?: ArrayList()
    }

    override fun section(): Entries {
        return this;
    }

    fun date() : String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(date.time)
        parcel.writeTypedList(fruit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Entries> {
        override fun createFromParcel(parcel: Parcel): Entries {
            return Entries(parcel)
        }

        override fun newArray(size: Int): Array<Entries?> {
            return arrayOfNulls(size)
        }
    }

}