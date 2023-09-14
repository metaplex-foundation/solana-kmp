package foundation.metaplex.solanapublickeys

import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import foundation.metaplex.base58.encodeToBase58String
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PublicKeyTests {

    @Test
    fun testExample() {
        assertTrue(true, "is True")
    }

    @Test
    fun notValidKeys() {
        assertFailsWith<IllegalArgumentException> {
            PublicKey(
                byteArrayOf(
                    3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0
                )
            )
            PublicKey("300000000000000000000000000000000000000000000000000000000000000000000")
            PublicKey("300000000000000000000000000000000000000000000000000000000000000")
        }
    }
    @Test
    fun validKeys() {
        val key = PublicKey(
            byteArrayOf(
                3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0
            )
        )
        assertEquals("CiDwVBFgWV9E5MvXWoLgnEgn2hK7rJikbvfWavzAQz3", key.toString())
        val key1 = PublicKey("CiDwVBFgWV9E5MvXWoLgnEgn2hK7rJikbvfWavzAQz3")
        assertEquals("CiDwVBFgWV9E5MvXWoLgnEgn2hK7rJikbvfWavzAQz3", key1.toBase58())
        val key2 = PublicKey("11111111111111111111111111111111")
        assertEquals("11111111111111111111111111111111", key2.toBase58())
        val byteKey = byteArrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1
        )
        val key3 = PublicKey(byteKey)

        assertEquals(key3.toBase58(), byteKey.encodeToBase58String())
        assertEquals(byteKey.encodeToBase58String(), PublicKey(key3.toBase58()).toByteArray().encodeToBase58String())
    }

    @Test
    fun equals() {
        val key = PublicKey("11111111111111111111111111111111")
        assertTrue(key.equals(key))
        assertFalse(key.equals(PublicKey("11111111111111111111111111111112")))
    }

    @Test
    fun readPubkey() {
        val key = PublicKey("11111111111111111111111111111111")
        val bufferSize = 1 + key.toByteArray().count()
        val bos = PlatformBuffer.allocate(bufferSize)
        bos.writeByte(1)
        bos.writeBytes(key.toByteArray())
        bos.resetForRead()
        val bytes = bos.readByteArray(bufferSize)
        assertEquals(key.toString(), PublicKey.readPubkey(bytes, 1).toString())
    }

    @Test
    fun createProgramAddress() = runBlocking {
        val programId = PublicKey("BPFLoader1111111111111111111111111111111111")
        var programAddress = PublicKey.createProgramAddress(
            listOf(PublicKey("SeedPubey1111111111111111111111111111111111").toByteArray()),
            programId
        )
        assertTrue(programAddress.equals(PublicKey("GUs5qLUfsEHkcMB9T38vjr18ypEhRuNWiePW2LoK4E3K")))
        programAddress = PublicKey.createProgramAddress(
            listOf("".encodeToByteArray(), byteArrayOf(1)),
            programId
        )
        assertTrue(programAddress.equals(PublicKey("3gF2KMe9KiC6FNVBmfg9i267aMPvK37FewCip4eGBFcT")))
        programAddress = PublicKey.createProgramAddress(listOf("☉".encodeToByteArray()), programId)
        assertTrue(programAddress.equals(PublicKey("7ytmC1nT1xY4RfxCV2ZgyA7UakC93do5ZdyhdF3EtPj7")))
        programAddress = PublicKey.createProgramAddress(
            listOf("Talking".encodeToByteArray(), "Squirrels".encodeToByteArray()),
            programId
        )
        assertTrue(programAddress.equals(PublicKey("HwRVBufQ4haG5XSgpspwKtNd3PC9GM9m1196uJW36vds")))
        val programAddress2 =
            PublicKey.createProgramAddress(listOf("Talking".encodeToByteArray()), programId)
        assertFalse(programAddress.equals(programAddress2))
    }

    @Test
    fun findProgramAddress() = runBlocking {
        val programId = PublicKey("BPFLoader1111111111111111111111111111111111")
        val programAddress =
            PublicKey.findProgramAddress(listOf("".encodeToByteArray()), programId)
        assertTrue(
            programAddress.address.equals(
                PublicKey.createProgramAddress(
                    listOf("".encodeToByteArray(), byteArrayOf(programAddress.nonce.toByte())),
                    programId
                )
            )
        )
    }

    @Test
    fun findProgramAddress1() = runBlocking {
        val programId = PublicKey("6Cust2JhvweKLh4CVo1dt21s2PJ86uNGkziudpkNPaCj")
        val programId2 = PublicKey("BPFLoader1111111111111111111111111111111111")
        val programAddress = PublicKey.findProgramAddress(
            listOf(PublicKey("8VBafTNv1F8k5Bg7DTVwhitw3MGAMTmekHsgLuMJxLC8").toByteArray()),
            programId
        )
        assertTrue(programAddress.address.equals(PublicKey("FGnnqkzkXUGKD7wtgJCqTemU3WZ6yYqkYJ8xoQoXVvUG")))
        val programAddress2 = PublicKey
            .findProgramAddress(
                listOf(
                    PublicKey("SeedPubey1111111111111111111111111111111111").toByteArray(),
                    PublicKey("3gF2KMe9KiC6FNVBmfg9i267aMPvK37FewCip4eGBFcT").toByteArray(),
                    PublicKey("HwRVBufQ4haG5XSgpspwKtNd3PC9GM9m1196uJW36vds").toByteArray()
                ),
                programId2
            )
        assertTrue(programAddress2.address.equals(PublicKey("GXLbx3CbJuTTtJDZeS1PGzwJJ5jGYVEqcXum7472kpUp")))
        assertEquals(programAddress2.nonce, 254)
    }
}