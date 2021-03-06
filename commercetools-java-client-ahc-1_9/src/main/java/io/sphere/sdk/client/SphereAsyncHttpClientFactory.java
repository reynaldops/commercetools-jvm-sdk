package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.models.Base;

public final class SphereAsyncHttpClientFactory extends Base {
    private SphereAsyncHttpClientFactory() {
    }

    public static HttpClient create() {
        return AsyncHttpClientAdapter.of(new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"}).build()));
    }
}
