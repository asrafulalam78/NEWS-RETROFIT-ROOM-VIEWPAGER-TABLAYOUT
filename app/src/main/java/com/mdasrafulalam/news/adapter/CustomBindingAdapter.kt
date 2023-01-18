package com.mdasrafulalam.news.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdasrafulalam.news.R

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

//@BindingAdapter("app:setDate")
//fun setFormattedDate(tv: TextView, date: Long) {
//    tv.text = getFormattedDateTime(date, "dd/MM/yyyy")
//}
//
//@BindingAdapter("app:setTime")
//fun setFormattedTime(tv: TextView, date: Long) {
//    tv.text = getFormattedDateTime(date, "hh:mm a")
//}