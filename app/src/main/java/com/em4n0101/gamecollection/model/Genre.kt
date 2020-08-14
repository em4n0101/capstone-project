package com.em4n0101.gamecollection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Genre (
    val id: Int,
    val name: String?,
    val slug: String?
) : Parcelable