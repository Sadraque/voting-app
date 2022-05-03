package com.votingapp.configuration.interceptor;

import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomInterceptor implements HandlerInterceptor {

    private StopWatch stopWatch;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startStopWatch();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        endStopWatch();
    }

    private StopWatch startStopWatch() {
        this.stopWatch = new StopWatch();

        this.stopWatch.start();

        return this.stopWatch;
    }

    private StopWatch endStopWatch() {
        this.stopWatch.stop();

        return this.stopWatch;
    }
}
