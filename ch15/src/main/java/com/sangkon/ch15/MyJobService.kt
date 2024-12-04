package com.sangkon.ch15

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build

class MyJobService : JobService() {
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "oneId", "oneName", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "oneDesc"
            manager.createNotificationChannel(channel)
            Notification.Builder(this, "oneId")
        } else {
            Notification.Builder(this)
        }.run {
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setContentTitle("JobScheduler Title")
            setContentText("Content Message")
            setAutoCancel(true)
            manager.notify(1, build())
        }
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }
}