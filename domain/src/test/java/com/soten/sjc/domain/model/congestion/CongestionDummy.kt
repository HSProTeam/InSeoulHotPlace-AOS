package com.soten.sjc.domain.model.congestion

object CongestionDummy {

    val Alyshia = CongestionInfo(
        areaName = "Alyshia",
        areaCongestLevel = "Malarie",
        areaCongestNumber = 3223,
        category = Category(
            value = "Kandra", isChecked = true
        ),
        isBookmark = false
    )

    val Lissette = CongestionInfo(
        areaName = "Lissette",
        areaCongestLevel = "Jodee",
        areaCongestNumber = 2454,
        category = Category(
            value = "Kandra", isChecked = true
        ),
        isBookmark = false
    )

    val Luca = CongestionInfo(
        areaName = "Luca",
        areaCongestLevel = "Dujuan",
        areaCongestNumber = 7374,
        category = Category(
            value = "Ryon", isChecked = false
        ),
        isBookmark = false
    )

    val Latoshia = CongestionInfo(
        areaName = "Latoshia",
        areaCongestLevel = "Blakely",
        areaCongestNumber = 3526,
        category = Category(
            value = "Ryon", isChecked = false
        ),
        isBookmark = false
    )

    val Casondra = CongestionInfo(
        areaName = "Casondra",
        areaCongestLevel = "Tiona",
        areaCongestNumber = 3830,
        category = Category(
            value = "Obed", isChecked = false
        ),
        isBookmark = true
    )

    val dummy = listOf(
        Alyshia,
        Lissette,
        Luca,
        Latoshia,
        Casondra,
    )
}