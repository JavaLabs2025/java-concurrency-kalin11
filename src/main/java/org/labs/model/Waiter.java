package org.labs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Waiter implements Runnable {
    private final int id;

    @Override
    public void run() {

    }
}
