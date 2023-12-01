package com.javierortizmi.chronometeradvertising

class ClockUtility {
    /*
   * This method computes and returns the equivalent number of milliseconds
   *   for its parameter, a String that represents a clock
   * @param  clock, a String, expected to look like mm:ss or hh:mm:ss
   * @return a long, the equivalent number of milliseconds to clock
   */
    companion object {
        fun milliseconds(clock: String): Long {
            var ms = 0L
            val clockArray: List<String> = clock.split(":")
            // compute milliseconds
            // compute milliseconds
            try {
                if (clockArray.size == 3) {
                    ms =
                        (clockArray[0].toInt() * 60 * 60 * 1000 + clockArray[1].toInt() * 60 * 1000 + clockArray[2].toInt() * 1000).toLong()
                } else if (clockArray.size == 2) {
                    ms = (clockArray[0].toInt() * 60 * 1000
                            + clockArray[1].toInt() * 1000).toLong()
                }
            } catch (nfe: NumberFormatException) {
                // should never get here if clock has proper format
            }
            return ms
        }
    }
}