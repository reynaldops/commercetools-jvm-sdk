package io.sphere.sdk.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;

/**
 * Infos about availability.
 *
 * @see AvailabilityInfoBuilder
 */
@ResourceValue
@JsonDeserialize(as = AvailabilityInfoImpl.class)
public interface AvailabilityInfo {
    @JsonProperty("isOnStock")
    @Nullable
    Boolean isOnStock();

    @Nullable
    Integer getRestockableInDays();

    Long getAvailableQuantity();
}
