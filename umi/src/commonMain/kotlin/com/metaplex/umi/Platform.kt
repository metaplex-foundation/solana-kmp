package com.metaplex.umi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform