package com.mdasrafulalam.news.adapter

import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdasrafulalam.news.R
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*

@BindingAdapter("app:setImageResources")
fun setImageResources(imageView: ImageView, imgUrl:String){
    imgUrl.let {
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.downloading)
            )
            .error(R.drawable.broken_image)
            .into(imageView)
    }
//
//    Log.d("list", "list: $imgUrl")
}

@BindingAdapter("app:setBookMarkedIcon")
fun setBookMarkedIcon(imageView: ImageView, bookmarked: Boolean) {
    if (bookmarked) {
        imageView.setImageResource(R.drawable.bookmark)
    } else {
        imageView.setImageResource(R.drawable.bookmark_grey)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("app:dateTime")
fun setDateTime(textView: TextView, datetime:String){
    if (datetime!=null){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dt = LocalDateTime.parse(datetime, formatter)
        val calendar = Calendar.getInstance()
        calendar.set(dt.year, dt.monthValue, dt.dayOfMonth, dt.hour, dt.minute)
        val today = LocalDateTime.now()
        val todayMonth =  today.monthValue
        val todayDay =  today.dayOfMonth
        val todayYear =  today.year
        val fromDay = dt.dayOfMonth
        var fromMonth = dt.monthValue
        var fromYear = dt.year
        var hourDelay = today.hour  - dt.hour
        var dayDelay = todayDay - fromDay
        var monthDelay =  todayMonth - fromMonth
        var yearDelay =  todayYear - fromYear
        val start = Time(today.hour, today.minute, today.second)
        val stop = Time(dt.hour, dt.minute, dt.second)
        val diff: Time
        if (todayDay<fromDay){
            dayDelay = (todayDay+30) - fromDay
            fromMonth = fromMonth+1
            if (todayMonth<fromMonth){
                monthDelay = (todayMonth+12) - fromMonth
                fromYear = fromYear+1
            }else{
                monthDelay = todayMonth - fromMonth
                yearDelay = todayYear -fromYear
            }
        }else{
            dayDelay = todayDay - fromDay
            monthDelay = todayMonth - fromMonth
            yearDelay = todayYear - fromYear
        }


        if (dayDelay>0){
            textView.text = String.format("Published at: "+dt.dayOfMonth+"/"+dt.monthValue+"/"+dt.year)
        }else{
            diff = difference(stop, start)
            textView.text = String.format("Published "+diff.hours+" hours ago")
        }




        Log.d("checktime", "delay: $hourDelay, $hourDelay")

    }

}
fun difference(start: Time, stop: Time): Time {
    val diff = Time(0, 0, 0)

    if (stop.seconds > start.seconds) {
        --start.minutes
        start.seconds += 60
    }

    diff.seconds = start.seconds - stop.seconds
    if (stop.minutes > start.minutes) {
        --start.hours
        start.minutes += 60
    }

    diff.minutes = start.minutes - stop.minutes
    diff.hours = start.hours - stop.hours

    return diff
}