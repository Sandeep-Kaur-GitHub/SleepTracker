package com.example.sleeptracker.sleeptracker

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeptracker.R
import com.example.sleeptracker.convertDurationToFormatted
import com.example.sleeptracker.convertNumericQualityToString
import com.example.sleeptracker.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>()
{
    var data= listOf<SleepNight>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= data[position]
        val res = holder.itemView.context.resources
        holder.bind(item, res)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val sleepLength:TextView= itemView.findViewById(R.id.sleep_length)
        val quality:TextView=itemView.findViewById(R.id.quality_string)
        val  qualityImage:ImageView=itemView.findViewById(R.id.imageView)
        fun bind(item: SleepNight, res: Resources) {
            sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)

            qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_0
                }
            )
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
            }
        }
    }


}