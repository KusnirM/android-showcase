package mk.digital.androidshowcase.util

import java.util.Locale

//toto zrejme mozme teraz nnativny string formatter

object StringFormatter {
     fun formatDouble(value: Double, decimals: Int): String {
        return String.format(Locale.US, "%.${decimals}f", value)
    }
}
