package com.catsoft.lifetime.ui.screens.timeRecordsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.lifetime.R
import com.catsoft.lifetime.di.Injectable
import com.catsoft.lifetime.presentation.timeRecordsList.TimeRecordsListAdapter
import com.catsoft.lifetime.presentation.timeRecordsList.TimeRecordsListViewModel
import kotlinx.android.synthetic.main.fragment_time_records_list.*
import javax.inject.Inject

class TimeRecordsListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: TimeRecordsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_time_records_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimeRecordsListViewModel::class.java)

        val button = add_time_record!!
        button.setOnClickListener{
            viewModel.addTimeRecord()
        }

        val adapter = TimeRecordsListAdapter()

        val recyclerView = recyclerView_timeRecordsList!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        viewModel.timeRecordsList.observe(this, Observer {
            Toast.makeText(this.context, it.count().toString(), Toast.LENGTH_LONG).show()
            adapter.replaceItems(it)
        })
    }
}