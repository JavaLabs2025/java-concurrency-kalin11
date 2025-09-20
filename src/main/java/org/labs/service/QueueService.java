package org.labs.service;

import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class QueueService {
    private final ConcurrentLinkedQueue<Integer> queue;

    public QueueService() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

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

    public void remove() {
        this.queue.remove();
    }

    public void print() {
        System.out.println(queue);
    }
}
