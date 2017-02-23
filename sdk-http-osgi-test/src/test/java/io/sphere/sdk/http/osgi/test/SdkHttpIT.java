package io.sphere.sdk.http.osgi.test;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;

import java.io.IOException;

import static io.sphere.sdk.http.osgi.test.PaxExamOptions.sdkHttpOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class SdkHttpIT {
    @Configuration
    public Option[] configure() throws IOException {
        return options(
                sdkHttpOptions(),
                junitBundles()
        );
    }

    @Test
    public void createApacheHttpClientAdapter() throws Exception {
        final CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.createDefault();
        final HttpClient httpClient = ApacheHttpClientAdapter.of(closeableHttpAsyncClient);
        assertThat(httpClient).isNotNull();
    }
}
