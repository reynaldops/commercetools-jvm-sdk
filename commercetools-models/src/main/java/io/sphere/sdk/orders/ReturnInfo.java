package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@ResourceValue
@JsonDeserialize(as = ReturnInfoImpl.class)
public interface ReturnInfo {
    static ReturnInfo of(final List<ReturnItem> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        return new ReturnInfoImpl(items, returnDate, returnTrackingId);
    }

    List<ReturnItem> getItems();

    @Nullable
    String getReturnTrackingId();

    @Nullable
    ZonedDateTime getReturnDate();
}
