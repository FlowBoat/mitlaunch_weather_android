package io.github.flowboat.flowweather.data.bridge

import kotlinx.io.ByteBuffer
import kotlinx.io.ByteOrder

fun Array<Int>.packBytes(): ByteArray {
    return toIntArray().packBytes()
}

fun IntArray.packBytes(): ByteArray {
    return map(Int::packByte).toByteArray()
}

fun Int.packByte(): Byte {
    return this.toByte()
}

fun ByteBuffer.Companion.wrap(vararg bytes: Byte): ByteBuffer {
    val bb = ByteBuffer.allocate(bytes.size)
    bb.order(ByteOrder.BIG_ENDIAN)
    bb.put(bytes)
    return bb
}
