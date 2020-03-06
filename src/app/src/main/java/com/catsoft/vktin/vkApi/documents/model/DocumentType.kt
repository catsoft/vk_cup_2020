package com.catsoft.vktin.vkApi.documents.model

enum class DocumentType(var value: Int) {
    TextDocument(1), Zip(2), Gif(3), Image(4), Audio(5), Video(6), Book(7), Other(8);

    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }!!
    }
}