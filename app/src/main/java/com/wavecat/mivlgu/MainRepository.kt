package com.wavecat.mivlgu

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.wavecat.mivlgu.models.ScheduleGetResult
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainRepository(context: Context) : Repository {
    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val facultyPreferences: SharedPreferences =
        context.getSharedPreferences("faculty", Context.MODE_PRIVATE)

    private val groupsPreferences: SharedPreferences =
        context.getSharedPreferences("groups", Context.MODE_PRIVATE)

    override var facultyIndex: Int
        get() = preferences.getInt(FACULTY_INDEX, 0)
        set(value) = preferences.edit {
            putInt(FACULTY_INDEX, value)
            apply()
        }

    override var teacherFio: String?
        get() = preferences.getString(TEACHER_FIO, "")
        set(value) = preferences.edit {
            putString(TEACHER_FIO, value)
            apply()
        }

    fun saveFacultyCache(facultyIndex: Int, data: List<String>) {
        facultyPreferences.edit {
            putString(facultyIndex.toString(), Json.encodeToString(data))
            apply()
        }
    }

    fun getFacultyCache(facultyIndex: Int): List<String> {
        return Json.decodeFromString(facultyPreferences.getString(facultyIndex.toString(), "[]")!!)
    }

    fun saveGroupsCache(group: String, data: ScheduleGetResult) {
        groupsPreferences.edit {
            putString(group, Json.encodeToString(data))
            apply()
        }
    }

    fun getGroupsCache(group: String): ScheduleGetResult {
        val result = groupsPreferences.getString(group, "")
        return if (result.isNullOrEmpty()) ScheduleGetResult(
            "",
            "",
            null,
            null,
            "",
            "",
            mapOf()
        )
        else Json.decodeFromString(result)
    }

    companion object {
        const val FACULTY_INDEX = "faculty_index"
        const val TEACHER_FIO = "teacher_fio"
    }
}