package pl.msiwak.multiplatform.utils.extensions

fun Char.isNumber() = isDigit() || toString() == "." || toString() == ","
fun Char.isTime() = isDigit() || toString() == ":" || toString() == "."
