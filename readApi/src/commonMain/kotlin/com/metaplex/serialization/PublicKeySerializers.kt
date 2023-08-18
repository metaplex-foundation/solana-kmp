/*
 * PublicKeySerializers
 * Metaplex
 * 
 * Created by Funkatronics on 7/20/2022
 */

package com.metaplex.serialization

import com.metaplex.solana_public_keys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object PublicKeyAsStringSerializer : KSerializer<PublicKey> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PublicKey")
    override fun serialize(encoder: Encoder, value: PublicKey) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): PublicKey = PublicKey(decoder.decodeString())
}