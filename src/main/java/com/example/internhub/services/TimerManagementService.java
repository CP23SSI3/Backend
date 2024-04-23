package com.example.internhub.services;

import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TimerManagementService {
    private ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<>();

    public void setTimer(String timerKey, TimerTask task, long delay) {
        Timer timer = new Timer();
        timers.put(timerKey, timer);
        timer.schedule(task, delay);
    }

    public void cancelTimer(String timerKey) {
        Timer timer = timers.get(timerKey);
        if (timer != null) {
            timer.cancel();
            timers.remove(timerKey);
        }
    }

    public void cancelAllTimers() {
        timers.forEach((id, timer) -> timer.cancel());
        timers.clear();
    }
}
