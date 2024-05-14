package foundation.metaplex.solana.programs

import com.funkatronics.kborsh.BorshEncoder
import com.solana.publickey.PublicKey
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlin.jvm.JvmStatic

import foundation.metaplex.solanapublickeys.PublicKey as SolanaPublicKey

object SystemProgram : Program() {
    val PROGRAM_ID = SolanaPublicKey("11111111111111111111111111111111")
    const val PROGRAM_INDEX_CREATE_ACCOUNT = 0
    const val PROGRAM_INDEX_TRANSFER = 2
    @JvmStatic
    fun transfer(
        fromPublicKey: PublicKey,
        toPublickKey: PublicKey,
        lamports: Long
    ): TransactionInstruction {
        val keys = ArrayList<AccountMeta>()
        keys.add(AccountMeta(fromPublicKey, true, true))
        keys.add(AccountMeta(toPublickKey, false, true))

        // 4 byte instruction index + 8 bytes lamports
        val data = BorshEncoder()
        data.encodeInt(PROGRAM_INDEX_TRANSFER)
        data.encodeLong(lamports)
        return createTransactionInstruction(PROGRAM_ID, keys, data.borshEncodedBytes)
    }

    fun createAccount(
        fromPublicKey: PublicKey, newAccountPublickey: PublicKey,
        lamports: Long, space: Long, programId: PublicKey
    ): TransactionInstruction {
        val keys = ArrayList<AccountMeta>()
        keys.add(AccountMeta(fromPublicKey, true, true))
        keys.add(AccountMeta(newAccountPublickey, true, true))
        val data = BorshEncoder()
        data.encodeInt(PROGRAM_INDEX_CREATE_ACCOUNT)
        data.encodeLong(lamports)
        data.encodeLong(space)
        data.encodeSerializableValue(ByteArraySerializer(), programId.bytes)
        return createTransactionInstruction(PROGRAM_ID, keys, data.borshEncodedBytes)
    }
}