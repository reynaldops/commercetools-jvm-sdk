package io.sphere.sdk.meta;

public final class BuildInfo {
    private static final String version = "1.6.1-OSGi-SNAPSHOT";
    private static final String userAgent = "commercetools-jvm-sdk/" + version;

    private BuildInfo() {
        //utility class
    }

    public static String userAgent() {
        return userAgent;
    }

    public static String version() {
        return version;
    }
}
