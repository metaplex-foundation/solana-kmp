package com.metaplex.solana_interfaces

/**
 * An object that can serialize and deserialize a value to and from a `ByteArray`.
 * It supports serializing looser types than it deserializes for convenience.
 * For example, a `BigInteger` serializer will always deserialize to a `BigInteger`
 * but can be used to serialize a `Number`.
 *
 * @param From The type of the value to serialize.
 * @param To The type of the deserialized value. Defaults to `From`.
 *
 * @category Serializers
 */
interface Serializer<From, To : From> {
    /** A description for the serializer. */
    val description: String
    /** The fixed size of the serialized value in bytes, or `null` if it is variable. */
    val fixedSize: Int?
    /** The maximum size a serialized value can be in bytes, or `null` if it is variable. */
    val maxSize: Int?
    /** The function that serializes a value into bytes. */
    fun serialize(value: From): ByteArray
    /**
     * The function that deserializes a value from bytes.
     * It returns the deserialized value and the number of bytes read.
     */
    fun deserialize(buffer: ByteArray, offset: Int = 0): Pair<To, Int>
}

/**
 * Defines common options for serializer factories.
 * @category Serializers
 */
interface BaseSerializerOptions {
    /** A custom description for the serializer. */
    val description: String?
}
