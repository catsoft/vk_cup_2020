package com.catsoft.vktinG.ui.cities

import com.catsoft.vktinG.vkApi.model.VKCity

interface IOnSelectCallback {
    fun select(city : VKCity)
}