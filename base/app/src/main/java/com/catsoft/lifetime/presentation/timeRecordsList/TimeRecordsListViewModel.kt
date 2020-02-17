package com.catsoft.lifetime.presentation.timeRecordsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catsoft.lifetime.dal.TimeRecordDao
import com.catsoft.lifetime.domain.TimeRecordModel
import com.catsoft.lifetime.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TimeRecordsListViewModel @Inject constructor(var recordDao: TimeRecordDao) : BaseViewModel() {

    val timeRecordsList: LiveData<List<TimeRecordModel>> = recordDao.getTimeRecords()

    fun addTimeRecord() {
        runBlocking {
            launch(Dispatchers.IO) {
                recordDao.insert(TimeRecordModel())
            }
        }
    }
}