package com.javierortizmi.ponggame

import android.graphics.Point

class Pong(private var deltaTime: Int) {

    private lateinit var ball: Ball
    private lateinit var paddle: Paddle

    fun setBall(ballCentre: Point, ballSpeed: Int, ballRadius: Int) {
        ball = Ball(ballCentre, ballSpeed, ballRadius)
    }
    fun getBall(): Ball {
        return ball
    }

    fun setPaddle(paddlePosition: Point, paddleWidth: Int, paddleHeight: Int) {
        paddle = Paddle(paddlePosition, paddleWidth, paddleHeight)
    }
    fun getPaddle(): Paddle {
        return paddle
    }

    fun getDeltaTime(): Int {
        return deltaTime
    }

    // BALL
    inner class Ball(private var ballCentre: Point, private var ballSpeed: Int, private var ballRadius: Int) {

        private var horizontalDirection = 0
        private var verticalDirection = 0

        fun getBallCentre(): Point {
            return ballCentre
        }

        fun getBallRadius(): Int {
            return ballRadius
        }

        fun getVerticalDirection(): Int {
            return verticalDirection
        }

        fun moveBall(case: Int) {
            when (case) {
                0 -> {  // DOWN RIGHT
                    ballCentre.y += (ballSpeed * getDeltaTime())
                    ballCentre.x += (ballSpeed * getDeltaTime())
                }
                1 -> {  // DOWN LEFT
                    ballCentre.y += (ballSpeed * getDeltaTime())
                    ballCentre.x -= (ballSpeed * getDeltaTime())
                }
                2 -> {  // UP RIGHT
                    ballCentre.y -= (ballSpeed * getDeltaTime())
                    ballCentre.x += (ballSpeed * getDeltaTime())
                }
                3 -> {  // UP LEFT
                    ballCentre.y -= (ballSpeed * getDeltaTime())
                    ballCentre.x -= (ballSpeed * getDeltaTime())
                }
                else -> {   // STOP BALL
                    ballCentre.y = -50
                    ballCentre.x = -50
                }
            }
        }

        fun checkCollision(width: Int, height: Int, paddle: Paddle): Int {
            if (ballCentre.x - getBallRadius() < 0 || ballCentre.x + getBallRadius() > width)
                changeDirection(0)
            if (ballCentre.y - getBallRadius() < 0 ||
                (ballCentre.y + getBallRadius() > paddle.getPaddlePosition().y - paddle.getPaddleHeight() &&
                        ballCentre.y + getBallRadius() < paddle.getPaddlePosition().y &&
                        ballCentre.x > paddle.getPaddlePosition().x - paddle.getPaddleWidth() &&
                        ballCentre.x < paddle.getPaddlePosition().x + paddle.getPaddleWidth()))
            {
                changeDirection(1)
            }
            if (ballCentre.y + getBallRadius() > height)
                return 4
            return if (horizontalDirection == 0 && verticalDirection == 0)
                0
            else if (horizontalDirection == 1 && verticalDirection == 0)
                1
            else if (horizontalDirection == 0 && verticalDirection == 1)
                2
            else if (horizontalDirection == 1 && verticalDirection == 1)
                3
            else
                4
        }

        private fun changeDirection(axis: Int) {
            if (axis == 0)
                horizontalDirection = if (horizontalDirection == 0)
                    1   // LEFT
                else
                    0   // RIGHT
            else
                verticalDirection = if (verticalDirection == 0)
                    1   // UP
                else
                    0   // DOWN
        }
    }

    // PADDLE
    // Paddle position = Paddle centre
    inner class Paddle(private var paddlePosition: Point, private var paddleWidth: Int, private var paddleHeight: Int) {
        fun setPaddlePosition(paddlePosition: Point) {
            this.paddlePosition = paddlePosition
        }
        fun getPaddlePosition(): Point {
            return paddlePosition
        }

        fun getPaddleWidth(): Int {
            return paddleWidth
        }

        fun getPaddleHeight(): Int {
            return paddleHeight
        }

        fun paddleCollisionCheck(): Boolean {
            return getPaddlePosition().y - getPaddleHeight() == ball.getBallCentre().y + ball.getBallRadius() &&
                    ball.getBallCentre().x > getPaddlePosition().x - getPaddleWidth() &&
                    ball.getBallCentre().x < getPaddlePosition().x + getPaddleWidth() &&
                    ball.getVerticalDirection() == 1

        }
    }
}