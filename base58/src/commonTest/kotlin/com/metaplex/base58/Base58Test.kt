package com.metaplex.base58

import diglol.encoding.decodeHexToBytes
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Base58Test {

    val TEST_VECTORS = mapOf(
        "" to "",
        "61" to "2g",
        "626262" to "a3gV",
        "636363" to "aPEr",
        "73696d706c792061206c6f6e6720737472696e67" to "2cFupjhnEsSn59qHXstmK2ffpLv2",
        "00eb15231dfceb60925886b67d065299925915aeb172c06647" to "1NS17iag9jJgTHD1VXjvLCEnZuQ3rJDE9L",
        "516b6fcd0f" to "ABnLTmg",
        "bf4f89001e670274dd" to "3SEo3LWLoPntC",
        "572e4794" to "3EFU7m",
        "ecac89cad93923c02321" to "EJDM8drfXA6uyA",
        "10c8511e" to "Rt5zm",
        "00000000000000000000" to "1111111111"
    )

    @Test
    fun encodingToBase58Works() = runBlocking {
        TEST_VECTORS.forEach {
            assertEquals(it.value, it.key.decodeHexToBytes()!!.encodeToBase58String())
        }
    }

    @Test
    fun decodingFromBase58Works() = runBlocking {
        TEST_VECTORS.forEach {
            // I do the decode and re-encode to check versus strings. Checking ByteArrays require deep check
            assertEquals(it.value.decodeBase58().encodeToBase58String(), it.key.decodeHexToBytes()!!.encodeToBase58String())
        }
    }
}