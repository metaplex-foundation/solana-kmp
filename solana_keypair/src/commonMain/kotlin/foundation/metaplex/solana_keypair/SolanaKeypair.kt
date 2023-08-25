package foundation.metaplex.solana_keypair
import foundation.metaplex.solana_interfaces.Keypair
import foundation.metaplex.solana_public_keys.PublicKey

class SolanaKeypair(
    override val publicKey: PublicKey,
    override val secretKey: ByteArray
) : Keypair