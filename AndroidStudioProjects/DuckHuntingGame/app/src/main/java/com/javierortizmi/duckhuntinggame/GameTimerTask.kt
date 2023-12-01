package com.javierortizmi.duckhuntinggame

import java.util.TimerTask

class GameTimerTask(mainActivity: MainActivity) : TimerTask() {
    private var activity : MainActivity = mainActivity

    override fun run() {
        // update the model
        activity.updateModel()
        // update the view
        activity.updateView()
    }
}