package com.algo.ref.ex.kafka;

import java.util.function.Consumer;

public interface IReceiver {
    Boolean receive(Consumer<String> callback);
}
