package com.javierortizmi.duckhuntinggame

import android.graphics.Point
import android.graphics.Rect
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class Game// starting cannon angle
    (newDuckRect: Rect?, newBulletRadius: Int, newDuckSpeed: Float, newBulletSpeed: Float) {
    private var huntingRect: Rect? = null
    private var deltaTime = 0 // in milliSeconds
    private var duckRect: Rect? = null
    private var duckWidth = 0
    private var duckHeight = 0
    private var duckSpeed = 0f
    private var duckShot: Boolean  = false
    private var cannonCenter: Point? = null
    private var cannonRadius = 0
    private var barrelLength = 0
    private var barrelRadius = 0
    private var cannonAngle: Float = 0.0f
    private var bulletCenter: Point? = null
    private var bulletRadius = 0
    private var bulletFired: Boolean = false
    private var bulletAngle = 0f
    private var bulletSpeed = 0f
    private var random: Random? = null

    init {
        setDuckRect(newDuckRect)
        setDuckSpeed(newDuckSpeed)
        setBulletRadius(newBulletRadius)
        setBulletSpeed(newBulletSpeed)
        random = Random()
        bulletFired = false
        duckShot = false
        cannonAngle = Math.PI.toFloat() / 4
    }

    fun getCannonCenter(): Point {
        return cannonCenter!!
    }

    fun getCannonRadius(): Int {
        return cannonRadius
    }

    fun getBarrelLength(): Int {
        return barrelLength
    }

    fun getBulletCenter(): Point {
        return bulletCenter!!
    }

    fun getDuckRect(): Rect {
        return duckRect!!
    }

    /* fun getHuntingRect(): Rect? {
        return huntingRect
    } */

    fun setHuntingRect(newHuntingRect: Rect?) {
        if (newHuntingRect != null)
            huntingRect = newHuntingRect
    }

    fun setDeltaTime(newDeltaTime: Int) {
        if (newDeltaTime > 0)
            deltaTime = newDeltaTime
    }

    private fun setDuckRect(newDuckRect: Rect?) {
        if (newDuckRect != null) {
            duckWidth = newDuckRect.right - newDuckRect.left
            duckHeight = newDuckRect.bottom - newDuckRect.top
            duckRect = newDuckRect
        }
    }

    fun setDuckSpeed(newDuckSpeed: Float) {
        if (newDuckSpeed > 0) duckSpeed = newDuckSpeed
    }

    fun setCannon(
        newCannonCenter: Point?, newCannonRadius: Int,
        newBarrelLength: Int, newBarrelRadius: Int
    ) {
        if (newCannonCenter != null && ( newCannonRadius > 0 ) && ( newBarrelLength > 0 ) ) {
            cannonCenter = newCannonCenter
            cannonRadius = newCannonRadius
            barrelLength = newBarrelLength
            barrelRadius = newBarrelRadius
            bulletCenter = Point(
                (cannonCenter!!.x + cannonRadius * cos(cannonAngle.toDouble())).toInt(),
                (cannonCenter!!.y - cannonRadius * sin(cannonAngle.toDouble())).toInt()
            )
        }
    }

    fun getBulletRadius(): Int {
        return bulletRadius
    }

    private fun setBulletRadius(newBulletRadius: Int) {
        if (newBulletRadius > 0)
            bulletRadius = newBulletRadius
    }

    fun setBulletSpeed(newBulletSpeed: Float) {
        if (newBulletSpeed > 0)
            bulletSpeed = newBulletSpeed
    }

    fun getCannonAngle(): Float {
        return cannonAngle
    }

    fun setCannonAngle(newCannonAngle: Float) {
        cannonAngle = if (newCannonAngle >= 0 && newCannonAngle <= Math.PI / 2)
            newCannonAngle
        else if (newCannonAngle < 0)
            0f
        else
            Math.PI.toFloat() / 2
        if (!bulletFired)
            loadBullet()
    }

    fun fireBullet() {
        bulletFired = true
        bulletAngle = cannonAngle
    }

    fun isBulletFired(): Boolean {
        return bulletFired
    }

    /* fun isDuckShot(): Boolean {
        return duckShot
    } */

    fun setDuckShot(newDuckShot: Boolean) {
        duckShot = newDuckShot
    }

    fun startDuckFromRightTopHalf() {
        duckRect!!.left = huntingRect!!.right
        duckRect!!.right = duckRect!!.left + duckWidth
        duckRect!!.top = random!!.nextInt(huntingRect!!.bottom / 2)
        duckRect!!.bottom = duckRect!!.top + duckHeight
    }

    fun moveDuck() {
        if (!duckShot) { // move left
            duckRect!!.left -= (duckSpeed * deltaTime).toInt()
            duckRect!!.right -= (duckSpeed * deltaTime).toInt()
        } else { // move down
            duckRect!!.top += (5 * duckSpeed * deltaTime).toInt()
            duckRect!!.bottom += (5 * duckSpeed * deltaTime).toInt()
        }
    }

    fun duckOffScreen(): Boolean {
        return ( duckRect!!.right < 0 ) || ( duckRect!!.bottom < 0 )
                || ( duckRect!!.top > huntingRect!!.bottom )
                || ( duckRect!!.left > huntingRect!!.right )
    }

    fun moveBullet() {
        bulletCenter!!.x += (bulletSpeed * cos(bulletAngle.toDouble()) * deltaTime).toInt()
        bulletCenter!!.y -= (bulletSpeed * sin(bulletAngle.toDouble()) * deltaTime).toInt()
    }

    fun bulletOffScreen(): Boolean {
        return (bulletCenter!!.x - bulletRadius > huntingRect!!.right
                || bulletCenter!!.y + bulletRadius < 0)
    }

    fun loadBullet() {
        bulletFired = false
        bulletCenter!!.x = (cannonCenter!!.x
                + cannonRadius * cos(cannonAngle.toDouble())).toInt()
        bulletCenter!!.y = (cannonCenter!!.y
                - cannonRadius * sin(cannonAngle.toDouble())).toInt()
    }

    fun duckHit(): Boolean {
        return duckRect!!.intersects(
            bulletCenter!!.x - bulletRadius, bulletCenter!!.y - bulletRadius,
            bulletCenter!!.x + bulletRadius, bulletCenter!!.y + bulletRadius
        )
    }
}