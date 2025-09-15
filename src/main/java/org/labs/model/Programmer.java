package org.labs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Programmer implements Runnable {
    private final int id;
    private final Fork left;
    private final Fork right;

    @Override
    public void run() {

    }
}
