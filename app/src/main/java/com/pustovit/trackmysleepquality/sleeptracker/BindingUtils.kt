package com.pustovit.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pustovit.trackmysleepquality.R
import com.pustovit.trackmysleepquality.convertDurationToFormatted
import com.pustovit.trackmysleepquality.convertNumericQualityToString
import com.pustovit.trackmysleepquality.database.SleepNight


@BindingAdapter(value = ["sleepDurationFormatted"])
fun TextView.setSleepDurationFormatted(sleep: SleepNight?) {
    sleep?.let {
        text = convertDurationToFormatted(
            sleep.startTimeMilli,
            sleep.endTimeMilli,
            this.context.resources
        )
    }
}

@BindingAdapter(value = ["sleepQuality"])
fun TextView.setSleepQuality(sleep: SleepNight?) {
    sleep?.let {
        text = convertNumericQualityToString(sleep.sleepQuality, context.resources)
    }
}
@BindingAdapter(value = ["sleepImage"])
fun ImageView.setSleepImage(sleep: SleepNight?) {
    sleep?.let {
        this.setImageResource(
            when (sleep.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }
}