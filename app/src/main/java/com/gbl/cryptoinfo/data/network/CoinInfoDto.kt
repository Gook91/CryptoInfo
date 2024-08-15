package com.gbl.cryptoinfo.data.network

import com.gbl.cryptoinfo.entity.CoinInfo
import com.gbl.cryptoinfo.entity.Description
import com.gbl.cryptoinfo.entity.Image

data class CoinInfoDto(
    override val id: String,
    override val name: String,
    override val categories: List<String>,
    override val description: DescriptionDto,
    override val image: ImageDto
) : CoinInfo

data class DescriptionDto(
    override val en: String,
    override val ru: String
) : Description

data class ImageDto (
    override val large: String
): Image