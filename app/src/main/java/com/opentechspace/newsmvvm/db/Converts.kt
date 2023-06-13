package com.opentechspace.newsmvvm.db


import androidx.room.TypeConverter
import com.opentechspace.newsmvvm.Model.Source

class Converts {

   @TypeConverter
    fun fromSource(source: Source) : String
    {
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source
    {
        return Source(name,name)
    }
}
