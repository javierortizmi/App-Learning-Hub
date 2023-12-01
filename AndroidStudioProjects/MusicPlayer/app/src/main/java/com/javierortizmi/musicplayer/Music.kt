package com.javierortizmi.musicplayer

import android.media.MediaPlayer
import android.widget.SeekBar

object Music {
    fun setSeekBar(seekBar: SeekBar, mp: MediaPlayer, volume: Boolean? = null, seekTo: Boolean? = null) {
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    when {
                        volume == null -> mp.seekTo(p1)
                        seekTo == null -> {
                            val volumeNum = p1 / 100.0f
                            mp.setVolume(volumeNum, volumeNum)
                        }
                    }
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                // Not implemented
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                // Not implemented
            }
        })
    }

    fun createTimeLabel(time: Int): String {
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        var timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }
}