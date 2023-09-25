package foundation.metaplex.mplbubblegum.generated.splsnoop

import kotlin.Int
import kotlin.String

sealed interface SPLSnoopError {
    val code: Int

    val message: String
}
