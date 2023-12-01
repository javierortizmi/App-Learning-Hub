package com.javierortizmi.currencyconverter

class Travel(country: Int, exchangeRate: Double) {
    private var country:Int = 0             // 1 -> India; 2 -> Turkey; 3 -> Mexico; 4 -> Italy;
    private var exchangeRate:Double = 0.0

    init {
        setCountry(country)
        setExchangeRate(exchangeRate)
    }

    fun setCountry(country:Int) {
        this.country = country
    }
    fun getCountry() : Int {
        return country
    }
    fun setExchangeRate(exchangeRate:Double) {
        this.exchangeRate = exchangeRate
    }
    fun getExchangeRate() : Double {
        return exchangeRate
    }

    fun calculateMoneyExchange(exchangeAmount : Double) : String {
        val countryCurrency: String = when (country) {
            1 -> "₹" + "%.2f".format(getExchangeRate()*exchangeAmount) + "\nIndian Rupees"

            2 -> "₺" + "%.2f".format(getExchangeRate()*exchangeAmount) + "\nTurkish Lira"

            3 -> "$" + "%.2f".format(getExchangeRate()*exchangeAmount) + "\nPeso mexicano"

            else -> "%.2f".format(getExchangeRate()*exchangeAmount) + "€" + "\nEuro"
        }

        return countryCurrency
    }
}