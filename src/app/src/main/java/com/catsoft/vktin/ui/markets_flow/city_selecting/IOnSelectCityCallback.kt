package com.catsoft.vktin.ui.markets_flow.city_selecting

import com.catsoft.vktin.vkApi.model.VKCity

interface IOnSelectCityCallback {
    fun select(city : VKCity)
}