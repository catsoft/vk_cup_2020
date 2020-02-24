package com.catsoft.vktinG.ui.cities

import com.catsoft.vktinG.vkApi.model.VKCity

interface IOnSelectCityCallback {
    fun select(city : VKCity)
}