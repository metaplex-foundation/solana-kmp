package foundation.metaplex.solanapublickeys

import kotlin.jvm.JvmOverloads

// https://github.com/InstantWebP2P/tweetnacl-java/blob/master/src/main/java/com/iwebpp/crypto/TweetNaclFast.java
// Copyright (c) 2014 Tom Zhou<appnet.link@gmail.com>
/*
 * @description
 *   TweetNacl.c Java porting
 * */
internal object TweetNaclFast {
    private val _0 = ByteArray(16)
    private val _9 = ByteArray(32)

    init {
        ///for (int i = 0; i < _0.length; i ++) _0[i] = 0;

        ///for (int i = 0; i < _9.length; i ++) _9[i] = 0;
        _9[0] = 9
    }

    private val gf0 = LongArray(16)
    private val gf1 = LongArray(16)
    private val _121665 = LongArray(16)

    init {
        ///for (int i = 0; i < gf0.length; i ++) gf0[i] = 0;

        ///for (int i = 0; i < gf1.length; i ++)  gf1[i] = 0;
        gf1[0] = 1

        ///for (int i = 0; i < _121665.length; i ++) _121665[i] = 0;
        _121665[0] = 0xDB41
        _121665[1] = 1
    }

    private val D = longArrayOf(
        0x78a3, 0x1359, 0x4dca, 0x75eb,
        0xd8ab, 0x4141, 0x0a4d, 0x0070,
        0xe898, 0x7779, 0x4079, 0x8cc7,
        0xfe73, 0x2b6f, 0x6cee, 0x5203
    )
    private val D2 = longArrayOf(
        0xf159, 0x26b2, 0x9b94, 0xebd6,
        0xb156, 0x8283, 0x149a, 0x00e0,
        0xd130, 0xeef3, 0x80f2, 0x198e,
        0xfce7, 0x56df, 0xd9dc, 0x2406
    )
    private val X = longArrayOf(
        0xd51a, 0x8f25, 0x2d60, 0xc956,
        0xa7b2, 0x9525, 0xc760, 0x692c,
        0xdc5c, 0xfdd6, 0xe231, 0xc0a4,
        0x53fe, 0xcd6e, 0x36d3, 0x2169
    )
    private val Y = longArrayOf(
        0x6658, 0x6666, 0x6666, 0x6666,
        0x6666, 0x6666, 0x6666, 0x6666,
        0x6666, 0x6666, 0x6666, 0x6666,
        0x6666, 0x6666, 0x6666, 0x6666
    )
    private val I = longArrayOf(
        0xa0b0, 0x4a0e, 0x1b27, 0xc4ee,
        0xe478, 0xad2f, 0x1806, 0x2f43,
        0xd7a7, 0x3dfb, 0x0099, 0x2b4d,
        0xdf0b, 0x4fc1, 0x2480, 0x2b83
    )

    private fun ts64(x: ByteArray, xoff: Int, u: Long) {
        ///int i;
        ///for (i = 7;i >= 0;--i) { x[i+xoff] = (byte)(u&0xff); u >>>= 8; }
        var u = u
        x[7 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[6 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[5 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[4 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[3 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[2 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[1 + xoff] = (u and 0xffL).toByte()
        u = u ushr 8
        x[0 + xoff] = (u and 0xffL).toByte() ///u >>>= 8;
    }

    private fun vn(
        x: ByteArray, xoff: Int,
        y: ByteArray, yoff: Int,
        n: Int
    ): Int {
        var i: Int
        var d = 0
        i = 0
        while (i < n) {
            d = d or (x[i + xoff].toInt() xor y[i + yoff].toInt() and 0xff)
            i++
        }
        return (1 and (d - 1 ushr 8)) - 1
    }

    private fun crypto_verify_16(
        x: ByteArray, xoff: Int,
        y: ByteArray, yoff: Int
    ): Int {
        return vn(x, xoff, y, yoff, 16)
    }

    fun crypto_verify_16(x: ByteArray, y: ByteArray): Int {
        return crypto_verify_16(x, 0, y, 0)
    }

    private fun crypto_verify_32(
        x: ByteArray, xoff: Int,
        y: ByteArray, yoff: Int
    ): Int {
        return vn(x, xoff, y, yoff, 32)
    }

    fun crypto_verify_32(x: ByteArray, y: ByteArray): Int {
        return crypto_verify_32(x, 0, y, 0)
    }

    private fun core_salsa20(o: ByteArray, p: ByteArray, k: ByteArray, c: ByteArray) {
        val j0 =
            c[0].toInt() and 0xff or (c[1].toInt() and 0xff shl 8) or (c[2].toInt() and 0xff shl 16) or (c[3].toInt() and 0xff shl 24)
        val j1 =
            k[0].toInt() and 0xff or (k[1].toInt() and 0xff shl 8) or (k[2].toInt() and 0xff shl 16) or (k[3].toInt() and 0xff shl 24)
        val j2 =
            k[4].toInt() and 0xff or (k[5].toInt() and 0xff shl 8) or (k[6].toInt() and 0xff shl 16) or (k[7].toInt() and 0xff shl 24)
        val j3 =
            k[8].toInt() and 0xff or (k[9].toInt() and 0xff shl 8) or (k[10].toInt() and 0xff shl 16) or (k[11].toInt() and 0xff shl 24)
        val j4 =
            k[12].toInt() and 0xff or (k[13].toInt() and 0xff shl 8) or (k[14].toInt() and 0xff shl 16) or (k[15].toInt() and 0xff shl 24)
        val j5 =
            c[4].toInt() and 0xff or (c[5].toInt() and 0xff shl 8) or (c[6].toInt() and 0xff shl 16) or (c[7].toInt() and 0xff shl 24)
        val j6 =
            p[0].toInt() and 0xff or (p[1].toInt() and 0xff shl 8) or (p[2].toInt() and 0xff shl 16) or (p[3].toInt() and 0xff shl 24)
        val j7 =
            p[4].toInt() and 0xff or (p[5].toInt() and 0xff shl 8) or (p[6].toInt() and 0xff shl 16) or (p[7].toInt() and 0xff shl 24)
        val j8 =
            p[8].toInt() and 0xff or (p[9].toInt() and 0xff shl 8) or (p[10].toInt() and 0xff shl 16) or (p[11].toInt() and 0xff shl 24)
        val j9 =
            p[12].toInt() and 0xff or (p[13].toInt() and 0xff shl 8) or (p[14].toInt() and 0xff shl 16) or (p[15].toInt() and 0xff shl 24)
        val j10 =
            c[8].toInt() and 0xff or (c[9].toInt() and 0xff shl 8) or (c[10].toInt() and 0xff shl 16) or (c[11].toInt() and 0xff shl 24)
        val j11 =
            k[16].toInt() and 0xff or (k[17].toInt() and 0xff shl 8) or (k[18].toInt() and 0xff shl 16) or (k[19].toInt() and 0xff shl 24)
        val j12 =
            k[20].toInt() and 0xff or (k[21].toInt() and 0xff shl 8) or (k[22].toInt() and 0xff shl 16) or (k[23].toInt() and 0xff shl 24)
        val j13 =
            k[24].toInt() and 0xff or (k[25].toInt() and 0xff shl 8) or (k[26].toInt() and 0xff shl 16) or (k[27].toInt() and 0xff shl 24)
        val j14 =
            k[28].toInt() and 0xff or (k[29].toInt() and 0xff shl 8) or (k[30].toInt() and 0xff shl 16) or (k[31].toInt() and 0xff shl 24)
        val j15 =
            c[12].toInt() and 0xff or (c[13].toInt() and 0xff shl 8) or (c[14].toInt() and 0xff shl 16) or (c[15].toInt() and 0xff shl 24)
        var x0 = j0
        var x1 = j1
        var x2 = j2
        var x3 = j3
        var x4 = j4
        var x5 = j5
        var x6 = j6
        var x7 = j7
        var x8 = j8
        var x9 = j9
        var x10 = j10
        var x11 = j11
        var x12 = j12
        var x13 = j13
        var x14 = j14
        var x15 = j15
        var u: Int
        var i = 0
        while (i < 20) {
            u = x0 + x12 or 0
            x4 = x4 xor (u shl 7 or (u ushr 32)) - 7
            u = x4 + x0 or 0
            x8 = x8 xor (u shl 9 or (u ushr 32)) - 9
            u = x8 + x4 or 0
            x12 = x12 xor (u shl 13 or (u ushr 32)) - 13
            u = x12 + x8 or 0
            x0 = x0 xor (u shl 18 or (u ushr 32)) - 18
            u = x5 + x1 or 0
            x9 = x9 xor (u shl 7 or (u ushr 32)) - 7
            u = x9 + x5 or 0
            x13 = x13 xor (u shl 9 or (u ushr 32)) - 9
            u = x13 + x9 or 0
            x1 = x1 xor (u shl 13 or (u ushr 32)) - 13
            u = x1 + x13 or 0
            x5 = x5 xor (u shl 18 or (u ushr 32)) - 18
            u = x10 + x6 or 0
            x14 = x14 xor (u shl 7 or (u ushr 32)) - 7
            u = x14 + x10 or 0
            x2 = x2 xor (u shl 9 or (u ushr 32)) - 9
            u = x2 + x14 or 0
            x6 = x6 xor (u shl 13 or (u ushr 32)) - 13
            u = x6 + x2 or 0
            x10 = x10 xor (u shl 18 or (u ushr 32)) - 18
            u = x15 + x11 or 0
            x3 = x3 xor (u shl 7 or (u ushr 32)) - 7
            u = x3 + x15 or 0
            x7 = x7 xor (u shl 9 or (u ushr 32)) - 9
            u = x7 + x3 or 0
            x11 = x11 xor (u shl 13 or (u ushr 32)) - 13
            u = x11 + x7 or 0
            x15 = x15 xor (u shl 18 or (u ushr 32)) - 18
            u = x0 + x3 or 0
            x1 = x1 xor (u shl 7 or (u ushr 32)) - 7
            u = x1 + x0 or 0
            x2 = x2 xor (u shl 9 or (u ushr 32)) - 9
            u = x2 + x1 or 0
            x3 = x3 xor (u shl 13 or (u ushr 32)) - 13
            u = x3 + x2 or 0
            x0 = x0 xor (u shl 18 or (u ushr 32)) - 18
            u = x5 + x4 or 0
            x6 = x6 xor (u shl 7 or (u ushr 32)) - 7
            u = x6 + x5 or 0
            x7 = x7 xor (u shl 9 or (u ushr 32)) - 9
            u = x7 + x6 or 0
            x4 = x4 xor (u shl 13 or (u ushr 32)) - 13
            u = x4 + x7 or 0
            x5 = x5 xor (u shl 18 or (u ushr 32)) - 18
            u = x10 + x9 or 0
            x11 = x11 xor (u shl 7 or (u ushr 32)) - 7
            u = x11 + x10 or 0
            x8 = x8 xor (u shl 9 or (u ushr 32)) - 9
            u = x8 + x11 or 0
            x9 = x9 xor (u shl 13 or (u ushr 32)) - 13
            u = x9 + x8 or 0
            x10 = x10 xor (u shl 18 or (u ushr 32)) - 18
            u = x15 + x14 or 0
            x12 = x12 xor (u shl 7 or (u ushr 32)) - 7
            u = x12 + x15 or 0
            x13 = x13 xor (u shl 9 or (u ushr 32)) - 9
            u = x13 + x12 or 0
            x14 = x14 xor (u shl 13 or (u ushr 32)) - 13
            u = x14 + x13 or 0
            x15 = x15 xor (u shl 18 or (u ushr 32)) - 18
            i += 2
        }
        x0 = x0 + j0 or 0
        x1 = x1 + j1 or 0
        x2 = x2 + j2 or 0
        x3 = x3 + j3 or 0
        x4 = x4 + j4 or 0
        x5 = x5 + j5 or 0
        x6 = x6 + j6 or 0
        x7 = x7 + j7 or 0
        x8 = x8 + j8 or 0
        x9 = x9 + j9 or 0
        x10 = x10 + j10 or 0
        x11 = x11 + j11 or 0
        x12 = x12 + j12 or 0
        x13 = x13 + j13 or 0
        x14 = x14 + j14 or 0
        x15 = x15 + j15 or 0
        o[0] = (x0 ushr 0 and 0xff).toByte()
        o[1] = (x0 ushr 8 and 0xff).toByte()
        o[2] = (x0 ushr 16 and 0xff).toByte()
        o[3] = (x0 ushr 24 and 0xff).toByte()
        o[4] = (x1 ushr 0 and 0xff).toByte()
        o[5] = (x1 ushr 8 and 0xff).toByte()
        o[6] = (x1 ushr 16 and 0xff).toByte()
        o[7] = (x1 ushr 24 and 0xff).toByte()
        o[8] = (x2 ushr 0 and 0xff).toByte()
        o[9] = (x2 ushr 8 and 0xff).toByte()
        o[10] = (x2 ushr 16 and 0xff).toByte()
        o[11] = (x2 ushr 24 and 0xff).toByte()
        o[12] = (x3 ushr 0 and 0xff).toByte()
        o[13] = (x3 ushr 8 and 0xff).toByte()
        o[14] = (x3 ushr 16 and 0xff).toByte()
        o[15] = (x3 ushr 24 and 0xff).toByte()
        o[16] = (x4 ushr 0 and 0xff).toByte()
        o[17] = (x4 ushr 8 and 0xff).toByte()
        o[18] = (x4 ushr 16 and 0xff).toByte()
        o[19] = (x4 ushr 24 and 0xff).toByte()
        o[20] = (x5 ushr 0 and 0xff).toByte()
        o[21] = (x5 ushr 8 and 0xff).toByte()
        o[22] = (x5 ushr 16 and 0xff).toByte()
        o[23] = (x5 ushr 24 and 0xff).toByte()
        o[24] = (x6 ushr 0 and 0xff).toByte()
        o[25] = (x6 ushr 8 and 0xff).toByte()
        o[26] = (x6 ushr 16 and 0xff).toByte()
        o[27] = (x6 ushr 24 and 0xff).toByte()
        o[28] = (x7 ushr 0 and 0xff).toByte()
        o[29] = (x7 ushr 8 and 0xff).toByte()
        o[30] = (x7 ushr 16 and 0xff).toByte()
        o[31] = (x7 ushr 24 and 0xff).toByte()
        o[32] = (x8 ushr 0 and 0xff).toByte()
        o[33] = (x8 ushr 8 and 0xff).toByte()
        o[34] = (x8 ushr 16 and 0xff).toByte()
        o[35] = (x8 ushr 24 and 0xff).toByte()
        o[36] = (x9 ushr 0 and 0xff).toByte()
        o[37] = (x9 ushr 8 and 0xff).toByte()
        o[38] = (x9 ushr 16 and 0xff).toByte()
        o[39] = (x9 ushr 24 and 0xff).toByte()
        o[40] = (x10 ushr 0 and 0xff).toByte()
        o[41] = (x10 ushr 8 and 0xff).toByte()
        o[42] = (x10 ushr 16 and 0xff).toByte()
        o[43] = (x10 ushr 24 and 0xff).toByte()
        o[44] = (x11 ushr 0 and 0xff).toByte()
        o[45] = (x11 ushr 8 and 0xff).toByte()
        o[46] = (x11 ushr 16 and 0xff).toByte()
        o[47] = (x11 ushr 24 and 0xff).toByte()
        o[48] = (x12 ushr 0 and 0xff).toByte()
        o[49] = (x12 ushr 8 and 0xff).toByte()
        o[50] = (x12 ushr 16 and 0xff).toByte()
        o[51] = (x12 ushr 24 and 0xff).toByte()
        o[52] = (x13 ushr 0 and 0xff).toByte()
        o[53] = (x13 ushr 8 and 0xff).toByte()
        o[54] = (x13 ushr 16 and 0xff).toByte()
        o[55] = (x13 ushr 24 and 0xff).toByte()
        o[56] = (x14 ushr 0 and 0xff).toByte()
        o[57] = (x14 ushr 8 and 0xff).toByte()
        o[58] = (x14 ushr 16 and 0xff).toByte()
        o[59] = (x14 ushr 24 and 0xff).toByte()
        o[60] = (x15 ushr 0 and 0xff).toByte()
        o[61] = (x15 ushr 8 and 0xff).toByte()
        o[62] = (x15 ushr 16 and 0xff).toByte()
        o[63] = (x15 ushr 24 and 0xff).toByte()

        /*String dbgt = "";
		for (int dbg = 0; dbg < o.length; dbg ++) dbgt += " "+o[dbg];
		Log.d(TAG, "core_salsa20 -> "+dbgt);
*/
    }

    private fun core_hsalsa20(o: ByteArray, p: ByteArray, k: ByteArray, c: ByteArray) {
        val j0 =
            c[0].toInt() and 0xff or (c[1].toInt() and 0xff shl 8) or (c[2].toInt() and 0xff shl 16) or (c[3].toInt() and 0xff shl 24)
        val j1 =
            k[0].toInt() and 0xff or (k[1].toInt() and 0xff shl 8) or (k[2].toInt() and 0xff shl 16) or (k[3].toInt() and 0xff shl 24)
        val j2 =
            k[4].toInt() and 0xff or (k[5].toInt() and 0xff shl 8) or (k[6].toInt() and 0xff shl 16) or (k[7].toInt() and 0xff shl 24)
        val j3 =
            k[8].toInt() and 0xff or (k[9].toInt() and 0xff shl 8) or (k[10].toInt() and 0xff shl 16) or (k[11].toInt() and 0xff shl 24)
        val j4 =
            k[12].toInt() and 0xff or (k[13].toInt() and 0xff shl 8) or (k[14].toInt() and 0xff shl 16) or (k[15].toInt() and 0xff shl 24)
        val j5 =
            c[4].toInt() and 0xff or (c[5].toInt() and 0xff shl 8) or (c[6].toInt() and 0xff shl 16) or (c[7].toInt() and 0xff shl 24)
        val j6 =
            p[0].toInt() and 0xff or (p[1].toInt() and 0xff shl 8) or (p[2].toInt() and 0xff shl 16) or (p[3].toInt() and 0xff shl 24)
        val j7 =
            p[4].toInt() and 0xff or (p[5].toInt() and 0xff shl 8) or (p[6].toInt() and 0xff shl 16) or (p[7].toInt() and 0xff shl 24)
        val j8 =
            p[8].toInt() and 0xff or (p[9].toInt() and 0xff shl 8) or (p[10].toInt() and 0xff shl 16) or (p[11].toInt() and 0xff shl 24)
        val j9 =
            p[12].toInt() and 0xff or (p[13].toInt() and 0xff shl 8) or (p[14].toInt() and 0xff shl 16) or (p[15].toInt() and 0xff shl 24)
        val j10 =
            c[8].toInt() and 0xff or (c[9].toInt() and 0xff shl 8) or (c[10].toInt() and 0xff shl 16) or (c[11].toInt() and 0xff shl 24)
        val j11 =
            k[16].toInt() and 0xff or (k[17].toInt() and 0xff shl 8) or (k[18].toInt() and 0xff shl 16) or (k[19].toInt() and 0xff shl 24)
        val j12 =
            k[20].toInt() and 0xff or (k[21].toInt() and 0xff shl 8) or (k[22].toInt() and 0xff shl 16) or (k[23].toInt() and 0xff shl 24)
        val j13 =
            k[24].toInt() and 0xff or (k[25].toInt() and 0xff shl 8) or (k[26].toInt() and 0xff shl 16) or (k[27].toInt() and 0xff shl 24)
        val j14 =
            k[28].toInt() and 0xff or (k[29].toInt() and 0xff shl 8) or (k[30].toInt() and 0xff shl 16) or (k[31].toInt() and 0xff shl 24)
        val j15 =
            c[12].toInt() and 0xff or (c[13].toInt() and 0xff shl 8) or (c[14].toInt() and 0xff shl 16) or (c[15].toInt() and 0xff shl 24)
        var x0 = j0
        var x1 = j1
        var x2 = j2
        var x3 = j3
        var x4 = j4
        var x5 = j5
        var x6 = j6
        var x7 = j7
        var x8 = j8
        var x9 = j9
        var x10 = j10
        var x11 = j11
        var x12 = j12
        var x13 = j13
        var x14 = j14
        var x15 = j15
        var u: Int
        var i = 0
        while (i < 20) {
            u = x0 + x12 or 0
            x4 = x4 xor (u shl 7 or (u ushr 32)) - 7
            u = x4 + x0 or 0
            x8 = x8 xor (u shl 9 or (u ushr 32)) - 9
            u = x8 + x4 or 0
            x12 = x12 xor (u shl 13 or (u ushr 32)) - 13
            u = x12 + x8 or 0
            x0 = x0 xor (u shl 18 or (u ushr 32)) - 18
            u = x5 + x1 or 0
            x9 = x9 xor (u shl 7 or (u ushr 32)) - 7
            u = x9 + x5 or 0
            x13 = x13 xor (u shl 9 or (u ushr 32)) - 9
            u = x13 + x9 or 0
            x1 = x1 xor (u shl 13 or (u ushr 32)) - 13
            u = x1 + x13 or 0
            x5 = x5 xor (u shl 18 or (u ushr 32)) - 18
            u = x10 + x6 or 0
            x14 = x14 xor (u shl 7 or (u ushr 32)) - 7
            u = x14 + x10 or 0
            x2 = x2 xor (u shl 9 or (u ushr 32)) - 9
            u = x2 + x14 or 0
            x6 = x6 xor (u shl 13 or (u ushr 32)) - 13
            u = x6 + x2 or 0
            x10 = x10 xor (u shl 18 or (u ushr 32)) - 18
            u = x15 + x11 or 0
            x3 = x3 xor (u shl 7 or (u ushr 32)) - 7
            u = x3 + x15 or 0
            x7 = x7 xor (u shl 9 or (u ushr 32)) - 9
            u = x7 + x3 or 0
            x11 = x11 xor (u shl 13 or (u ushr 32)) - 13
            u = x11 + x7 or 0
            x15 = x15 xor (u shl 18 or (u ushr 32)) - 18
            u = x0 + x3 or 0
            x1 = x1 xor (u shl 7 or (u ushr 32)) - 7
            u = x1 + x0 or 0
            x2 = x2 xor (u shl 9 or (u ushr 32)) - 9
            u = x2 + x1 or 0
            x3 = x3 xor (u shl 13 or (u ushr 32)) - 13
            u = x3 + x2 or 0
            x0 = x0 xor (u shl 18 or (u ushr 32)) - 18
            u = x5 + x4 or 0
            x6 = x6 xor (u shl 7 or (u ushr 32)) - 7
            u = x6 + x5 or 0
            x7 = x7 xor (u shl 9 or (u ushr 32)) - 9
            u = x7 + x6 or 0
            x4 = x4 xor (u shl 13 or (u ushr 32)) - 13
            u = x4 + x7 or 0
            x5 = x5 xor (u shl 18 or (u ushr 32)) - 18
            u = x10 + x9 or 0
            x11 = x11 xor (u shl 7 or (u ushr 32)) - 7
            u = x11 + x10 or 0
            x8 = x8 xor (u shl 9 or (u ushr 32)) - 9
            u = x8 + x11 or 0
            x9 = x9 xor (u shl 13 or (u ushr 32)) - 13
            u = x9 + x8 or 0
            x10 = x10 xor (u shl 18 or (u ushr 32)) - 18
            u = x15 + x14 or 0
            x12 = x12 xor (u shl 7 or (u ushr 32)) - 7
            u = x12 + x15 or 0
            x13 = x13 xor (u shl 9 or (u ushr 32)) - 9
            u = x13 + x12 or 0
            x14 = x14 xor (u shl 13 or (u ushr 32)) - 13
            u = x14 + x13 or 0
            x15 = x15 xor (u shl 18 or (u ushr 32)) - 18
            i += 2
        }
        o[0] = (x0 ushr 0 and 0xff).toByte()
        o[1] = (x0 ushr 8 and 0xff).toByte()
        o[2] = (x0 ushr 16 and 0xff).toByte()
        o[3] = (x0 ushr 24 and 0xff).toByte()
        o[4] = (x5 ushr 0 and 0xff).toByte()
        o[5] = (x5 ushr 8 and 0xff).toByte()
        o[6] = (x5 ushr 16 and 0xff).toByte()
        o[7] = (x5 ushr 24 and 0xff).toByte()
        o[8] = (x10 ushr 0 and 0xff).toByte()
        o[9] = (x10 ushr 8 and 0xff).toByte()
        o[10] = (x10 ushr 16 and 0xff).toByte()
        o[11] = (x10 ushr 24 and 0xff).toByte()
        o[12] = (x15 ushr 0 and 0xff).toByte()
        o[13] = (x15 ushr 8 and 0xff).toByte()
        o[14] = (x15 ushr 16 and 0xff).toByte()
        o[15] = (x15 ushr 24 and 0xff).toByte()
        o[16] = (x6 ushr 0 and 0xff).toByte()
        o[17] = (x6 ushr 8 and 0xff).toByte()
        o[18] = (x6 ushr 16 and 0xff).toByte()
        o[19] = (x6 ushr 24 and 0xff).toByte()
        o[20] = (x7 ushr 0 and 0xff).toByte()
        o[21] = (x7 ushr 8 and 0xff).toByte()
        o[22] = (x7 ushr 16 and 0xff).toByte()
        o[23] = (x7 ushr 24 and 0xff).toByte()
        o[24] = (x8 ushr 0 and 0xff).toByte()
        o[25] = (x8 ushr 8 and 0xff).toByte()
        o[26] = (x8 ushr 16 and 0xff).toByte()
        o[27] = (x8 ushr 24 and 0xff).toByte()
        o[28] = (x9 ushr 0 and 0xff).toByte()
        o[29] = (x9 ushr 8 and 0xff).toByte()
        o[30] = (x9 ushr 16 and 0xff).toByte()
        o[31] = (x9 ushr 24 and 0xff).toByte()


        /*String dbgt = "";
		for (int dbg = 0; dbg < o.length; dbg ++) dbgt += " "+o[dbg];
		Log.d(TAG, "core_hsalsa20 -> "+dbgt);
*/
    }

    fun crypto_core_salsa20(out: ByteArray, `in`: ByteArray, k: ByteArray, c: ByteArray): Int {
        ///core(out,in,k,c,0);
        core_salsa20(out, `in`, k, c)

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < out.length; dbg ++) dbgt += " "+out[dbg];
        ///L/og.d(TAG, "crypto_core_salsa20 -> "+dbgt);
        return 0
    }

    fun crypto_core_hsalsa20(out: ByteArray, `in`: ByteArray, k: ByteArray, c: ByteArray): Int {
        ///core(out,in,k,c,1);
        core_hsalsa20(out, `in`, k, c)

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < out.length; dbg ++) dbgt += " "+out[dbg];
        ///L/og.d(TAG, "crypto_core_hsalsa20 -> "+dbgt);
        return 0
    }

    // "expand 32-byte k"
    private val sigma =
        byteArrayOf(101, 120, 112, 97, 110, 100, 32, 51, 50, 45, 98, 121, 116, 101, 32, 107)

    /*static {
		try {
			sigma = "expand 32-byte k".getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}*/
    private fun crypto_stream_salsa20_xor(
        c: ByteArray,
        cpos: Int,
        m: ByteArray,
        mpos: Int,
        b: Long,
        n: ByteArray,
        k: ByteArray
    ): Int {
        var cpos = cpos
        var mpos = mpos
        var b = b
        val z = ByteArray(16)
        val x = ByteArray(64)
        var u: Int
        var i: Int
        i = 0
        while (i < 16) {
            z[i] = 0
            i++
        }
        i = 0
        while (i < 8) {
            z[i] = n[i]
            i++
        }
        while (b >= 64) {
            crypto_core_salsa20(x, z, k, sigma)
            i = 0
            while (i < 64) {
                c[cpos + i] = (m[mpos + i].toInt() xor x[i].toInt() and 0xff).toByte()
                i++
            }
            u = 1
            i = 8
            while (i < 16) {
                u = u + (z[i].toInt() and 0xff) or 0
                z[i] = (u and 0xff).toByte()
                u = u ushr 8
                i++
            }
            b -= 64
            cpos += 64
            mpos += 64
        }
        if (b > 0) {
            crypto_core_salsa20(x, z, k, sigma)
            i = 0
            while (i < b) {
                c[cpos + i] = (m[mpos + i].toInt() xor x[i].toInt() and 0xff).toByte()
                i++
            }
        }

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < c.length-cpos; dbg ++) dbgt += " "+c[dbg +cpos];
        ///Log.d(TAG, "crypto_stream_salsa20_xor, c -> "+dbgt);
        return 0
    }

    fun crypto_stream_salsa20(c: ByteArray, cpos: Int, b: Long, n: ByteArray, k: ByteArray): Int {
        var cpos = cpos
        var b = b
        val z = ByteArray(16)
        val x = ByteArray(64)
        var u: Int
        var i: Int
        i = 0
        while (i < 16) {
            z[i] = 0
            i++
        }
        i = 0
        while (i < 8) {
            z[i] = n[i]
            i++
        }
        while (b >= 64) {
            crypto_core_salsa20(x, z, k, sigma)
            i = 0
            while (i < 64) {
                c[cpos + i] = x[i]
                i++
            }
            u = 1
            i = 8
            while (i < 16) {
                u = u + (z[i].toInt() and 0xff) or 0
                z[i] = (u and 0xff).toByte()
                u = u ushr 8
                i++
            }
            b -= 64
            cpos += 64
        }
        if (b > 0) {
            crypto_core_salsa20(x, z, k, sigma)
            i = 0
            while (i < b) {
                c[cpos + i] = x[i]
                i++
            }
        }

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < c.length-cpos; dbg ++) dbgt += " "+c[dbg +cpos];
        ///Log.d(TAG, "crypto_stream_salsa20, c -> "+dbgt);
        return 0
    }

    fun crypto_stream(c: ByteArray, cpos: Int, d: Long, n: ByteArray, k: ByteArray): Int {
        val s = ByteArray(32)
        crypto_core_hsalsa20(s, n, k, sigma)
        val sn = ByteArray(8)
        for (i in 0..7) sn[i] = n[i + 16]
        return crypto_stream_salsa20(c, cpos, d, sn, s)
    }

    fun crypto_stream_xor(
        c: ByteArray,
        cpos: Int,
        m: ByteArray,
        mpos: Int,
        d: Long,
        n: ByteArray,
        k: ByteArray
    ): Int {
        val s = ByteArray(32)

        /*String dbgt = "";
		for (int dbg = 0; dbg < n.length; dbg ++) dbgt += " "+n[dbg];
		Log.d(TAG, "crypto_stream_xor, nonce -> "+dbgt);

		dbgt = "";
		for (int dbg = 0; dbg < k.length; dbg ++) dbgt += " "+k[dbg];
		Log.d(TAG, "crypto_stream_xor, shk -> "+dbgt);
		*/crypto_core_hsalsa20(s, n, k, sigma)
        val sn = ByteArray(8)
        for (i in 0..7) sn[i] = n[i + 16]
        return crypto_stream_salsa20_xor(c, cpos, m, mpos, d, sn, s)
    }

    private fun crypto_onetimeauth(
        out: ByteArray, outpos: Int,
        m: ByteArray?, mpos: Int,
        n: Int,
        k: ByteArray
    ): Int {
        val s = poly1305(k)
        s.update(m, mpos, n)
        s.finish(out, outpos)

        /*String dbgt = "";
		for (int dbg = 0; dbg < out.length-outpos; dbg ++) dbgt += " "+out[dbg+outpos];
		Log.d(TAG, "crypto_onetimeauth -> "+dbgt);
		*/return 0
    }

    fun crypto_onetimeauth(out: ByteArray, m: ByteArray?,   /*long*/n: Int, k: ByteArray): Int {
        return crypto_onetimeauth(out, 0, m, 0, n, k)
    }

    private fun crypto_onetimeauth_verify(
        h: ByteArray, hoff: Int,
        m: ByteArray?, moff: Int,
        /*long*/n: Int,
        k: ByteArray
    ): Int {
        val x = ByteArray(16)
        crypto_onetimeauth(x, 0, m, moff, n, k)
        return crypto_verify_16(h, hoff, x, 0)
    }

    fun crypto_onetimeauth_verify(
        h: ByteArray,
        m: ByteArray?,   /*long*/
        n: Int,
        k: ByteArray
    ): Int {
        return crypto_onetimeauth_verify(h, 0, m, 0, n, k)
    }

    fun crypto_onetimeauth_verify(h: ByteArray, m: ByteArray?, k: ByteArray): Int {
        return crypto_onetimeauth_verify(h, m, m?.size ?: 0, k)
    }

    fun crypto_secretbox(
        c: ByteArray,
        m: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        k: ByteArray
    ): Int {
        var i: Int
        if (d < 32) return -1
        crypto_stream_xor(c, 0, m, 0, d.toLong(), n, k)
        crypto_onetimeauth(c, 16, c, 32, d - 32, c)
        ///for (i = 0; i < 16; i++) c[i] = 0;
        return 0
    }

    fun crypto_secretbox_open(
        m: ByteArray,
        c: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        k: ByteArray
    ): Int {
        var i: Int
        val x = ByteArray(32)
        if (d < 32) return -1
        crypto_stream(x, 0, 32, n, k)
        if (crypto_onetimeauth_verify(c, 16, c, 32, d - 32, x) != 0) return -1
        crypto_stream_xor(m, 0, c, 0, d.toLong(), n, k)
        ///for (i = 0; i < 32; i++) m[i] = 0;
        return 0
    }

    private fun set25519(r: LongArray?, a: LongArray) {
        var i: Int
        i = 0
        while (i < 16) {
            r!![i] = a[i]
            i++
        }
    }

    private fun car25519(o: LongArray) {
        var i: Int
        var v: Long
        var c: Long = 1
        i = 0
        while (i < 16) {
            v = o[i] + c + 65535
            c = v shr 16
            o[i] = v - c * 65536
            i++
        }
        o[0] += c - 1 + 37 * (c - 1)
    }

    private fun sel25519(
        p: LongArray,
        q: LongArray,
        b: Int
    ) {
        sel25519(p, 0, q, 0, b)
    }

    private fun sel25519(
        p: LongArray?, poff: Int,
        q: LongArray?, qoff: Int,
        b: Int
    ) {
        var t: Long
        val c = (b - 1).toLong().inv()
        for (i in 0..15) {
            t = c and (p!![i + poff] xor q!![i + qoff])
            p[i + poff] = p[i + poff] xor t
            q[i + qoff] = q[i + qoff] xor t
        }
    }

    private fun pack25519(o: ByteArray, n: LongArray?, noff: Int) {
        var i: Int
        var j: Int
        var b: Int
        val m = LongArray(16)
        val t = LongArray(16)
        i = 0
        while (i < 16) {
            t[i] = n!![i + noff]
            i++
        }
        car25519(t)
        car25519(t)
        car25519(t)
        j = 0
        while (j < 2) {
            m[0] = t[0] - 0xffed
            i = 1
            while (i < 15) {
                m[i] = t[i] - 0xffff - (m[i - 1] shr 16 and 1L)
                m[i - 1] = m[i - 1] and 0xffffL
                i++
            }
            m[15] = t[15] - 0x7fff - (m[14] shr 16 and 1L)
            b = (m[15] shr 16 and 1L).toInt()
            m[14] = m[14] and 0xffffL
            sel25519(t, 0, m, 0, 1 - b)
            j++
        }
        i = 0
        while (i < 16) {
            o[2 * i] = (t[i] and 0xffL).toByte()
            o[2 * i + 1] = (t[i] shr 8).toByte()
            i++
        }
    }

    private fun neq25519(a: LongArray, b: LongArray): Int {
        return neq25519(a, 0, b, 0)
    }

    private fun neq25519(a: LongArray, aoff: Int, b: LongArray, boff: Int): Int {
        val c = ByteArray(32)
        val d = ByteArray(32)
        pack25519(c, a, aoff)
        pack25519(d, b, boff)
        return crypto_verify_32(c, 0, d, 0)
    }

    private fun par25519(a: LongArray?, aoff: Int = 0): Byte {
        val d = ByteArray(32)
        pack25519(d, a, aoff)
        return (d[0].toInt() and 1).toByte()
    }

    private fun unpack25519(o: LongArray?, n: ByteArray) {
        var i: Int
        i = 0
        while (i < 16) {
            o!![i] = (n[2 * i].toInt() and 0xff) + (n[2 * i + 1].toInt() shl 8 and 0xffff).toLong()
            i++
        }
        o!![15] = o[15] and 0x7fffL
    }

    private fun A(
        o: LongArray,
        a: LongArray?,
        b: LongArray
    ) {
        A(o, 0, a, 0, b, 0)
    }

    private fun A(
        o: LongArray, ooff: Int,
        a: LongArray?, aoff: Int,
        b: LongArray?, boff: Int
    ) {
        var i: Int
        i = 0
        while (i < 16) {
            o[i + ooff] = a!![i + aoff] + b!![i + boff]
            i++
        }
    }

    private fun Z(
        o: LongArray?,
        a: LongArray,
        b: LongArray?
    ) {
        Z(o, 0, a, 0, b, 0)
    }

    private fun Z(
        o: LongArray?, ooff: Int,
        a: LongArray?, aoff: Int,
        b: LongArray?, boff: Int
    ) {
        var i: Int
        i = 0
        while (i < 16) {
            o!![i + ooff] = a!![i + aoff] - b!![i + boff]
            i++
        }
    }

    private fun M(
        o: LongArray?,
        a: LongArray?,
        b: LongArray?
    ) {
        M(o, 0, a, 0, b, 0)
    }

    private fun M(
        o: LongArray?, ooff: Int,
        a: LongArray?, aoff: Int,
        b: LongArray?, boff: Int
    ) {
        var v: Long
        var c: Long
        var t0: Long = 0
        var t1: Long = 0
        var t2: Long = 0
        var t3: Long = 0
        var t4: Long = 0
        var t5: Long = 0
        var t6: Long = 0
        var t7: Long = 0
        var t8: Long = 0
        var t9: Long = 0
        var t10: Long = 0
        var t11: Long = 0
        var t12: Long = 0
        var t13: Long = 0
        var t14: Long = 0
        var t15: Long = 0
        var t16: Long = 0
        var t17: Long = 0
        var t18: Long = 0
        var t19: Long = 0
        var t20: Long = 0
        var t21: Long = 0
        var t22: Long = 0
        var t23: Long = 0
        var t24: Long = 0
        var t25: Long = 0
        var t26: Long = 0
        var t27: Long = 0
        var t28: Long = 0
        var t29: Long = 0
        var t30: Long = 0
        val b0 = b!![0 + boff]
        val b1 = b[1 + boff]
        val b2 = b[2 + boff]
        val b3 = b[3 + boff]
        val b4 = b[4 + boff]
        val b5 = b[5 + boff]
        val b6 = b[6 + boff]
        val b7 = b[7 + boff]
        val b8 = b[8 + boff]
        val b9 = b[9 + boff]
        val b10 = b[10 + boff]
        val b11 = b[11 + boff]
        val b12 = b[12 + boff]
        val b13 = b[13 + boff]
        val b14 = b[14 + boff]
        val b15 = b[15 + boff]
        v = a!![0 + aoff]
        t0 += v * b0
        t1 += v * b1
        t2 += v * b2
        t3 += v * b3
        t4 += v * b4
        t5 += v * b5
        t6 += v * b6
        t7 += v * b7
        t8 += v * b8
        t9 += v * b9
        t10 += v * b10
        t11 += v * b11
        t12 += v * b12
        t13 += v * b13
        t14 += v * b14
        t15 += v * b15
        v = a[1 + aoff]
        t1 += v * b0
        t2 += v * b1
        t3 += v * b2
        t4 += v * b3
        t5 += v * b4
        t6 += v * b5
        t7 += v * b6
        t8 += v * b7
        t9 += v * b8
        t10 += v * b9
        t11 += v * b10
        t12 += v * b11
        t13 += v * b12
        t14 += v * b13
        t15 += v * b14
        t16 += v * b15
        v = a[2 + aoff]
        t2 += v * b0
        t3 += v * b1
        t4 += v * b2
        t5 += v * b3
        t6 += v * b4
        t7 += v * b5
        t8 += v * b6
        t9 += v * b7
        t10 += v * b8
        t11 += v * b9
        t12 += v * b10
        t13 += v * b11
        t14 += v * b12
        t15 += v * b13
        t16 += v * b14
        t17 += v * b15
        v = a[3 + aoff]
        t3 += v * b0
        t4 += v * b1
        t5 += v * b2
        t6 += v * b3
        t7 += v * b4
        t8 += v * b5
        t9 += v * b6
        t10 += v * b7
        t11 += v * b8
        t12 += v * b9
        t13 += v * b10
        t14 += v * b11
        t15 += v * b12
        t16 += v * b13
        t17 += v * b14
        t18 += v * b15
        v = a[4 + aoff]
        t4 += v * b0
        t5 += v * b1
        t6 += v * b2
        t7 += v * b3
        t8 += v * b4
        t9 += v * b5
        t10 += v * b6
        t11 += v * b7
        t12 += v * b8
        t13 += v * b9
        t14 += v * b10
        t15 += v * b11
        t16 += v * b12
        t17 += v * b13
        t18 += v * b14
        t19 += v * b15
        v = a[5 + aoff]
        t5 += v * b0
        t6 += v * b1
        t7 += v * b2
        t8 += v * b3
        t9 += v * b4
        t10 += v * b5
        t11 += v * b6
        t12 += v * b7
        t13 += v * b8
        t14 += v * b9
        t15 += v * b10
        t16 += v * b11
        t17 += v * b12
        t18 += v * b13
        t19 += v * b14
        t20 += v * b15
        v = a[6 + aoff]
        t6 += v * b0
        t7 += v * b1
        t8 += v * b2
        t9 += v * b3
        t10 += v * b4
        t11 += v * b5
        t12 += v * b6
        t13 += v * b7
        t14 += v * b8
        t15 += v * b9
        t16 += v * b10
        t17 += v * b11
        t18 += v * b12
        t19 += v * b13
        t20 += v * b14
        t21 += v * b15
        v = a[7 + aoff]
        t7 += v * b0
        t8 += v * b1
        t9 += v * b2
        t10 += v * b3
        t11 += v * b4
        t12 += v * b5
        t13 += v * b6
        t14 += v * b7
        t15 += v * b8
        t16 += v * b9
        t17 += v * b10
        t18 += v * b11
        t19 += v * b12
        t20 += v * b13
        t21 += v * b14
        t22 += v * b15
        v = a[8 + aoff]
        t8 += v * b0
        t9 += v * b1
        t10 += v * b2
        t11 += v * b3
        t12 += v * b4
        t13 += v * b5
        t14 += v * b6
        t15 += v * b7
        t16 += v * b8
        t17 += v * b9
        t18 += v * b10
        t19 += v * b11
        t20 += v * b12
        t21 += v * b13
        t22 += v * b14
        t23 += v * b15
        v = a[9 + aoff]
        t9 += v * b0
        t10 += v * b1
        t11 += v * b2
        t12 += v * b3
        t13 += v * b4
        t14 += v * b5
        t15 += v * b6
        t16 += v * b7
        t17 += v * b8
        t18 += v * b9
        t19 += v * b10
        t20 += v * b11
        t21 += v * b12
        t22 += v * b13
        t23 += v * b14
        t24 += v * b15
        v = a[10 + aoff]
        t10 += v * b0
        t11 += v * b1
        t12 += v * b2
        t13 += v * b3
        t14 += v * b4
        t15 += v * b5
        t16 += v * b6
        t17 += v * b7
        t18 += v * b8
        t19 += v * b9
        t20 += v * b10
        t21 += v * b11
        t22 += v * b12
        t23 += v * b13
        t24 += v * b14
        t25 += v * b15
        v = a[11 + aoff]
        t11 += v * b0
        t12 += v * b1
        t13 += v * b2
        t14 += v * b3
        t15 += v * b4
        t16 += v * b5
        t17 += v * b6
        t18 += v * b7
        t19 += v * b8
        t20 += v * b9
        t21 += v * b10
        t22 += v * b11
        t23 += v * b12
        t24 += v * b13
        t25 += v * b14
        t26 += v * b15
        v = a[12 + aoff]
        t12 += v * b0
        t13 += v * b1
        t14 += v * b2
        t15 += v * b3
        t16 += v * b4
        t17 += v * b5
        t18 += v * b6
        t19 += v * b7
        t20 += v * b8
        t21 += v * b9
        t22 += v * b10
        t23 += v * b11
        t24 += v * b12
        t25 += v * b13
        t26 += v * b14
        t27 += v * b15
        v = a[13 + aoff]
        t13 += v * b0
        t14 += v * b1
        t15 += v * b2
        t16 += v * b3
        t17 += v * b4
        t18 += v * b5
        t19 += v * b6
        t20 += v * b7
        t21 += v * b8
        t22 += v * b9
        t23 += v * b10
        t24 += v * b11
        t25 += v * b12
        t26 += v * b13
        t27 += v * b14
        t28 += v * b15
        v = a[14 + aoff]
        t14 += v * b0
        t15 += v * b1
        t16 += v * b2
        t17 += v * b3
        t18 += v * b4
        t19 += v * b5
        t20 += v * b6
        t21 += v * b7
        t22 += v * b8
        t23 += v * b9
        t24 += v * b10
        t25 += v * b11
        t26 += v * b12
        t27 += v * b13
        t28 += v * b14
        t29 += v * b15
        v = a[15 + aoff]
        t15 += v * b0
        t16 += v * b1
        t17 += v * b2
        t18 += v * b3
        t19 += v * b4
        t20 += v * b5
        t21 += v * b6
        t22 += v * b7
        t23 += v * b8
        t24 += v * b9
        t25 += v * b10
        t26 += v * b11
        t27 += v * b12
        t28 += v * b13
        t29 += v * b14
        t30 += v * b15
        t0 += 38 * t16
        t1 += 38 * t17
        t2 += 38 * t18
        t3 += 38 * t19
        t4 += 38 * t20
        t5 += 38 * t21
        t6 += 38 * t22
        t7 += 38 * t23
        t8 += 38 * t24
        t9 += 38 * t25
        t10 += 38 * t26
        t11 += 38 * t27
        t12 += 38 * t28
        t13 += 38 * t29
        t14 += 38 * t30
        // t15 left as is

        // first car
        c = 1
        v = t0 + c + 65535
        c = v shr 16
        t0 = v - c * 65536
        v = t1 + c + 65535
        c = v shr 16
        t1 = v - c * 65536
        v = t2 + c + 65535
        c = v shr 16
        t2 = v - c * 65536
        v = t3 + c + 65535
        c = v shr 16
        t3 = v - c * 65536
        v = t4 + c + 65535
        c = v shr 16
        t4 = v - c * 65536
        v = t5 + c + 65535
        c = v shr 16
        t5 = v - c * 65536
        v = t6 + c + 65535
        c = v shr 16
        t6 = v - c * 65536
        v = t7 + c + 65535
        c = v shr 16
        t7 = v - c * 65536
        v = t8 + c + 65535
        c = v shr 16
        t8 = v - c * 65536
        v = t9 + c + 65535
        c = v shr 16
        t9 = v - c * 65536
        v = t10 + c + 65535
        c = v shr 16
        t10 = v - c * 65536
        v = t11 + c + 65535
        c = v shr 16
        t11 = v - c * 65536
        v = t12 + c + 65535
        c = v shr 16
        t12 = v - c * 65536
        v = t13 + c + 65535
        c = v shr 16
        t13 = v - c * 65536
        v = t14 + c + 65535
        c = v shr 16
        t14 = v - c * 65536
        v = t15 + c + 65535
        c = v shr 16
        t15 = v - c * 65536
        t0 += c - 1 + 37 * (c - 1)

        // second car
        c = 1
        v = t0 + c + 65535
        c = v shr 16
        t0 = v - c * 65536
        v = t1 + c + 65535
        c = v shr 16
        t1 = v - c * 65536
        v = t2 + c + 65535
        c = v shr 16
        t2 = v - c * 65536
        v = t3 + c + 65535
        c = v shr 16
        t3 = v - c * 65536
        v = t4 + c + 65535
        c = v shr 16
        t4 = v - c * 65536
        v = t5 + c + 65535
        c = v shr 16
        t5 = v - c * 65536
        v = t6 + c + 65535
        c = v shr 16
        t6 = v - c * 65536
        v = t7 + c + 65535
        c = v shr 16
        t7 = v - c * 65536
        v = t8 + c + 65535
        c = v shr 16
        t8 = v - c * 65536
        v = t9 + c + 65535
        c = v shr 16
        t9 = v - c * 65536
        v = t10 + c + 65535
        c = v shr 16
        t10 = v - c * 65536
        v = t11 + c + 65535
        c = v shr 16
        t11 = v - c * 65536
        v = t12 + c + 65535
        c = v shr 16
        t12 = v - c * 65536
        v = t13 + c + 65535
        c = v shr 16
        t13 = v - c * 65536
        v = t14 + c + 65535
        c = v shr 16
        t14 = v - c * 65536
        v = t15 + c + 65535
        c = v shr 16
        t15 = v - c * 65536
        t0 += c - 1 + 37 * (c - 1)
        o!![0 + ooff] = t0
        o[1 + ooff] = t1
        o[2 + ooff] = t2
        o[3 + ooff] = t3
        o[4 + ooff] = t4
        o[5 + ooff] = t5
        o[6 + ooff] = t6
        o[7 + ooff] = t7
        o[8 + ooff] = t8
        o[9 + ooff] = t9
        o[10 + ooff] = t10
        o[11 + ooff] = t11
        o[12 + ooff] = t12
        o[13 + ooff] = t13
        o[14 + ooff] = t14
        o[15 + ooff] = t15
    }

    private fun S(
        o: LongArray,
        a: LongArray?
    ) {
        S(o, 0, a, 0)
    }

    private fun S(
        o: LongArray, ooff: Int,
        a: LongArray?, aoff: Int
    ) {
        M(o, ooff, a, aoff, a, aoff)
    }

    private fun inv25519(
        o: LongArray, ooff: Int,
        i: LongArray?, ioff: Int
    ) {
        val c = LongArray(16)
        var a: Int
        a = 0
        while (a < 16) {
            c[a] = i!![a + ioff]
            a++
        }
        a = 253
        while (a >= 0) {
            S(c, 0, c, 0)
            if (a != 2 && a != 4) M(c, 0, c, 0, i, ioff)
            a--
        }
        a = 0
        while (a < 16) {
            o[a + ooff] = c[a]
            a++
        }
    }

    private fun pow2523(o: LongArray, i: LongArray) {
        val c = LongArray(16)
        var a: Int
        a = 0
        while (a < 16) {
            c[a] = i[a]
            a++
        }
        a = 250
        while (a >= 0) {
            S(c, 0, c, 0)
            if (a != 1) M(c, 0, c, 0, i, 0)
            a--
        }
        a = 0
        while (a < 16) {
            o[a] = c[a]
            a++
        }
    }

    fun crypto_scalarmult(q: ByteArray, n: ByteArray, p: ByteArray): Int {
        val z = ByteArray(32)
        val x = LongArray(80)
        var r: Int
        var i: Int
        val a = LongArray(16)
        val b = LongArray(16)
        val c = LongArray(16)
        val d = LongArray(16)
        val e = LongArray(16)
        val f = LongArray(16)
        i = 0
        while (i < 31) {
            z[i] = n[i]
            i++
        }
        z[31] = (n[31].toInt() and 127 or 64 and 0xff).toByte()
        z[0] = (z[0].toInt() and 248).toByte()
        unpack25519(x, p)
        i = 0
        while (i < 16) {
            b[i] = x[i]
            c[i] = 0
            a[i] = c[i]
            d[i] = a[i]
            i++
        }
        d[0] = 1
        a[0] = d[0]
        i = 254
        while (i >= 0) {
            r = z[i ushr 3].toInt() ushr (i and 7) and 1
            sel25519(a, b, r)
            sel25519(c, d, r)
            A(e, a, c)
            Z(a, a, c)
            A(c, b, d)
            Z(b, b, d)
            S(d, e)
            S(f, a)
            M(a, c, a)
            M(c, b, e)
            A(e, a, c)
            Z(a, a, c)
            S(b, a)
            Z(c, d, f)
            M(a, c, _121665)
            A(a, a, d)
            M(c, c, a)
            M(a, d, f)
            M(d, b, x)
            S(b, e)
            sel25519(a, b, r)
            sel25519(c, d, r)
            --i
        }
        i = 0
        while (i < 16) {
            x[i + 16] = a[i]
            x[i + 32] = c[i]
            x[i + 48] = b[i]
            x[i + 64] = d[i]
            i++
        }
        inv25519(x, 32, x, 32)
        M(x, 16, x, 16, x, 32)
        pack25519(q, x, 16)
        return 0
    }

    fun crypto_scalarmult_base(q: ByteArray, n: ByteArray): Int {
        return crypto_scalarmult(q, n, _9)
    }

    /*fun crypto_box_keypair(y: ByteArray, x: ByteArray): Int {
        randombytes(x, 32)
        return crypto_scalarmult_base(y, x)
    }*/

    fun crypto_box_beforenm(k: ByteArray, y: ByteArray, x: ByteArray): Int {
        val s = ByteArray(32)
        crypto_scalarmult(s, x, y)

        /*String dbgt = "";
		for (int dbg = 0; dbg < s.length; dbg ++) dbgt += " "+s[dbg];
		Log.d(TAG, "crypto_box_beforenm -> "+dbgt);

	    dbgt = "";
		for (int dbg = 0; dbg < x.length; dbg ++) dbgt += " "+x[dbg];
		Log.d(TAG, "crypto_box_beforenm, x -> "+dbgt);
	    dbgt = "";
		for (int dbg = 0; dbg < y.length; dbg ++) dbgt += " "+y[dbg];
		Log.d(TAG, "crypto_box_beforenm, y -> "+dbgt);
		*/return crypto_core_hsalsa20(k, _0, s, sigma)
    }

    fun crypto_box_afternm(
        c: ByteArray,
        m: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        k: ByteArray
    ): Int {
        return crypto_secretbox(c, m, d, n, k)
    }

    fun crypto_box_open_afternm(
        m: ByteArray,
        c: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        k: ByteArray
    ): Int {
        return crypto_secretbox_open(m, c, d, n, k)
    }

    fun crypto_box(
        c: ByteArray,
        m: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        y: ByteArray,
        x: ByteArray
    ): Int {
        val k = ByteArray(32)

        ///L/og.d(TAG, "crypto_box start ...");
        crypto_box_beforenm(k, y, x)
        return crypto_box_afternm(c, m, d, n, k)
    }

    fun crypto_box_open(
        m: ByteArray,
        c: ByteArray,   /*long*/
        d: Int,
        n: ByteArray,
        y: ByteArray,
        x: ByteArray
    ): Int {
        val k = ByteArray(32)
        crypto_box_beforenm(k, y, x)
        return crypto_box_open_afternm(m, c, d, n, k)
    }

    private val K = longArrayOf(
        0x428a2f98d728ae22L, 0x7137449123ef65cdL, -0x4a3f043013b2c4d1L, -0x164a245a7e762444L,
        0x3956c25bf348b538L, 0x59f111f1b605d019L, -0x6dc07d5b50e6b065L, -0x54e3a12a25927ee8L,
        -0x27f855675cfcfdbeL, 0x12835b0145706fbeL, 0x243185be4ee4b28cL, 0x550c7dc3d5ffb4e2L,
        0x72be5d74f27b896fL, -0x7f214e01c4e9694fL, -0x6423f958da38edcbL, -0x3e640e8b3096d96cL,
        -0x1b64963e610eb52eL, -0x1041b879c7b0da1dL, 0x0fc19dc68b8cd5b5L, 0x240ca1cc77ac9c65L,
        0x2de92c6f592b0275L, 0x4a7484aa6ea6e483L, 0x5cb0a9dcbd41fbd4L, 0x76f988da831153b5L,
        -0x67c1aead11992055L, -0x57ce3992d24bcdf0L, -0x4ffcd8376704dec1L, -0x40a680384110f11cL,
        -0x391ff40cc257703eL, -0x2a586eb86cf558dbL, 0x06ca6351e003826fL, 0x142929670a0e6e70L,
        0x27b70a8546d22ffcL, 0x2e1b21385c26c926L, 0x4d2c6dfc5ac42aedL, 0x53380d139d95b3dfL,
        0x650a73548baf63deL, 0x766a0abb3c77b2a8L, -0x7e3d36d1b812511aL, -0x6d8dd37aeb7dcac5L,
        -0x5d40175eb30efc9cL, -0x57e599b443bdcfffL, -0x3db4748f2f07686fL, -0x3893ae5cf9ab41d0L,
        -0x2e6d17e62910ade8L, -0x2966f9dbaa9a56f0L, -0xbf1ca7aa88edfd6L, 0x106aa07032bbd1b8L,
        0x19a4c116b8d2d0c8L, 0x1e376c085141ab53L, 0x2748774cdf8eeb99L, 0x34b0bcb5e19b48a8L,
        0x391c0cb3c5c95a63L, 0x4ed8aa4ae3418acbL, 0x5b9cca4f7763e373L, 0x682e6ff3d6b2b8a3L,
        0x748f82ee5defb2fcL, 0x78a5636f43172f60L, -0x7b3787eb5e0f548eL, -0x7338fdf7e59bc614L,
        -0x6f410005dc9ce1d8L, -0x5baf9314217d4217L, -0x41065c084d3986ebL, -0x398e870d1c8dacd5L,
        -0x35d8c13115d99e64L, -0x2e794738de3f3df9L, -0x15258229321f14e2L, -0xa82b08011912e88L,
        0x06f067aa72176fbaL, 0x0a637dc5a2c898a6L, 0x113f9804bef90daeL, 0x1b710b35131c471bL,
        0x28db77f523047d84L, 0x32caab7b40c72493L, 0x3c9ebe0a15c9bebcL, 0x431d67c49c100d4cL,
        0x4cc5d4becb3e42b6L, 0x597f299cfc657e2aL, 0x5fcb6fab3ad6faecL, 0x6c44198c4a475817L
    )

    private fun crypto_hashblocks_hl(
        hh: IntArray,
        hl: IntArray,
        m: ByteArray?,
        moff: Int,
        n: Int
    ): Int {

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < n; dbg ++) dbgt += " "+m[dbg+moff];
        ///Log.d(TAG, "crypto_hashblocks_hl m/"+n + "-> "+dbgt);
        var n = n
        val wh = IntArray(16)
        val wl = IntArray(16)
        var bh0: Int
        var bh1: Int
        var bh2: Int
        var bh3: Int
        var bh4: Int
        var bh5: Int
        var bh6: Int
        var bh7: Int
        var bl0: Int
        var bl1: Int
        var bl2: Int
        var bl3: Int
        var bl4: Int
        var bl5: Int
        var bl6: Int
        var bl7: Int
        var th: Int
        var tl: Int
        var h: Int
        var l: Int
        var i: Int
        var j: Int
        var a: Int
        var b: Int
        var c: Int
        var d: Int
        var ah0 = hh[0]
        var ah1 = hh[1]
        var ah2 = hh[2]
        var ah3 = hh[3]
        var ah4 = hh[4]
        var ah5 = hh[5]
        var ah6 = hh[6]
        var ah7 = hh[7]
        var al0 = hl[0]
        var al1 = hl[1]
        var al2 = hl[2]
        var al3 = hl[3]
        var al4 = hl[4]
        var al5 = hl[5]
        var al6 = hl[6]
        var al7 = hl[7]
        var pos = 0
        while (n >= 128) {
            i = 0
            while (i < 16) {
                j = 8 * i + pos
                wh[i] =
                    m!![j + 0 + moff].toInt() and 0xff shl 24 or (m[j + 1 + moff].toInt() and 0xff shl 16) or (m[j + 2 + moff].toInt() and 0xff shl 8) or (m[j + 3 + moff].toInt() and 0xff shl 0)
                wl[i] =
                    m[j + 4 + moff].toInt() and 0xff shl 24 or (m[j + 5 + moff].toInt() and 0xff shl 16) or (m[j + 6 + moff].toInt() and 0xff shl 8) or (m[j + 7 + moff].toInt() and 0xff shl 0)
                i++
            }
            i = 0
            while (i < 80) {
                bh0 = ah0
                bh1 = ah1
                bh2 = ah2
                bh3 = ah3
                bh4 = ah4
                bh5 = ah5
                bh6 = ah6
                bh7 = ah7
                bl0 = al0
                bl1 = al1
                bl2 = al2
                bl3 = al3
                bl4 = al4
                bl5 = al5
                bl6 = al6
                bl7 = al7

                // add
                h = ah7
                l = al7
                a = l and 0xffff
                b = l ushr 16
                c = h and 0xffff
                d = h ushr 16

                // Sigma1
                h =
                    ah4 ushr 14 or (al4 shl 32) - 14 xor (ah4 ushr 18 or (al4 shl 32)) - 18 xor (al4 ushr 41 - 32 or (ah4 shl 32)) - (41 - 32)
                l =
                    al4 ushr 14 or (ah4 shl 32) - 14 xor (al4 ushr 18 or (ah4 shl 32)) - 18 xor (ah4 ushr 41 - 32 or (al4 shl 32)) - (41 - 32)
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16

                // Ch
                h = ah4 and ah5 xor (ah4.inv() and ah6)
                l = al4 and al5 xor (al4.inv() and al6)
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16

                // K
                ///h = K[i*2];
                ///l = K[i*2+1];
                h = (K[i] ushr 32 and 0xffffffffL).toInt()
                l = (K[i] ushr 0 and 0xffffffffL).toInt()

                ///Log.d(TAG, "i"+i + ",h:0x"+Integer.toHexString(h) + ",l:0x"+Integer.toHexString(l));
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16

                // w
                h = wh[i % 16]
                l = wl[i % 16]
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16
                b += a ushr 16
                c += b ushr 16
                d += c ushr 16
                th = c and 0xffff or (d shl 16)
                tl = a and 0xffff or (b shl 16)

                // add
                h = th
                l = tl
                a = l and 0xffff
                b = l ushr 16
                c = h and 0xffff
                d = h ushr 16

                // Sigma0
                h =
                    ah0 ushr 28 or (al0 shl 32) - 28 xor (al0 ushr 34 - 32 or (ah0 shl 32)) - (34 - 32) xor (al0 ushr 39 - 32 or (ah0 shl 32)) - (39 - 32)
                l =
                    al0 ushr 28 or (ah0 shl 32) - 28 xor (ah0 ushr 34 - 32 or (al0 shl 32)) - (34 - 32) xor (ah0 ushr 39 - 32 or (al0 shl 32)) - (39 - 32)
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16

                // Maj
                h = ah0 and ah1 xor (ah0 and ah2) xor (ah1 and ah2)
                l = al0 and al1 xor (al0 and al2) xor (al1 and al2)
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16
                b += a ushr 16
                c += b ushr 16
                d += c ushr 16
                bh7 = c and 0xffff or (d shl 16)
                bl7 = a and 0xffff or (b shl 16)

                // add
                h = bh3
                l = bl3
                a = l and 0xffff
                b = l ushr 16
                c = h and 0xffff
                d = h ushr 16
                h = th
                l = tl
                a += l and 0xffff
                b += l ushr 16
                c += h and 0xffff
                d += h ushr 16
                b += a ushr 16
                c += b ushr 16
                d += c ushr 16
                bh3 = c and 0xffff or (d shl 16)
                bl3 = a and 0xffff or (b shl 16)
                ah1 = bh0
                ah2 = bh1
                ah3 = bh2
                ah4 = bh3
                ah5 = bh4
                ah6 = bh5
                ah7 = bh6
                ah0 = bh7
                al1 = bl0
                al2 = bl1
                al3 = bl2
                al4 = bl3
                al5 = bl4
                al6 = bl5
                al7 = bl6
                al0 = bl7
                if (i % 16 == 15) {
                    j = 0
                    while (j < 16) {

                        // add
                        h = wh[j]
                        l = wl[j]
                        a = l and 0xffff
                        b = l ushr 16
                        c = h and 0xffff
                        d = h ushr 16
                        h = wh[(j + 9) % 16]
                        l = wl[(j + 9) % 16]
                        a += l and 0xffff
                        b += l ushr 16
                        c += h and 0xffff
                        d += h ushr 16

                        // sigma0
                        th = wh[(j + 1) % 16]
                        tl = wl[(j + 1) % 16]
                        h =
                            th ushr 1 or (tl shl 32) - 1 xor (th ushr 8 or (tl shl 32)) - 8 xor (th ushr 7)
                        l =
                            tl ushr 1 or (th shl 32) - 1 xor (tl ushr 8 or (th shl 32)) - 8 xor (tl ushr 7 or (th shl 32)) - 7
                        a += l and 0xffff
                        b += l ushr 16
                        c += h and 0xffff
                        d += h ushr 16

                        // sigma1
                        th = wh[(j + 14) % 16]
                        tl = wl[(j + 14) % 16]
                        h =
                            th ushr 19 or (tl shl 32) - 19 xor (tl ushr 61 - 32 or (th shl 32)) - (61 - 32) xor (th ushr 6)
                        l =
                            tl ushr 19 or (th shl 32) - 19 xor (th ushr 61 - 32 or (tl shl 32)) - (61 - 32) xor (tl ushr 6 or (th shl 32)) - 6
                        a += l and 0xffff
                        b += l ushr 16
                        c += h and 0xffff
                        d += h ushr 16
                        b += a ushr 16
                        c += b ushr 16
                        d += c ushr 16
                        wh[j] = c and 0xffff or (d shl 16)
                        wl[j] = a and 0xffff or (b shl 16)
                        j++
                    }
                }
                i++
            }

            // add
            h = ah0
            l = al0
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[0]
            l = hl[0]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah0 = c and 0xffff or (d shl 16)
            hh[0] = ah0
            al0 = a and 0xffff or (b shl 16)
            hl[0] = al0
            h = ah1
            l = al1
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[1]
            l = hl[1]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah1 = c and 0xffff or (d shl 16)
            hh[1] = ah1
            al1 = a and 0xffff or (b shl 16)
            hl[1] = al1
            h = ah2
            l = al2
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[2]
            l = hl[2]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah2 = c and 0xffff or (d shl 16)
            hh[2] = ah2
            al2 = a and 0xffff or (b shl 16)
            hl[2] = al2
            h = ah3
            l = al3
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[3]
            l = hl[3]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah3 = c and 0xffff or (d shl 16)
            hh[3] = ah3
            al3 = a and 0xffff or (b shl 16)
            hl[3] = al3
            h = ah4
            l = al4
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[4]
            l = hl[4]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah4 = c and 0xffff or (d shl 16)
            hh[4] = ah4
            al4 = a and 0xffff or (b shl 16)
            hl[4] = al4
            h = ah5
            l = al5
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[5]
            l = hl[5]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah5 = c and 0xffff or (d shl 16)
            hh[5] = ah5
            al5 = a and 0xffff or (b shl 16)
            hl[5] = al5
            h = ah6
            l = al6
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[6]
            l = hl[6]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah6 = c and 0xffff or (d shl 16)
            hh[6] = ah6
            al6 = a and 0xffff or (b shl 16)
            hl[6] = al6
            h = ah7
            l = al7
            a = l and 0xffff
            b = l ushr 16
            c = h and 0xffff
            d = h ushr 16
            h = hh[7]
            l = hl[7]
            a += l and 0xffff
            b += l ushr 16
            c += h and 0xffff
            d += h ushr 16
            b += a ushr 16
            c += b ushr 16
            d += c ushr 16
            ah7 = c and 0xffff or (d shl 16)
            hh[7] = ah7
            al7 = a and 0xffff or (b shl 16)
            hl[7] = al7
            pos += 128
            n -= 128

            /*dbgt = "";
				for (int dbg = 0; dbg < hh.length; dbg ++) dbgt += " "+hh[dbg];
				Log.d(TAG, "\ncrypto_hashblocks_hl hh -> "+dbgt);

				dbgt = "";
				for (int dbg = 0; dbg < hl.length; dbg ++) dbgt += " "+hl[dbg];
				Log.d(TAG, "\ncrypto_hashblocks_hl hl -> "+dbgt);*/
        }
        return n
    }

    // TBD 64bits of n
    ///int crypto_hash(byte [] out, byte [] m, long n)
    @JvmOverloads
    fun crypto_hash(
        out: ByteArray,
        m: ByteArray?,
        moff: Int = 0,
        n: Int = if (m != null) m.size else 0
    ): Int {
        var n = n
        val hh = IntArray(8)
        val hl = IntArray(8)
        val x = ByteArray(256)
        var i: Int
        val b = n
        var u: Long
        hh[0] = 0x6a09e667
        hh[1] = -0x4498517b
        hh[2] = 0x3c6ef372
        hh[3] = -0x5ab00ac6
        hh[4] = 0x510e527f
        hh[5] = -0x64fa9774
        hh[6] = 0x1f83d9ab
        hh[7] = 0x5be0cd19
        hl[0] = -0xc4336f8
        hl[1] = -0x7b3558c5
        hl[2] = -0x16b07d5
        hl[3] = 0x5f1d36f1
        hl[4] = -0x52197d2f
        hl[5] = 0x2b3e6c1f
        hl[6] = -0x4be4295
        hl[7] = 0x137e2179
        if (n >= 128) {
            crypto_hashblocks_hl(hh, hl, m, moff, n)
            n %= 128
        }
        i = 0
        while (i < n) {
            x[i] = m!![b - n + i + moff]
            i++
        }
        x[n] = 128.toByte()
        n = 256 - 128 * if (n < 112) 1 else 0
        x[n - 9] = 0
        ts64(x, n - 8, (b shl 3 /*(b / 0x20000000) | 0, b << 3*/).toLong())
        crypto_hashblocks_hl(hh, hl, x, 0, n)
        i = 0
        while (i < 8) {
            u = hh[i].toLong()
            u = u shl 32
            u = u or (hl[i].toLong() and 0xffffffffL)
            ts64(out, 8 * i, u)
            i++
        }
        return 0
    }

    // gf: long[16]
    ///private static void add(gf p[4],gf q[4])
    private fun add(p: Array<LongArray?>, q: Array<LongArray?>) {
        val a = LongArray(16)
        val b = LongArray(16)
        val c = LongArray(16)
        val d = LongArray(16)
        val t = LongArray(16)
        val e = LongArray(16)
        val f = LongArray(16)
        val g = LongArray(16)
        val h = LongArray(16)
        val p0 = p[0]
        val p1 = p[1]
        val p2 = p[2]
        val p3 = p[3]
        val q0 = q[0]
        val q1 = q[1]
        val q2 = q[2]
        val q3 = q[3]
        Z(a, 0, p1, 0, p0, 0)
        Z(t, 0, q1, 0, q0, 0)
        M(a, 0, a, 0, t, 0)
        A(b, 0, p0, 0, p1, 0)
        A(t, 0, q0, 0, q1, 0)
        M(b, 0, b, 0, t, 0)
        M(c, 0, p3, 0, q3, 0)
        M(c, 0, c, 0, D2, 0)
        M(d, 0, p2, 0, q2, 0)
        A(d, 0, d, 0, d, 0)
        Z(e, 0, b, 0, a, 0)
        Z(f, 0, d, 0, c, 0)
        A(g, 0, d, 0, c, 0)
        A(h, 0, b, 0, a, 0)
        M(p0, 0, e, 0, f, 0)
        M(p1, 0, h, 0, g, 0)
        M(p2, 0, g, 0, f, 0)
        M(p3, 0, e, 0, h, 0)
    }

    private fun cswap(p: Array<LongArray?>, q: Array<LongArray?>, b: Byte) {
        var i: Int
        i = 0
        while (i < 4) {
            sel25519(p[i], 0, q[i], 0, b.toInt())
            i++
        }
    }

    private fun pack(r: ByteArray, p: Array<LongArray?>) {
        val tx = LongArray(16)
        val ty = LongArray(16)
        val zi = LongArray(16)
        inv25519(zi, 0, p[2], 0)
        M(tx, 0, p[0], 0, zi, 0)
        M(ty, 0, p[1], 0, zi, 0)
        pack25519(r, ty, 0)
        r[31] = (r[31].toInt() xor (par25519(tx, 0)
            .toInt() shl 7)).toByte()
    }

    private fun scalarmult(p: Array<LongArray?>, q: Array<LongArray?>, s: ByteArray, soff: Int) {
        var i: Int
        set25519(p[0], gf0)
        set25519(p[1], gf1)
        set25519(p[2], gf1)
        set25519(p[3], gf0)
        i = 255
        while (i >= 0) {
            val b = (s[i / 8 + soff].toInt() ushr (i and 7) and 1).toByte()
            cswap(p, q, b)
            add(q, p)
            add(p, p)
            cswap(p, q, b)
            --i
        }

        ///String dbgt = "";
        ///for (int dbg = 0; dbg < p.length; dbg ++) for (int dd = 0; dd < p[dbg].length; dd ++) dbgt += " "+p[dbg][dd];
        ///L/og.d(TAG, "scalarmult -> "+dbgt);
    }

    private fun scalarbase(p: Array<LongArray?>, s: ByteArray, soff: Int) {
        val q = arrayOfNulls<LongArray>(4)
        q[0] = LongArray(16)
        q[1] = LongArray(16)
        q[2] = LongArray(16)
        q[3] = LongArray(16)
        set25519(q[0], X)
        set25519(q[1], Y)
        set25519(q[2], gf1)
        M(q[3], 0, X, 0, Y, 0)
        scalarmult(p, q, s, soff)
    }

    /*fun crypto_sign_keypair(pk: ByteArray, sk: ByteArray, seeded: Boolean): Int {
        val d = ByteArray(64)
        val p = arrayOfNulls<LongArray>(4)
        p[0] = LongArray(16)
        p[1] = LongArray(16)
        p[2] = LongArray(16)
        p[3] = LongArray(16)
        var i: Int
        if (!seeded) randombytes(sk, 32)
        crypto_hash(d, sk, 0, 32)
        d[0] = (d[0].toInt() and 248).toByte()
        d[31] = (d[31].toInt() and 127).toByte()
        d[31] = (d[31].toInt() or 64).toByte()
        scalarbase(p, d, 0)
        pack(pk, p)
        i = 0
        while (i < 32) {
            sk[i + 32] = pk[i]
            i++
        }
        return 0
    }*/

    private val L = longArrayOf(
        0xed, 0xd3, 0xf5, 0x5c, 0x1a, 0x63, 0x12, 0x58,
        0xd6, 0x9c, 0xf7, 0xa2, 0xde, 0xf9, 0xde, 0x14,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0x10
    )

    private fun modL(r: ByteArray, roff: Int, x: LongArray) {
        var carry: Long
        var i: Int
        var j: Int
        i = 63
        while (i >= 32) {
            carry = 0
            j = i - 32
            while (j < i - 12) {
                x[j] += carry - 16 * x[i] * L[j - (i - 32)]
                carry = x[j] + 128 shr 8
                x[j] -= carry shl 8
                ++j
            }
            x[j] += carry
            x[i] = 0
            --i
        }
        carry = 0
        j = 0
        while (j < 32) {
            x[j] += carry - (x[31] shr 4) * L[j]
            carry = x[j] shr 8
            x[j] = x[j] and 255L
            j++
        }
        j = 0
        while (j < 32) {
            x[j] -= carry * L[j]
            j++
        }
        i = 0
        while (i < 32) {
            x[i + 1] += x[i] shr 8
            r[i + roff] = (x[i] and 255L).toByte()
            i++
        }
    }

    private fun reduce(r: ByteArray) {
        val x = LongArray(64)
        var i: Int
        i = 0
        while (i < 64) {
            x[i] = (r[i].toInt() and 0xff).toLong()
            i++
        }
        i = 0
        while (i < 64) {
            r[i] = 0
            i++
        }
        modL(r, 0, x)
    }

    // TBD... 64bits of n
    ///int crypto_sign(byte [] sm, long * smlen, byte [] m, long n, byte [] sk)
    fun crypto_sign(
        sm: ByteArray,
        dummy: Long /* *smlen not used*/,
        m: ByteArray,
        moff: Int,   /*long*/
        n: Int,
        sk: ByteArray
    ): Int {
        val d = ByteArray(64)
        val h = ByteArray(64)
        val r = ByteArray(64)
        var i: Int
        var j: Int
        val x = LongArray(64)
        val p = arrayOfNulls<LongArray>(4)
        p[0] = LongArray(16)
        p[1] = LongArray(16)
        p[2] = LongArray(16)
        p[3] = LongArray(16)
        crypto_hash(d, sk, 0, 32)
        d[0] = (d[0].toInt() and 248).toByte()
        d[31] = (d[31].toInt() and 127).toByte()
        d[31] = (d[31].toInt() or 64).toByte()

        ///*smlen = n+64;
        i = 0
        while (i < n) {
            sm[64 + i] = m[i + moff]
            i++
        }
        i = 0
        while (i < 32) {
            sm[32 + i] = d[32 + i]
            i++
        }
        crypto_hash(r, sm, 32, n + 32)
        reduce(r)
        scalarbase(p, r, 0)
        pack(sm, p)
        i = 0
        while (i < 32) {
            sm[i + 32] = sk[i + 32]
            i++
        }
        crypto_hash(h, sm, 0, n + 64)
        reduce(h)
        i = 0
        while (i < 64) {
            x[i] = 0
            i++
        }
        i = 0
        while (i < 32) {
            x[i] = (r[i].toInt() and 0xff).toLong()
            i++
        }
        i = 0
        while (i < 32) {
            j = 0
            while (j < 32) {
                x[i + j] += (h[i].toInt() and 0xff) * (d[j].toInt() and 0xff).toLong()
                j++
            }
            i++
        }
        modL(sm, 32, x)
        return 0
    }

    private fun unpackneg(r: Array<LongArray?>, p: ByteArray): Int {
        val t = LongArray(16)
        val chk = LongArray(16)
        val num = LongArray(16)
        val den = LongArray(16)
        val den2 = LongArray(16)
        val den4 = LongArray(16)
        val den6 = LongArray(16)
        set25519(r[2], gf1)
        unpack25519(r[1], p)
        S(num, r[1])
        M(den, num, D)
        Z(num, num, r[2])
        A(den, r[2], den)
        S(den2, den)
        S(den4, den2)
        M(den6, den4, den2)
        M(t, den6, num)
        M(t, t, den)
        pow2523(t, t)
        M(t, t, num)
        M(t, t, den)
        M(t, t, den)
        M(r[0], t, den)
        S(chk, r[0])
        M(chk, chk, den)
        if (neq25519(chk, num) != 0) M(
            r[0], r[0], I
        )
        S(chk, r[0])
        M(chk, chk, den)
        if (neq25519(chk, num) != 0) return -1
        if (par25519(r[0]).toInt() == p[31].toInt() and 0xFF ushr 7) Z(
            r[0], gf0, r[0]
        )
        M(r[3], r[0], r[1])
        return 0
    }

    /// TBD 64bits of mlen
    ///int crypto_sign_open(byte []m,long *mlen,byte []sm,long n,byte []pk)
    fun crypto_sign_open(
        m: ByteArray,
        dummy: Long /* *mlen not used*/,
        sm: ByteArray,
        smoff: Int,   /*long*/
        n: Int,
        pk: ByteArray
    ): Int {
        var n = n
        var i: Int
        val t = ByteArray(32)
        val h = ByteArray(64)
        val p = arrayOfNulls<LongArray>(4)
        p[0] = LongArray(16)
        p[1] = LongArray(16)
        p[2] = LongArray(16)
        p[3] = LongArray(16)
        val q = arrayOfNulls<LongArray>(4)
        q[0] = LongArray(16)
        q[1] = LongArray(16)
        q[2] = LongArray(16)
        q[3] = LongArray(16)

        ///*mlen = -1;
        if (n < 64) return -1
        if (unpackneg(q, pk) != 0) return -1
        i = 0
        while (i < n) {
            m[i] = sm[i + smoff]
            i++
        }
        i = 0
        while (i < 32) {
            m[i + 32] = pk[i]
            i++
        }
        crypto_hash(h, m, 0, n)
        reduce(h)
        scalarmult(p, q, h, 0)
        scalarbase(q, sm, 32 + smoff)
        add(p, q)
        pack(t, p)
        n -= 64
        return if (crypto_verify_32(sm, smoff, t, 0) != 0) {
            // optimizing it
            ///for (i = 0; i < n; i ++) m[i] = 0;
            -1
        } else 0

        // TBD optimizing ...
        ///for (i = 0; i < n; i ++) m[i] = sm[i + 64 + smoff];
        ///*mlen = n;
    }

    /*
     * @description
     *   Java SecureRandom generator
     * */
    /*private val jrandom: java.security.SecureRandom = java.security.SecureRandom()
    fun randombytes(x: ByteArray): ByteArray {
        jrandom.nextBytes(x)
        return x
    }

    fun randombytes(len: Int): ByteArray {
        return randombytes(ByteArray(len))
    }

    fun randombytes(x: ByteArray, len: Int): ByteArray {
        val b = randombytes(len)
        java.lang.System.arraycopy(b, 0, x, 0, len)
        return x
    }

    fun makeBoxNonce(): ByteArray {
        return randombytes(Box.nonceLength)
    }

    fun makeSecretBoxNonce(): ByteArray {
        return randombytes(SecretBox.nonceLength)
    }

    fun hexEncodeToString(raw: ByteArray): String {
        val HEXES = "0123456789ABCDEF"
        val hex: java.lang.StringBuilder = java.lang.StringBuilder(2 * raw.size)
        for (b in raw) {
            hex.append(HEXES[b.toInt() and 0xF0 shr 4])
                .append(HEXES[b.toInt() and 0x0F])
        }
        return hex.toString()
    }*/

    fun hexDecode(s: String): ByteArray {
        val b = ByteArray(s.length / 2)
        var i = 0
        while (i < s.length) {
            b[i / 2] = ((s[i].digitToIntOrNull(16) ?: -1 shl 4)
            + s[i + 1].digitToIntOrNull(16)!! ?: -1).toByte()
            i += 2
        }
        return b
    }

    // public static boolean java.util.Arrays.equals(array1, array2);
    // Check that a pubkey is on the curve.
    fun is_on_curve(p: ByteArray): Int {
        val r = arrayOf(LongArray(16), LongArray(16), LongArray(16), LongArray(16))
        val t = LongArray(16)
        val chk = LongArray(16)
        val num = LongArray(16)
        val den = LongArray(16)
        val den2 = LongArray(16)
        val den4 = LongArray(16)
        val den6 = LongArray(16)
        set25519(r[2], gf1)
        unpack25519(r[1], p)
        S(num, r[1])
        M(den, num, D)
        Z(num, num, r[2])
        A(den, r[2], den)
        S(den2, den)
        S(den4, den2)
        M(den6, den4, den2)
        M(t, den6, num)
        M(t, t, den)
        pow2523(t, t)
        M(t, t, num)
        M(t, t, den)
        M(t, t, den)
        M(r[0], t, den)
        S(chk, r[0])
        M(chk, chk, den)
        if (neq25519(chk, num) != 0) M(
            r[0], r[0], I
        )
        S(chk, r[0])
        M(chk, chk, den)
        return if (neq25519(chk, num) != 0) 0 else 1
    }

    /*
     * Port of Andrew Moon's Poly1305-donna-16. Public domain.
     * https://github.com/floodyberry/poly1305-donna
     */
    class poly1305(key: ByteArray) {
        private val buffer: ByteArray
        private val r: IntArray
        private val h: IntArray
        private val pad: IntArray
        private var leftover: Int
        private var fin: Int

        init {
            buffer = ByteArray(16)
            r = IntArray(10)
            h = IntArray(10)
            pad = IntArray(8)
            leftover = 0
            fin = 0
            val t0: Int
            val t1: Int
            val t2: Int
            val t3: Int
            val t4: Int
            val t5: Int
            val t6: Int
            val t7: Int
            t0 = key[0].toInt() and 0xff or (key[1].toInt() and 0xff shl 8)
            r[0] = t0 and 0x1fff
            t1 = key[2].toInt() and 0xff or (key[3].toInt() and 0xff shl 8)
            r[1] = t0 ushr 13 or (t1 shl 3) and 0x1fff
            t2 = key[4].toInt() and 0xff or (key[5].toInt() and 0xff shl 8)
            r[2] = t1 ushr 10 or (t2 shl 6) and 0x1f03
            t3 = key[6].toInt() and 0xff or (key[7].toInt() and 0xff shl 8)
            r[3] = t2 ushr 7 or (t3 shl 9) and 0x1fff
            t4 = key[8].toInt() and 0xff or (key[9].toInt() and 0xff shl 8)
            r[4] = t3 ushr 4 or (t4 shl 12) and 0x00ff
            r[5] = t4 ushr 1 and 0x1ffe
            t5 = key[10].toInt() and 0xff or (key[11].toInt() and 0xff shl 8)
            r[6] = t4 ushr 14 or (t5 shl 2) and 0x1fff
            t6 = key[12].toInt() and 0xff or (key[13].toInt() and 0xff shl 8)
            r[7] = t5 ushr 11 or (t6 shl 5) and 0x1f81
            t7 = key[14].toInt() and 0xff or (key[15].toInt() and 0xff shl 8)
            r[8] = t6 ushr 8 or (t7 shl 8) and 0x1fff
            r[9] = t7 ushr 5 and 0x007f
            pad[0] = key[16].toInt() and 0xff or (key[17].toInt() and 0xff shl 8)
            pad[1] = key[18].toInt() and 0xff or (key[19].toInt() and 0xff shl 8)
            pad[2] = key[20].toInt() and 0xff or (key[21].toInt() and 0xff shl 8)
            pad[3] = key[22].toInt() and 0xff or (key[23].toInt() and 0xff shl 8)
            pad[4] = key[24].toInt() and 0xff or (key[25].toInt() and 0xff shl 8)
            pad[5] = key[26].toInt() and 0xff or (key[27].toInt() and 0xff shl 8)
            pad[6] = key[28].toInt() and 0xff or (key[29].toInt() and 0xff shl 8)
            pad[7] = key[30].toInt() and 0xff or (key[31].toInt() and 0xff shl 8)
        }

        fun blocks(m: ByteArray?, mpos: Int, bytes: Int): poly1305 {
            var mpos = mpos
            var bytes = bytes
            val hibit = if (fin != 0) 0 else 1 shl 11
            var t0: Int
            var t1: Int
            var t2: Int
            var t3: Int
            var t4: Int
            var t5: Int
            var t6: Int
            var t7: Int
            var c: Int
            var d0: Int
            var d1: Int
            var d2: Int
            var d3: Int
            var d4: Int
            var d5: Int
            var d6: Int
            var d7: Int
            var d8: Int
            var d9: Int
            var h0 = h[0]
            var h1 = h[1]
            var h2 = h[2]
            var h3 = h[3]
            var h4 = h[4]
            var h5 = h[5]
            var h6 = h[6]
            var h7 = h[7]
            var h8 = h[8]
            var h9 = h[9]
            val r0 = r[0]
            val r1 = r[1]
            val r2 = r[2]
            val r3 = r[3]
            val r4 = r[4]
            val r5 = r[5]
            val r6 = r[6]
            val r7 = r[7]
            val r8 = r[8]
            val r9 = r[9]
            while (bytes >= 16) {
                t0 = m!![mpos + 0].toInt() and 0xff or (m[mpos + 1].toInt() and 0xff shl 8)
                h0 += t0 and 0x1fff
                t1 = m[mpos + 2].toInt() and 0xff or (m[mpos + 3].toInt() and 0xff shl 8)
                h1 += t0 ushr 13 or (t1 shl 3) and 0x1fff
                t2 = m[mpos + 4].toInt() and 0xff or (m[mpos + 5].toInt() and 0xff shl 8)
                h2 += t1 ushr 10 or (t2 shl 6) and 0x1fff
                t3 = m[mpos + 6].toInt() and 0xff or (m[mpos + 7].toInt() and 0xff shl 8)
                h3 += t2 ushr 7 or (t3 shl 9) and 0x1fff
                t4 = m[mpos + 8].toInt() and 0xff or (m[mpos + 9].toInt() and 0xff shl 8)
                h4 += t3 ushr 4 or (t4 shl 12) and 0x1fff
                h5 += t4 ushr 1 and 0x1fff
                t5 = m[mpos + 10].toInt() and 0xff or (m[mpos + 11].toInt() and 0xff shl 8)
                h6 += t4 ushr 14 or (t5 shl 2) and 0x1fff
                t6 = m[mpos + 12].toInt() and 0xff or (m[mpos + 13].toInt() and 0xff shl 8)
                h7 += t5 ushr 11 or (t6 shl 5) and 0x1fff
                t7 = m[mpos + 14].toInt() and 0xff or (m[mpos + 15].toInt() and 0xff shl 8)
                h8 += t6 ushr 8 or (t7 shl 8) and 0x1fff
                h9 += t7 ushr 5 or hibit
                c = 0
                d0 = c
                d0 += h0 * r0
                d0 += h1 * (5 * r9)
                d0 += h2 * (5 * r8)
                d0 += h3 * (5 * r7)
                d0 += h4 * (5 * r6)
                c = d0 ushr 13
                d0 = d0 and 0x1fff
                d0 += h5 * (5 * r5)
                d0 += h6 * (5 * r4)
                d0 += h7 * (5 * r3)
                d0 += h8 * (5 * r2)
                d0 += h9 * (5 * r1)
                c += d0 ushr 13
                d0 = d0 and 0x1fff
                d1 = c
                d1 += h0 * r1
                d1 += h1 * r0
                d1 += h2 * (5 * r9)
                d1 += h3 * (5 * r8)
                d1 += h4 * (5 * r7)
                c = d1 ushr 13
                d1 = d1 and 0x1fff
                d1 += h5 * (5 * r6)
                d1 += h6 * (5 * r5)
                d1 += h7 * (5 * r4)
                d1 += h8 * (5 * r3)
                d1 += h9 * (5 * r2)
                c += d1 ushr 13
                d1 = d1 and 0x1fff
                d2 = c
                d2 += h0 * r2
                d2 += h1 * r1
                d2 += h2 * r0
                d2 += h3 * (5 * r9)
                d2 += h4 * (5 * r8)
                c = d2 ushr 13
                d2 = d2 and 0x1fff
                d2 += h5 * (5 * r7)
                d2 += h6 * (5 * r6)
                d2 += h7 * (5 * r5)
                d2 += h8 * (5 * r4)
                d2 += h9 * (5 * r3)
                c += d2 ushr 13
                d2 = d2 and 0x1fff
                d3 = c
                d3 += h0 * r3
                d3 += h1 * r2
                d3 += h2 * r1
                d3 += h3 * r0
                d3 += h4 * (5 * r9)
                c = d3 ushr 13
                d3 = d3 and 0x1fff
                d3 += h5 * (5 * r8)
                d3 += h6 * (5 * r7)
                d3 += h7 * (5 * r6)
                d3 += h8 * (5 * r5)
                d3 += h9 * (5 * r4)
                c += d3 ushr 13
                d3 = d3 and 0x1fff
                d4 = c
                d4 += h0 * r4
                d4 += h1 * r3
                d4 += h2 * r2
                d4 += h3 * r1
                d4 += h4 * r0
                c = d4 ushr 13
                d4 = d4 and 0x1fff
                d4 += h5 * (5 * r9)
                d4 += h6 * (5 * r8)
                d4 += h7 * (5 * r7)
                d4 += h8 * (5 * r6)
                d4 += h9 * (5 * r5)
                c += d4 ushr 13
                d4 = d4 and 0x1fff
                d5 = c
                d5 += h0 * r5
                d5 += h1 * r4
                d5 += h2 * r3
                d5 += h3 * r2
                d5 += h4 * r1
                c = d5 ushr 13
                d5 = d5 and 0x1fff
                d5 += h5 * r0
                d5 += h6 * (5 * r9)
                d5 += h7 * (5 * r8)
                d5 += h8 * (5 * r7)
                d5 += h9 * (5 * r6)
                c += d5 ushr 13
                d5 = d5 and 0x1fff
                d6 = c
                d6 += h0 * r6
                d6 += h1 * r5
                d6 += h2 * r4
                d6 += h3 * r3
                d6 += h4 * r2
                c = d6 ushr 13
                d6 = d6 and 0x1fff
                d6 += h5 * r1
                d6 += h6 * r0
                d6 += h7 * (5 * r9)
                d6 += h8 * (5 * r8)
                d6 += h9 * (5 * r7)
                c += d6 ushr 13
                d6 = d6 and 0x1fff
                d7 = c
                d7 += h0 * r7
                d7 += h1 * r6
                d7 += h2 * r5
                d7 += h3 * r4
                d7 += h4 * r3
                c = d7 ushr 13
                d7 = d7 and 0x1fff
                d7 += h5 * r2
                d7 += h6 * r1
                d7 += h7 * r0
                d7 += h8 * (5 * r9)
                d7 += h9 * (5 * r8)
                c += d7 ushr 13
                d7 = d7 and 0x1fff
                d8 = c
                d8 += h0 * r8
                d8 += h1 * r7
                d8 += h2 * r6
                d8 += h3 * r5
                d8 += h4 * r4
                c = d8 ushr 13
                d8 = d8 and 0x1fff
                d8 += h5 * r3
                d8 += h6 * r2
                d8 += h7 * r1
                d8 += h8 * r0
                d8 += h9 * (5 * r9)
                c += d8 ushr 13
                d8 = d8 and 0x1fff
                d9 = c
                d9 += h0 * r9
                d9 += h1 * r8
                d9 += h2 * r7
                d9 += h3 * r6
                d9 += h4 * r5
                c = d9 ushr 13
                d9 = d9 and 0x1fff
                d9 += h5 * r4
                d9 += h6 * r3
                d9 += h7 * r2
                d9 += h8 * r1
                d9 += h9 * r0
                c += d9 ushr 13
                d9 = d9 and 0x1fff
                c = (c shl 2) + c or 0
                c = c + d0 or 0
                d0 = c and 0x1fff
                c = c ushr 13
                d1 += c
                h0 = d0
                h1 = d1
                h2 = d2
                h3 = d3
                h4 = d4
                h5 = d5
                h6 = d6
                h7 = d7
                h8 = d8
                h9 = d9
                mpos += 16
                bytes -= 16
            }
            h[0] = h0
            h[1] = h1
            h[2] = h2
            h[3] = h3
            h[4] = h4
            h[5] = h5
            h[6] = h6
            h[7] = h7
            h[8] = h8
            h[9] = h9
            return this
        }

        fun finish(mac: ByteArray, macpos: Int): poly1305 {
            val g = IntArray(10)
            var c: Int
            var mask: Int
            var f: Int
            var i: Int
            if (leftover != 0) {
                i = leftover
                buffer[i++] = 1
                while (i < 16) {
                    buffer[i] = 0
                    i++
                }
                fin = 1
                blocks(buffer, 0, 16)
            }
            c = h[1] ushr 13
            h[1] = h[1] and 0x1fff
            i = 2
            while (i < 10) {
                h[i] += c
                c = h[i] ushr 13
                h[i] = h[i] and 0x1fff
                i++
            }
            h[0] += c * 5
            c = h[0] ushr 13
            h[0] = h[0] and 0x1fff
            h[1] += c
            c = h[1] ushr 13
            h[1] = h[1] and 0x1fff
            h[2] += c
            g[0] = h[0] + 5
            c = g[0] ushr 13
            g[0] = g[0] and 0x1fff
            i = 1
            while (i < 10) {
                g[i] = h[i] + c
                c = g[i] ushr 13
                g[i] = g[i] and 0x1fff
                i++
            }
            g[9] -= 1 shl 13
            g[9] = g[9] and 0xffff

            /*
                        backport from tweetnacl-fast.js https://github.com/dchest/tweetnacl-js/releases/tag/v0.14.3
                        <<<
                        "The issue was not properly detecting if st->h was >= 2^130 - 5,
                        coupled with [testing mistake] not catching the failure.
                        The chance of the bug affecting anything in the real world is essentially zero luckily,
                        but it's good to have it fixed."
                        >>>
                        */
            ///change mask = (g[9] >>> ((2 * 8) - 1)) - 1; to as
            mask = (c xor 1) - 1
            mask = mask and 0xffff
            ///////////////////////////////////////
            i = 0
            while (i < 10) {
                g[i] = g[i] and mask
                i++
            }
            mask = mask.inv()
            i = 0
            while (i < 10) {
                h[i] = h[i] and mask or g[i]
                i++
            }
            h[0] = h[0] or (h[1] shl 13) and 0xffff
            h[1] = h[1] ushr 3 or (h[2] shl 10) and 0xffff
            h[2] = h[2] ushr 6 or (h[3] shl 7) and 0xffff
            h[3] = h[3] ushr 9 or (h[4] shl 4) and 0xffff
            h[4] = h[4] ushr 12 or (h[5] shl 1) or (h[6] shl 14) and 0xffff
            h[5] = h[6] ushr 2 or (h[7] shl 11) and 0xffff
            h[6] = h[7] ushr 5 or (h[8] shl 8) and 0xffff
            h[7] = h[8] ushr 8 or (h[9] shl 5) and 0xffff
            f = h[0] + pad[0]
            h[0] = f and 0xffff
            i = 1
            while (i < 8) {
                f = (h[i] + pad[i] or 0) + (f ushr 16) or 0
                h[i] = f and 0xffff
                i++
            }
            mac[macpos + 0] = (h[0] ushr 0 and 0xff).toByte()
            mac[macpos + 1] = (h[0] ushr 8 and 0xff).toByte()
            mac[macpos + 2] = (h[1] ushr 0 and 0xff).toByte()
            mac[macpos + 3] = (h[1] ushr 8 and 0xff).toByte()
            mac[macpos + 4] = (h[2] ushr 0 and 0xff).toByte()
            mac[macpos + 5] = (h[2] ushr 8 and 0xff).toByte()
            mac[macpos + 6] = (h[3] ushr 0 and 0xff).toByte()
            mac[macpos + 7] = (h[3] ushr 8 and 0xff).toByte()
            mac[macpos + 8] = (h[4] ushr 0 and 0xff).toByte()
            mac[macpos + 9] = (h[4] ushr 8 and 0xff).toByte()
            mac[macpos + 10] = (h[5] ushr 0 and 0xff).toByte()
            mac[macpos + 11] = (h[5] ushr 8 and 0xff).toByte()
            mac[macpos + 12] = (h[6] ushr 0 and 0xff).toByte()
            mac[macpos + 13] = (h[6] ushr 8 and 0xff).toByte()
            mac[macpos + 14] = (h[7] ushr 0 and 0xff).toByte()
            mac[macpos + 15] = (h[7] ushr 8 and 0xff).toByte()
            return this
        }

        fun update(m: ByteArray?, mpos: Int, bytes: Int): poly1305 {
            var mpos = mpos
            var bytes = bytes
            var i: Int
            var want: Int
            if (leftover != 0) {
                want = 16 - leftover
                if (want > bytes) want = bytes
                i = 0
                while (i < want) {
                    buffer[leftover + i] = m!![mpos + i]
                    i++
                }
                bytes -= want
                mpos += want
                leftover += want
                if (leftover < 16) return this
                blocks(buffer, 0, 16)
                leftover = 0
            }
            if (bytes >= 16) {
                want = bytes - bytes % 16
                blocks(m, mpos, want)
                mpos += want
                bytes -= want
            }
            if (bytes != 0) {
                i = 0
                while (i < bytes) {
                    buffer[leftover + i] = m!![mpos + i]
                    i++
                }
                leftover += bytes
            }
            return this
        }
    }
}