package com.catsoft.vktin.vkApi.model

class VKSaveInfo(val id: Int,
                 val albumId: Int,
                 val ownerId: Int) {
    fun getAttachment() = "photo${ownerId}_$id"
}

