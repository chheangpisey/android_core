package ig.core.android.utils

import android.content.Context
import android.net.ParseException
import ig.core.android.R
import java.text.SimpleDateFormat
import java.util.*

/****
 *
 * Created by ORN TONY on 01/01/19.
 *
 */

object DateTime {
    const val FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val FORMAT_APP = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_PROJECT_PROGRESS = "dd MMM yy"
    const val FORMAT_YEAR = "yyyy"
    const val FORMAT_CAPTURE_IMAGE = "yyyy-MM-dd HH:mm:ss"

    const val SERVER_TIME_ZONE = "UTC"
    const val LOCAL_TIME_ZONE = "GMT+07:00"

    private val V_SECOND: Long = 1
    private val V_MINUTE = 60 * V_SECOND
    private val V_HOUR = 60 * V_MINUTE
    private val V_DAY = 24 * V_HOUR
    private val V_WEEK = 7 * V_DAY
    private var myTimeAgo = "Moment Ago"

    val todayDateAtSixString: String
        get() {
            val dateFormat = SimpleDateFormat(FORMAT_APP, Locale.getDefault())
            return dateFormat.format(today())
        }

    val yesterdayDateAtSixString: String
        get() {
            val dateFormat = SimpleDateFormat(FORMAT_APP, Locale.getDefault())
            return dateFormat.format(yesterday())
        }

    fun getDateTime(format: String): String {
        val df = SimpleDateFormat(format, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone(LOCAL_TIME_ZONE)
        return df.format(Calendar.getInstance().time)
    }

    /***
     * @param formatFromApp
     * @param formatToServer
     * @param dateTime
     * @return
     */
    fun convertDateFromAppToServer(formatFromApp: String, formatToServer: String, dateTime: String): String {
        val dateParser = SimpleDateFormat(formatFromApp, Locale.getDefault())
        dateParser.timeZone = TimeZone.getTimeZone(LOCAL_TIME_ZONE)
        var date: Date? = null
        try {
            date = dateParser.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        // Then convert the Date to a String, formatted as you dd/MM/yyyy
        val dateFormatter = SimpleDateFormat(formatToServer, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone(SERVER_TIME_ZONE)
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        dateParser.timeZone = tz
        return if (null == date) {
            ""
        } else {
            dateFormatter.format(date)
        }
    }

    fun convertDateFromServerToApp(formatFromServer: String, formatToApp: String, dateTime: String): String {
        var dateString = ""
        try {
            val dateParser = SimpleDateFormat(formatFromServer, Locale.getDefault())
            dateParser.timeZone = TimeZone.getTimeZone(SERVER_TIME_ZONE)

            try {
                val date = dateParser.parse(dateTime)
                val dateFormatter = SimpleDateFormat(formatToApp, Locale.getDefault())
                dateFormatter.timeZone = TimeZone.getTimeZone(LOCAL_TIME_ZONE)
                dateString = dateFormatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dateString
    }

    private fun convertInTheSameTimeZone(formatFromServer: String, formatToApp: String, dateTime: String): String {
        var dateString = ""
        try {
            val dateParser = SimpleDateFormat(formatFromServer, Locale.getDefault())

            try {
                val date = dateParser.parse(dateTime)
                val dateFormatter = SimpleDateFormat(formatToApp, Locale.getDefault())
                dateString = dateFormatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dateString
    }

    fun convertDateUtcToTimeStamp(myDate: String, myDateFormat: String): Long {
        return try {
            val formatter = SimpleDateFormat(myDateFormat, Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone(SERVER_TIME_ZONE)
            val date: Date
            try {
                date = formatter.parse(myDate)
                date.time
            } catch (e: ParseException) {
                e.printStackTrace()
                0L
            }

        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    private fun convertDateToTimeStamp(myDate: String, myDateFormat: String): Long {
        return try {
            val formatter = SimpleDateFormat(myDateFormat, Locale.getDefault())
            val date: Date
            try {
                date = formatter.parse(myDate)
                date.time
            } catch (e: ParseException) {
                e.printStackTrace()
                0L
            }

        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }

    }

    fun convertFromTimeStampToDate(myTimeStamp: Long, myFormat: String): String {
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(LOCAL_TIME_ZONE)
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(LOCAL_TIME_ZONE)
        calendar.timeInMillis = myTimeStamp
        val tz = TimeZone.getDefault()
        sdf.timeZone = tz
        return sdf.format(calendar.time)
    }

    private fun yesterday(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        cal.set(Calendar.HOUR_OF_DAY, 6)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.time
    }

    private fun today(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 12)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.time
    }

    fun convertTimeAgoFromDate(myContext: Context, myLastDate: String, myServerFormat: String, myResultDateFormat: String): String {
        val myLastTimeStamp = convertDateToTimeStamp(myLastDate, myServerFormat) / 1000

        val mElapseTime = System.currentTimeMillis() / 1000 - myLastTimeStamp
        myTimeAgo = when {
            mElapseTime <= 5 * V_SECOND -> myContext.resources.getString(R.string.momment_ago)
            mElapseTime < V_MINUTE -> "${mElapseTime / V_SECOND} ".plus(myContext.resources.getString(R.string.second_ago))
            mElapseTime == V_MINUTE -> "${mElapseTime / V_SECOND} ".plus(myContext.resources.getString(R.string.second_ago))
            mElapseTime < V_HOUR -> "${mElapseTime / V_MINUTE} ".plus(myContext.resources.getString(R.string.minute_ago))
            mElapseTime == V_HOUR -> "${mElapseTime / V_MINUTE} ".plus(myContext.resources.getString(R.string.minute_ago))
            mElapseTime < V_DAY -> "${mElapseTime / V_HOUR} ".plus(myContext.resources.getString(R.string.hour_ago))
            mElapseTime == V_DAY -> "${mElapseTime / V_HOUR} ".plus(myContext.resources.getString(R.string.hour_ago))
            mElapseTime < V_WEEK -> "${mElapseTime / V_DAY} ".plus(myContext.resources.getString(R.string.day_ago))
            mElapseTime <= 4 * V_WEEK -> convertInTheSameTimeZone(myServerFormat, myResultDateFormat, myLastDate)
            else -> convertInTheSameTimeZone(myServerFormat, myResultDateFormat, myLastDate)
        }
        return myTimeAgo
    }
}