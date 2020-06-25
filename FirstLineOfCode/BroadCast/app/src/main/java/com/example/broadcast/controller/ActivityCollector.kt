package com.example.broadcast.controller

import android.app.Activity

/**
 * ActivityCollector collects all the activities.
 * It's a kind of controller where we can finish all the activities.
 */
object ActivityCollector {
    private val activities = ArrayList<Activity>()

    /**
     * Add an activity to the activity list
     *
     * @param activity the target activity
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * Remove an activity from the activity list
     *
     * @param activity the target activity
     */
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    /**
     * Finish all the activities and clear the list
     */
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}