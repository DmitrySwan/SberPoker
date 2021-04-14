import java.util.*
import kotlin.collections.HashSet

fun main() {
    val cards = arrayOf("07s", "08s", "09s", "10s", "11s")

    if (cardsHaveDuplicates(cards)) {
        println("Джон жульничает, есть одинаковые карты.")
        return
    }

    val m: MutableSet<String> = HashSet()
    val currentCards: MutableList<Int> = ArrayList()
    for (card in cards) {
        currentCards.add(card.substring(0, 2).toInt())
        m.add(card.substring(2))
    }
    println("Список мастей карт: $m")
    println("Список карт: $currentCards")

    val currentCardCounts = orderAndCount(currentCards)

    val sortedCardCountsString = getStringOfOrder(currentCardCounts)
    val straight = sortedCardCountsString.contains("11111") // 5 карт по порядку

    val flush = m.size == 1 //если в Set всего один элемент -> у всех карт одна масть

    println("Результат: " + getResult(straight, flush, sortedCardCountsString))
}

private fun cardsHaveDuplicates(cards: Array<String>): Boolean {
    for (i in cards.indices) {
        for (j in i + 1 until cards.size) {
            if (cards[i] == cards[j]) {
                return true
            }
        }
    }
    return false
}

private fun orderAndCount(currentCards: List<Int>): IntArray {
    val currentCardCounts =
        IntArray(14) // массив, где индекс = достоинство карты, значение = количество карт одного достоинства
    for (card in currentCards) {
        currentCardCounts[card] = currentCardCounts[card] + 1
    }
    println("Количество карт одного достоинства: " + currentCardCounts.contentToString())
    return currentCardCounts
}

private fun getStringOfOrder(currentCardCounts: IntArray): String {
    val sortedCardCounts = StringBuilder()
    for (cardCount in currentCardCounts) {
        sortedCardCounts.append(cardCount)
    }
    return sortedCardCounts.toString()
}

private fun getResult(straight: Boolean, flush: Boolean, sortedCardCounts: String): Int {
    if (straight && flush) return 8
    if (sortedCardCounts.contains("4")) return 7

    val threeOfaKind = sortedCardCounts.contains("3")
    val pair = sortedCardCounts.contains("2")

    if (threeOfaKind && pair) return 6
    if (flush) return 5
    if (straight) return 4
    if (threeOfaKind) return 3
    return if (pair) {
        if (sortedCardCounts.split("2").toTypedArray().size == 3) {
            2
        } else 1
    } else 0
}