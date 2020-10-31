package com.c.v.di.module

import com.c.v.ui.check_in_flow.friends.FriendsListFragment
import com.c.v.ui.check_in_flow.places.PlacesListFragment
import com.c.v.ui.markets_flow.market_list.MarketListFragment
import com.c.v.ui.sharing_flow.pick_photo.PickImageFragment
import com.c.v.ui.sharing_flow.share_content.ShareContentFragment
import com.c.v.ui.unsubscribe_flow.user_group_detail.UserGroupDetailFragment
import com.c.v.ui.unsubscribe_flow.user_group_list.UserGroupListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMarketListFragment(): MarketListFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupListFragment(): UserGroupListFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupDetailFragment(): UserGroupDetailFragment

    @ContributesAndroidInjector
    abstract fun contributePickImageFragment(): PickImageFragment

    @ContributesAndroidInjector
    abstract fun contributeShareContentFragment(): ShareContentFragment

    @ContributesAndroidInjector
    abstract fun contributePlacesListFragment(): PlacesListFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendsListFragment(): FriendsListFragment
}