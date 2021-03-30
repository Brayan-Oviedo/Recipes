package co.recipes.data.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Person(open var name: String = "", open var imageUrl: String = "") : Parcelable