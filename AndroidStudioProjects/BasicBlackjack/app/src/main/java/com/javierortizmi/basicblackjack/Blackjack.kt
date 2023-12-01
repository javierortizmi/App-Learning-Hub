package com.javierortizmi.basicblackjack

class Blackjack(currentTotal: Int) {

    private var currentTotal : Int = 0

    init {
        setCurrentTotal(currentTotal)
    }

    private fun setCurrentTotal(currentTotal : Int) {
        if (currentTotal >= 0)
            this.currentTotal += currentTotal
    }

    fun getCurrentTotal() : Int {
        return currentTotal
    }

    private fun generateCard() : Int {
        return (2..13).random()
    }

    fun updateCards(dealNO : Int) : String {
        return if (dealNO in 1..3) {

            val card : Int = generateCard()
            setCurrentTotal(card)
            card.toString()

        } else ""
    }
}