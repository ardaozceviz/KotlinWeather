package com.choxxy.rainmaker.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("LocationResponse")
    val locationResponse: List<LocationResponseItem?>? = null
)

data class LocationResponseItem(
    @field:SerializedName("local_names")
    val localNames: LocalNames? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)

data class LocalNames(

    @field:SerializedName("de")
    val de: String? = null,

    @field:SerializedName("hi")
    val hi: String? = null,

    @field:SerializedName("no")
    val no: String? = null,

    @field:SerializedName("fi")
    val fi: String? = null,

    @field:SerializedName("ru")
    val ru: String? = null,

    @field:SerializedName("pt")
    val pt: String? = null,

    @field:SerializedName("bg")
    val bg: String? = null,

    @field:SerializedName("lt")
    val lt: String? = null,

    @field:SerializedName("hr")
    val hr: String? = null,

    @field:SerializedName("fr")
    val fr: String? = null,

    @field:SerializedName("hu")
    val hu: String? = null,

    @field:SerializedName("sk")
    val sk: String? = null,

    @field:SerializedName("sl")
    val sl: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("ascii")
    val ascii: String? = null,

    @field:SerializedName("ca")
    val ca: String? = null,

    @field:SerializedName("mk")
    val mk: String? = null,

    @field:SerializedName("sr")
    val sr: String? = null,

    @field:SerializedName("feature_name")
    val featureName: String? = null,

    @field:SerializedName("af")
    val af: String? = null,

    @field:SerializedName("gl")
    val gl: String? = null,

    @field:SerializedName("el")
    val el: String? = null,

    @field:SerializedName("en")
    val en: String? = null,

    @field:SerializedName("it")
    val it: String? = null,

    @field:SerializedName("eu")
    val eu: String? = null,

    @field:SerializedName("ar")
    val ar: String? = null,

    @field:SerializedName("vi")
    val vi: String? = null,

    @field:SerializedName("th")
    val th: String? = null,

    @field:SerializedName("la")
    val la: String? = null,

    @field:SerializedName("ja")
    val ja: String? = null,

    @field:SerializedName("az")
    val az: String? = null,

    @field:SerializedName("fa")
    val fa: String? = null,

    @field:SerializedName("zu")
    val zu: String? = null,

    @field:SerializedName("pl")
    val pl: String? = null,

    @field:SerializedName("da")
    val da: String? = null,

    @field:SerializedName("he")
    val he: String? = null,

    @field:SerializedName("ro")
    val ro: String? = null,

    @field:SerializedName("nl")
    val nl: String? = null,

    @field:SerializedName("tr")
    val tr: String? = null
)
