package com.javierortizmi.ponggame

import java.util.TimerTask

class GameTimerTask(private var activity: MainActivity): TimerTask() {
    override fun run() {
        // Update the model
        activity.updateModel()
        // Update the view
        activity.updateView()
    }
}