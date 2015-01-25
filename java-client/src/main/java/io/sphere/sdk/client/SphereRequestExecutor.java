package io.sphere.sdk.client;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public interface SphereRequestExecutor extends Closeable {
    <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest);

    void close();
}