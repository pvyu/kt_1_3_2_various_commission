enum class CardType {Unknown, Mastercard, Maestro, Visa, Mir, VK_Pay}
//---------------------------------------------------------------------

const val TRANSFER_LIMIT_ERROR : Int = -1
const val MONTH_LIMIT_ERROR : Int = -2
//---------------------------------------------------------------------

fun calsTransferTax(sum : Int, totalMonthSum : Int = 0, cardTypeFrom : CardType = CardType.VK_Pay) : Int {
    var result : Int = 0;

    val monthLimit_Master_Maestro : Int = 75_000
    val tax_percent_Master_Maestro : Double = 0.6
    val tax_fixed_Master_Maestro : Int = 20

    val tax_percent_Visa_Mir : Double = 0.75
    val tax_fixed_Visa_Mir : Int = 35

    val monthLimit_Card : Int = 600_000
    val transferLimit_Card : Int = 150_000

    val monthLimit_VK_Pay : Int = 600_000
    val transferLimit_VK_Pay : Int = 150_000

    var tax_percent : Double = 0.0
    var tax_fixed : Int = 0
    var monthLimit : Int = 0
    var transferLimit : Int = 0

    if (cardTypeFrom == CardType.VK_Pay) {
        monthLimit = monthLimit_VK_Pay;
        transferLimit = transferLimit_VK_Pay;
    }
    else {
        monthLimit = monthLimit_Card;
        transferLimit = transferLimit_Card;
    }

    if (sum > transferLimit) {
        return TRANSFER_LIMIT_ERROR;
    }
    if (sum + totalMonthSum > monthLimit) {
        return MONTH_LIMIT_ERROR;
    }

    if (cardTypeFrom == CardType.Mastercard || cardTypeFrom == CardType.Maestro) {
        if (sum + totalMonthSum > monthLimit_Master_Maestro) {
            tax_percent  = tax_percent_Master_Maestro
            tax_fixed = tax_fixed_Master_Maestro
        }
    }
    else if (cardTypeFrom == CardType.Visa || cardTypeFrom == CardType.Mir) {
        tax_percent  = tax_percent_Visa_Mir
        tax_fixed = tax_fixed_Visa_Mir
    }

    result = (sum * tax_percent/100.0).toInt() + tax_fixed

    return result
}
//---------------------------------------------------------------------

fun main() {
  val tax : Int = calsTransferTax(500, 0, CardType.Maestro)
  println(tax)
}