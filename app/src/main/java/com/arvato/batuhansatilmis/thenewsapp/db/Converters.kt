package com.arvato.batuhansatilmis.thenewsapp.db

import androidx.room.TypeConverter
import com.arvato.batuhansatilmis.thenewsapp.models.Source

class Converters {


    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name

    }

    @TypeConverter
    fun toSource(name:String): Source{
        return Source(name,name)
    }

}