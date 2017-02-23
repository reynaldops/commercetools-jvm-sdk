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

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class SdkInOSGiIT {
    @Configuration
    public Option[] configure() throws IOException {
        return options(
                url("link:classpath:org.apache.httpcomponents.httpcore.link"),
                url("link:classpath:org.apache.httpcomponents.httpclient.link"),
                url("link:classpath:org.apache.httpcomponents.httpasyncclient.link"),
                url("link:classpath:org.apache.commons.io.link"),
                url("link:classpath:org.apache.commons.lang3.link"),
                url("link:classpath:org.jsr-305.link"),
                url("link:classpath:com.commercetools.sdk.jvm.core.sdk-http.link").start(),
                url("link:classpath:com.commercetools.sdk.jvm.core.sdk-http-apache-async.link").noStart(),
                junitBundles()
                );
    }

    @Test
    public void createApacheHttpClientAdapter() throws Exception {
        final CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.createDefault();
        final HttpClient httpClient = ApacheHttpClientAdapter.of(closeableHttpAsyncClient);
        assertNotNull(httpClient);
    }
}
