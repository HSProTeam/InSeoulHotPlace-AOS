package com.soten.sjc.domain

import com.google.common.truth.Truth.assertThat
import com.soten.sjc.domain.SearchUtil.isMatchByKeyword
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SearchUtilTest(
    private val based: String,
    private val input: String,
    private val result: Boolean,
) {

    @Test
    fun 한글_초성으로_검색이된다() {
        // when
        val actual = isMatchByKeyword(based, input)

        // then
        assertThat(actual).isEqualTo(result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() : Collection<Array<Any>> {
            return listOf(
                arrayOf("하이", "ㅎㅇ", true),
                arrayOf("안녕", "ㅇㄴ", true),
                arrayOf("동해물과백두산이", "ㅁㄱ", true),
                arrayOf("최윤호", "ㅇㅅㄱ", false),
            )
        }
    }
}