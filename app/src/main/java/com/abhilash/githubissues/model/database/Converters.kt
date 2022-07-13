package com.abhilash.githubissues.model.database

import androidx.room.TypeConverter
import com.abhilash.githubissues.model.entities.User
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun convertUserToString(user: User?): String? {
        return user?.toString()
    }

    @TypeConverter
    fun convertStringToUser(source: String?): User? {
        val gson = Gson()
        return gson.fromJson(source, User::class.java)
    }
}