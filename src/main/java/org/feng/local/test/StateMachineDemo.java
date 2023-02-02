package org.feng.local.test;

import java.util.EnumMap;
import java.util.Map;

// 定义状态枚举
enum State {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
    RETRY,
    MANUAL_REVIEW
}

// 定义事件枚举
enum Event {
    START_PROCESSING,
    PROCESS_SUCCESS,
    PROCESS_FAIL,
    RETRY_LIMIT_REACHED,
    MANUAL_INTERVENTION_REQUIRED,
    RETRY,
    MANUAL_RESOLVE
}

// 状态机类
class CallStateMachine {
    private final int maxRetries;
    private final Map<State, Map<Event, State>> transitions;
    private State currentState;
    private int retryCount;

    public CallStateMachine() {
        this.currentState = State.PENDING;
        this.retryCount = 0;
        this.maxRetries = 3;
        this.transitions = new EnumMap<>(State.class);
        initializeTransitions();
    }

    private void initializeTransitions() {
        // PENDING状态的转换
        Map<Event, State> pendingTransitions = new EnumMap<>(Event.class);
        pendingTransitions.put(Event.START_PROCESSING, State.PROCESSING);
        transitions.put(State.PENDING, pendingTransitions);

        // PROCESSING状态的转换
        Map<Event, State> processingTransitions = new EnumMap<>(Event.class);
        processingTransitions.put(Event.PROCESS_SUCCESS, State.COMPLETED);
        processingTransitions.put(Event.PROCESS_FAIL, State.FAILED);
        transitions.put(State.PROCESSING, processingTransitions);

        // FAILED状态的转换
        Map<Event, State> failedTransitions = new EnumMap<>(Event.class);
        failedTransitions.put(Event.RETRY, State.RETRY);
        failedTransitions.put(Event.RETRY_LIMIT_REACHED, State.MANUAL_REVIEW);
        transitions.put(State.FAILED, failedTransitions);

        // RETRY状态的转换
        Map<Event, State> retryTransitions = new EnumMap<>(Event.class);
        retryTransitions.put(Event.START_PROCESSING, State.PROCESSING);
        transitions.put(State.RETRY, retryTransitions);

        // MANUAL_REVIEW状态的转换
        Map<Event, State> manualReviewTransitions = new EnumMap<>(Event.class);
        manualReviewTransitions.put(Event.MANUAL_RESOLVE, State.PENDING);
        manualReviewTransitions.put(Event.MANUAL_INTERVENTION_REQUIRED, State.COMPLETED);
        transitions.put(State.MANUAL_REVIEW, manualReviewTransitions);
    }

    public State getState() {
        return currentState;
    }

    public State transition(Event event) {
        Map<Event, State> stateTransitions = transitions.get(currentState);
        if (stateTransitions != null && stateTransitions.containsKey(event)) {
            State nextState = stateTransitions.get(event);

            // 特殊逻辑处理
            if (currentState == State.FAILED && event == Event.RETRY) {
                if (retryCount < maxRetries) {
                    retryCount++;
                } else {
                    return currentState; // 不允许再次重试
                }
            }

            if (event == Event.MANUAL_RESOLVE) {
                retryCount = 0; // 重置重试计数
            }

            currentState = nextState;
            return currentState;
        }
        // 如果没有有效的转换，保持当前状态
        return currentState;
    }
}

// 主类，用于测试
public class StateMachineDemo {
    public static void main(String[] args) {
        CallStateMachine callSM = new CallStateMachine();

        System.out.println("Initial state: " + callSM.getState());

        callSM.transition(Event.START_PROCESSING);
        System.out.println("After START_PROCESSING: " + callSM.getState());

        callSM.transition(Event.PROCESS_FAIL);
        System.out.println("After PROCESS_FAIL: " + callSM.getState());

        callSM.transition(Event.RETRY);
        System.out.println("After RETRY: " + callSM.getState());

        callSM.transition(Event.START_PROCESSING);
        System.out.println("After START_PROCESSING again: " + callSM.getState());

        callSM.transition(Event.PROCESS_SUCCESS);
        System.out.println("After PROCESS_SUCCESS: " + callSM.getState());
    }
}