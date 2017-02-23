package io.sphere.sdk.http.osgi.test;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.options.UrlProvisionOption;

import java.util.Arrays;
import java.util.List;

import static org.ops4j.pax.exam.CoreOptions.linkBundle;

/**
 * This helper class provides methods to configure pax exam correctly.
 */
public class PaxExamOptions {

    public static CompositeOption sdkHttpOptions() {
        final List<UrlProvisionOption> sdkHttpOptions = Arrays.asList(
                linkBundle("org.apache.httpcomponents.httpcore"),
                linkBundle("org.apache.httpcomponents.httpclient"),
                linkBundle("org.apache.httpcomponents.httpasyncclient"),
                linkBundle("org.apache.commons.io"),
                linkBundle("org.apache.commons.lang3"),
                linkBundle("org.jsr-305"),
                linkBundle("org.assertj.core"),
                linkBundle("com.commercetools.sdk.jvm.core.sdk-http"),
                linkBundle("com.commercetools.sdk.jvm.core.sdk-http-apache-async").noStart()
        );
        return new DefaultCompositeOption(sdkHttpOptions.toArray(new Option[sdkHttpOptions.size()]));
    }
}
