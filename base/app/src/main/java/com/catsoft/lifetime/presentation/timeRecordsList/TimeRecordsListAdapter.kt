package com.catsoft.lifetime.presentation.timeRecordsList

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catsoft.lifetime.R
import com.catsoft.lifetime.domain.TimeRecordModel
import org.jetbrains.anko.layoutInflater

class TimeRecordsListAdapter : RecyclerView.Adapter<TimeRecordsListAdapter.TimeRecordsListHolder>() {

    private var timeRecords: List<TimeRecordModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = TimeRecordsListHolder(parent.context.layoutInflater.inflate(R.layout.row_time_records_list, parent, false))

    override fun onBindViewHolder(timeRecordModel: TimeRecordsListHolder, position: Int) = timeRecordModel.bind(timeRecords[position])

    override fun getItemCount() = timeRecords.size

    fun replaceItems(items: List<TimeRecordModel>) {
        timeRecords = items
        notifyDataSetChanged()
    }

    class TimeRecordsListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(timeRecordModel: TimeRecordModel) = with(itemView) {

        }
    }
}
