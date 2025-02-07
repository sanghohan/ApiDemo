package com.example.apidemo

import org.junit.jupiter.api.Test


class common {

    @Test
    fun `HashMap 초기 용량 지정`() {
        val expectedSize = 100
        val initialCapacity = kotlin.math.ceil(expectedSize / 0.75).toInt()

        print("initialCapacity: $initialCapacity")

        // 초기 용량(capacity) 지정
        val map = HashMap<String, Int>(initialCapacity)

        // 값 추가
        for (i in 1..expectedSize) {
            map["Key$i"] = i
        }

        // HashMap 크기 확인
        println("현재 HashMap 크기(size): ${map.size}")  // 100
    }

    @Test
    fun testJavaString() {
        // Java String은 immutable(불변)이기 때문에 새로운 값을 할당하면 새로운 객체가 생성된다.

        var str = "Hello, Java String!"
        val str2 = str;

        str = "Hello, Kotlin String!"

        println(str)
        println(str2)
    }
}