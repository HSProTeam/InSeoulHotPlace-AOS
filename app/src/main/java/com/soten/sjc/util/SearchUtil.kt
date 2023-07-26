package com.soten.sjc.util

object SearchUtil {

    private const val koreanUnicodeStart = 44032 // 가
    private const val koreanUnicodeEnd = 55203 // 힣
    private const val koreanUnicodeBased = 588 // 각 자음 마다 가지는 글자 수

    private const val START_INDEX = 0

    private val koreanConsonant = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    fun search(based: String, input: String): Boolean {
        if (input.isEmpty()) return true

        val firstWord = input.first()
        if (isKorean(firstWord) || isConsonant(firstWord)) {
            return matchKoreanAndConsonant(based, input)
        }

        return input.uppercase() in based.uppercase()
    }

    private fun matchKoreanAndConsonant(based: String, input: String): Boolean {
        if (based.length < input.length) return false

        return (START_INDEX..(based.length - input.length)).any { startIndex ->
            input.indices.all { offset ->
                val baseChar = based[startIndex + offset]
                val searchChar = input[offset]

                when {
                    isConsonant(searchChar) && isKorean(baseChar) -> getConsonant(baseChar) == searchChar
                    else -> baseChar == searchChar
                }
            }
        }
    }

    private fun isConsonant(ch: Char): Boolean = ch in koreanConsonant

    private fun isKorean(ch: Char): Boolean = ch.code in koreanUnicodeStart..koreanUnicodeEnd

    private fun getConsonant(ch: Char): Char =
        koreanConsonant[(ch.code - koreanUnicodeStart) / koreanUnicodeBased]

}
