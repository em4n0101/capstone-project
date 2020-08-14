package com.em4n0101.gamecollection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Clip (
    val clip: String,
    val video: String,
    val preview: String
) : Parcelable