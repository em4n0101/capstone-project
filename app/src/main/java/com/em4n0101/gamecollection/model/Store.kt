package com.em4n0101.gamecollection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Store (
    val id: Int,
    val url_en: String?,
    val store: InnerStore?
) : Parcelable