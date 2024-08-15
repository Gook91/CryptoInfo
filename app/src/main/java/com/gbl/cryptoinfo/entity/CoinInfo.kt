package com.gbl.cryptoinfo.entity

interface CoinInfo {
    val id: String
    val name: String
    val categories: List<String>
    val description: Description
    val image: Image
}

interface Description {
    val en: String
    val ru: String
    // Дополнительные языки для описания надо будет добавлять с добавлением языков локализаций
}

interface Image {
    val large: String
}