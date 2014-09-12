package io.sphere.sdk.client;


import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.typesafe.config.Config;
import io.sphere.sdk.http.*;
import io.sphere.sdk.meta.BuildInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.StringUtils.*;

public class NingAsyncHttpClient implements HttpClient {

    private final ClientCredentials clientCredentials;
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final String coreUrl;
    private final String projectKey;

    public NingAsyncHttpClient(final Config config) {
        clientCredentials = SphereClientCredentials.createAndBeginRefreshInBackground(config, new OAuthClient(asyncHttpClient));
        coreUrl = config.getString("sphere.core");
        projectKey = config.getString("sphere.project");
    }

    @Override
    public <T> CompletableFuture<HttpResponse> execute(final Requestable requestable) {
        final Request request = asNingRequest(requestable);
        try {
            final CompletableFuture<Response> future = CompletableFutureUtils.wrap(asyncHttpClient.executeRequest(request));
            return future.thenApply((Response response) -> {
                try {
                    return HttpResponse.of(response.getStatusCode(), response.getResponseBody(StandardCharsets.UTF_8.name()), requestable.httpRequest());
                } catch (IOException e) {
                    throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
        }
    }

    /* package scope for testing */
    <T> Request asNingRequest(final Requestable requestable) {
        final HttpRequest request = requestable.httpRequest();
        final RequestBuilder builder = new RequestBuilder().
                setUrl(stripEnd(coreUrl, "/") + "/" + projectKey + request.getPath()).
                setMethod(request.getHttpMethod().toString()).
                setHeader("User-Agent", "SPHERE.IO JVM SDK version " + BuildInfo.version()).
                setHeader("Authorization", "Bearer " + clientCredentials.getAccessToken());
        return request.getBody().map(builder::setBody).orElse(builder).build();
    }

    @Override
    public void close() {
        clientCredentials.close();
        asyncHttpClient.close();
    }
}
