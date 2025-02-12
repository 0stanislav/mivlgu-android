package com.wavecat.mivlgu

import java.util.*

class Utils {
    companion object {
        fun getSemester(calendar: Calendar): String =
            if (calendar.get(Calendar.MONTH) < Calendar.SEPTEMBER) "2" else "1"


        fun getYear(calendar: Calendar): String {
            val result = Calendar.getInstance()
            result.timeInMillis = calendar.timeInMillis
            if (getSemester(calendar) == "2")
                result.add(Calendar.YEAR, -1)
            return result.get(Calendar.YEAR).toString()
        }
    }
}