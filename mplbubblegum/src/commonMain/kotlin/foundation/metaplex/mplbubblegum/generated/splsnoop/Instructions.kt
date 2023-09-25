package foundation.metaplex.mplbubblegum.generated.splsnoop

import com.funkatronics.kborsh.Borsh
import foundation.metaplex.rpc.serializers.AnchorInstructionSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.Serializable
import kotlin.ByteArray

object SPLSnoopInstructions {
    fun noopInstruction(data: ByteArray): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        return TransactionInstruction(
            PublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                keys, Borsh.encodeToByteArray(
                AnchorInstructionSerializer("noop_instruction"),
                Args_noopInstruction(data)
            ))
    }

    @Serializable
    class Args_noopInstruction(val data: ByteArray)
}
