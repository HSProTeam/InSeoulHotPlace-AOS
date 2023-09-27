package com.soten.sjc.domain.model.congestion

import com.soten.sjc.domain.model.congestion.CongestionDummy.Alyshia
import com.soten.sjc.domain.model.congestion.CongestionDummy.dummy
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)

class CongestionFilterTest(
    private val congestionInfo: CongestionInfo,
    private val keyword: String,
    private val category: Category,
    private val result: Boolean
) {

    @Test
    fun 혼자도_정보_카테고리_및_키워드_필터링() {

    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            val congestionInfos = CongestionInfos(dummy)
            val keyword = ""
            // Kandra, Ryon, Obed
            Category("Kandra")
            Category("Ryon")
            Category("Obed")


            /*
              Alyshia,
              Lissette,
              Luca,
              Latoshia,
              Casondra,
             */
            return listOf(
                arrayOf(Alyshia, "ㅎㅇ", true),
                arrayOf("안녕", "ㅇㄴ", true),
                arrayOf("동해물과백두산이", "ㅁㄱ", true),
                arrayOf("최윤호", "ㅇㅅㄱ", false),
            )
        }
    }
}
