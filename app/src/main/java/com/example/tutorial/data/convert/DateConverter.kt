package com.example.tutorial.data.convert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime

// Date ROOM Converter
@RequiresApi(Build.VERSION_CODES.O)
class DateConverter {

    @TypeConverter
    fun fromTimestamp(dateString : String) : LocalDateTime? {
        //toDateString 메서드에서 date가 Null 일경우 String "null" 을 반환하는듯
        if("null" == dateString)
            return null
        return LocalDateTime.parse(dateString)
    }

    @TypeConverter
    fun toDateString(date : LocalDateTime?) : String {
        return date.toString()
    }
}