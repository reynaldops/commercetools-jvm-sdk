package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * A SubRate is used to calculate the tax portions field in a cart or order.
 * It is useful if the total tax of a country is a combination of multiple taxes (e.g. state and local taxes)
 */
@ResourceValue
@JsonDeserialize(as = SubRateImpl.class)
public interface SubRate {
    String getName();

    Double getAmount();

    static SubRate of(final String name, final Double amount) {
        return new SubRateImpl(amount, name);
    }
}
