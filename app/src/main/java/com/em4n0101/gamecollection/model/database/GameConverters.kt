package com.em4n0101.gamecollection.model.database

import androidx.room.TypeConverter
import com.em4n0101.gamecollection.model.*

class GameConverters {
    companion object {
      /*  @TypeConverter
        @JvmStatic
        fun fromParent_platforms(value: List<Platform>?): String {
            return value. ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun toRating(value: Double): Rating {
            return Rating(value)
        }*/

        @TypeConverter
        @JvmStatic
        fun fromGenres(value: List<Genre>): String {
            val res = arrayListOf<String>()
            for (genre in value) {
                res.add(genre.name ?: "")
            }
            return res.joinToString(separator = ",")
        }

        @TypeConverter
        @JvmStatic
        fun toGenres(value: String): List<Genre> {
            val res = value.split(",")
            val genreList = arrayListOf<Genre>()
            for (genre in res) {
                genreList.add(Genre(0, genre, genre))
            }
            return genreList
        }

        @TypeConverter
        @JvmStatic
        fun fromShort_screenshots(value: List<ScreenShot>): String {
            val res = arrayListOf<String>()
            for (screenshot in value) {
                res.add(screenshot.image ?: emptyImageUrl)
            }
            return res.joinToString(separator = ",")
        }

        @TypeConverter
        @JvmStatic
        fun toShort_screenshots(value: String): List<ScreenShot> {
            val res = value.split(",")
            val screenshotList = arrayListOf<ScreenShot>()
            for (screenshot in res) {
                screenshotList.add(ScreenShot(0, screenshot))
            }
            return screenshotList
        }

        @TypeConverter
        @JvmStatic
        fun fromParent_platforms(value: List<Platform>): String {
            val res = arrayListOf<Int>()
            for (platform in value) {
                res.add(platform.platform.id)
            }
            return res.joinToString(separator = ",")
        }

        @TypeConverter
        @JvmStatic
        fun toParent_platforms(value: String): List<Platform> {
            val res = value.split(",")
            val platformList = arrayListOf<Platform>()
            for (platformId in res) {
                platformList.add(Platform(InnerPlatform(platformId.toInt(), "")))
            }
            return platformList
        }

        @TypeConverter
        @JvmStatic
        fun fromClip(value: Clip): String {
            return value.clip + "," + value.video + "," + value.preview
        }

        private const val emptyImageUrl =
            "https://lh3.googleusercontent.com/proxy/NsdqXQzwKh_V4kvqtVw9DDoG8pbOOJoGAPv8UQUS-rJD0a-0tB6ypPgy3hoat3G4aMNp9mcC6uQZLoFu6-dcHhQlZBGaepWBGis_gRzJMmzg7PDrYHN8OGmP-xVdSucc"
        private const val emptyVideoUrl =
            "https://media.rawg.io/media/stories-640/5b0/5b0cfff8c606c5e4db4f74f108c4413b.mp4"

        @TypeConverter
        @JvmStatic
        fun toClip(value: String): Clip {
            val images = value.split(",")
            var clip = emptyVideoUrl
            var video = emptyVideoUrl
            var preview = emptyImageUrl
            if (images.size == 3) {
                clip = images[0]
                video = images[1]
                preview = images[2]
            }
            return Clip(
                clip,
                video,
                preview
            )
        }

    }
}