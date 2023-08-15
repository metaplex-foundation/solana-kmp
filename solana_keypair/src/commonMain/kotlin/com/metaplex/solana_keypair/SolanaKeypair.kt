package com.metaplex.solana_keypair
import com.metaplex.solana_interfaces.Keypair
import com.metaplex.solana_public_keys.PublicKey

class SolanaKeypair(
    override val publicKey: PublicKey,
    override val secretKey: ByteArray
) : Keypair