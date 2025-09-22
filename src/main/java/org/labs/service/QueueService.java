package org.labs.service;

import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class QueueService {
    private final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    public void put(Integer programmerId) {
        this.queue.add(programmerId);
    }

    public Integer poll() {
        if (this.queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    public boolean contains(Integer programmerId) {
        return this.queue.contains(programmerId);
    }

    public String print() {
        return this.queue.toString();
    }
}
