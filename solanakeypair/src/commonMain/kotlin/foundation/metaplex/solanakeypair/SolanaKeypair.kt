package foundation.metaplex.solanakeypair
import foundation.metaplex.solanainterfaces.Keypair
import foundation.metaplex.solanapublickeys.PublicKey

class SolanaKeypair(
    override val publicKey: PublicKey,
    override val secretKey: ByteArray
) : Keypair