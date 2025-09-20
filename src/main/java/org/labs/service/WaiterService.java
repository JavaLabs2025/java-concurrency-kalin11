package org.labs.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.labs.model.Waiter;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WaiterService {
    private final List<Waiter> waiters;


}
