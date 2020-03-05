package com.catsoft.vktin.vkApi.requests

import android.net.Uri
import com.catsoft.vktin.vkApi.models.VKFileUploadInfo
import com.catsoft.vktin.vkApi.models.VKSaveInfo
import com.catsoft.vktin.vkApi.models.VKServerUploadInfo
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKHttpPostCall
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class VKWallPostRequest(
    private val message: String? = null,
    private val photos: List<Uri> = listOf(),
    private val ownerId: Int = 0,
    private val friendsOnly: Boolean = false,
    private val fromGroup: Boolean = false
) : ApiCommand<Int>() {
    override fun onExecute(manager: VKApiManager): Int {
        val callBuilder =
            VKMethodCall.Builder().method("wall.post").args("friends_only", if (friendsOnly) 1 else 0).args("from_group", if (fromGroup) 1 else 0)
                .version(manager.config.version)
        message?.let {
            callBuilder.args("message", it)
        }

        if (ownerId != 0) {
            callBuilder.args("owner_id", ownerId)
        }

        if (photos.isNotEmpty()) {
            val uploadInfo = getServerUploadInfo(manager)
            val attachments = photos.map { uploadPhoto(it, uploadInfo, manager) }

            callBuilder.args("attachments", attachments.joinToString(","))
        }

        return manager.execute(callBuilder.build(), ResponseApiParser())
    }

    private fun getServerUploadInfo(manager: VKApiManager): VKServerUploadInfo {
        val uploadInfoCall = VKMethodCall.Builder().method("photos.getWallUploadServer").version(manager.config.version).build()
        return manager.execute(uploadInfoCall, ServerUploadInfoParser())
    }

    private fun uploadPhoto(uri: Uri, serverUploadInfo: VKServerUploadInfo, manager: VKApiManager): String {
        val fileUploadCall =
            VKHttpPostCall.Builder().url(serverUploadInfo.uploadUrl).args("photo", uri, "image.jpg").timeout(TimeUnit.MINUTES.toMillis(5))
                .retryCount(RETRY_COUNT).build()
        val fileUploadInfo = manager.execute(
            fileUploadCall, null, FileUploadInfoParser()
        )

        val saveCall = VKMethodCall.Builder().method("photos.saveWallPhoto").args("server", fileUploadInfo.server).args("photo", fileUploadInfo.photo)
            .args("hash", fileUploadInfo.hash).version(manager.config.version).build()

        val saveInfo = manager.execute(saveCall, SaveInfoParser())

        return saveInfo.getAttachment()
    }

    companion object {
        const val RETRY_COUNT = 3
    }

    private class ResponseApiParser : VKApiResponseParser<Int> {
        override fun parse(response: String): Int {
            try {
                return JSONObject(response).getJSONObject("response").getInt("post_id")
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class ServerUploadInfoParser : VKApiResponseParser<VKServerUploadInfo> {
        override fun parse(response: String): VKServerUploadInfo {
            try {
                val joResponse = JSONObject(response).getJSONObject("response")
                return VKServerUploadInfo(
                    uploadUrl = joResponse.getString("upload_url"), albumId = joResponse.getInt("album_id"), userId = joResponse.getInt("user_id")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class FileUploadInfoParser : VKApiResponseParser<VKFileUploadInfo> {
        override fun parse(response: String): VKFileUploadInfo {
            try {
                val joResponse = JSONObject(response)
                return VKFileUploadInfo(
                    server = joResponse.getString("server"), photo = joResponse.getString("photo"), hash = joResponse.getString("hash")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class SaveInfoParser : VKApiResponseParser<VKSaveInfo> {
        override fun parse(response: String): VKSaveInfo {
            try {
                val joResponse = JSONObject(response).getJSONArray("response").getJSONObject(0)
                return VKSaveInfo(
                    id = joResponse.getInt("id"), albumId = joResponse.getInt("album_id"), ownerId = joResponse.getInt("owner_id")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}