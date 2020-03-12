package com.c.v.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c.v.di.CustomViewModelFactory
import com.c.v.di.ViewModelKey
import com.c.v.ui.markets_flow.market_list.MarketListViewModel
import com.c.v.ui.unsubscribe_flow.group_list.GroupListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MarketListViewModel::class)
    abstract fun bindMarketListViewModel(marketListViewModel: MarketListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupListViewModel::class)
    abstract fun bindGroupListViewModel(groupListViewModel: GroupListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}