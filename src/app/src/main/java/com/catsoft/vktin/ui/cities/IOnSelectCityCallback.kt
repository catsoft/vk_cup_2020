package com.catsoft.vktin.ui.cities

import com.catsoft.vktin.vkApi.model.VKCity

interface IOnSelectCityCallback {
    fun select(city : VKCity)
}