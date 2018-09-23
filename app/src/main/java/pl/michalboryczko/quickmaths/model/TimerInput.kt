package pl.michalboryczko.quickmaths.model

import java.util.concurrent.TimeUnit

class TimerInput(
        val initialDelay: Long,
        val startTime: Long,
        val endTime: Long,
        val interval: Long,
        val timeUnit: TimeUnit
)