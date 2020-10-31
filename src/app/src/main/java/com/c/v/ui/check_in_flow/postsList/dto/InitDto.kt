package com.c.v.ui.check_in_flow.postsList.dto

import com.c.v.data.network.vkApi.model.VKGeo
import java.io.Serializable

class InitDto(val userId: Int, val geo: VKGeo) : Serializable