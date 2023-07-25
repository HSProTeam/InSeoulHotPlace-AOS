package com.soten.sjc.util

object KoreanSearchUtil {

    private const val koreanUnicodeStart = 44032 // 가
    private const val koreanUnicodeEnd = 55203 // 힣
    private const val koreanUnicodeBased = 588 // 각 자음 마다 가지는 글자 수

    private val koreanConsonant = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    fun isConsonant(ch: Char): Boolean = ch in koreanConsonant

    fun isKorean(ch: Char): Boolean = ch.code in koreanUnicodeStart..koreanUnicodeEnd

    private fun getConsonant(ch: Char): Char = koreanConsonant[(ch.code - koreanUnicodeStart) / koreanUnicodeBased]

    fun matchKoreanAndConsonant(based: String, search: String): Boolean {
        if (based.length < search.length) return false

        return (0..(based.length - search.length)).any { startIndex ->
            search.indices.all { offset ->
                val baseChar = based[startIndex + offset]
                val searchChar = search[offset]

                when {
                    isConsonant(searchChar) && isKorean(baseChar) -> getConsonant(baseChar) == searchChar
                    else -> baseChar == searchChar
                }
            }
        }
    }
}
