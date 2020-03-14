package com.c.v.di.module

import com.c.v.ui.markets_flow.market_list.MarketListFragment
import com.c.v.ui.unsubscribe_flow.group_detail.GroupDetailFragment
import com.c.v.ui.unsubscribe_flow.group_list.GroupListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMarketListFragment(): MarketListFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupListFragment(): GroupListFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupDetailFragment(): GroupDetailFragment
}