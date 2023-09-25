package foundation.metaplex.mplbubblegum.system

import com.funkatronics.kborsh.BorshEncoder
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.jvm.JvmStatic

object SystemProgram : Program() {
    val PROGRAM_ID = PublicKey("11111111111111111111111111111111")
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
        data.encodeSerializableValue(PublicKeyAs32ByteSerializer, programId)
        return createTransactionInstruction(PROGRAM_ID, keys, data.borshEncodedBytes)
    }
}