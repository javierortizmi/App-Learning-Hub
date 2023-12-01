package com.javierortizmi.ballonsgame

class Balloon(private var x: Int, private var y: Int, private var radius: Int) {

    // GETTERS AND SETTERS
    fun setX(x: Int) {
        this.x = x
    }
    fun getX(): Int {
        return x
    }
    fun setY(y: Int) {
        this.y = y
    }
    fun getY(): Int {
        return y
    }
    fun setRadius(radius: Int) {
        this.radius = radius
    }
    fun getRadius(): Int {
        return radius
    }


    override fun toString(): String {
        return "$x; $y; $radius"
    }
}