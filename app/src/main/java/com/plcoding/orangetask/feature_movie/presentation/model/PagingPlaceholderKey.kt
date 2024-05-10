package com.plcoding.orangetask.feature_movie.presentation.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("BanParcelableUsage")
data class PagingPlaceholderKey(private val index: Int) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = parcel.writeInt(index)

    override fun describeContents(): Int = 0

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())
                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}