package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@ResourceValue
@JsonDeserialize(as = SyncInfoImpl.class)
public interface SyncInfo {
    static SyncInfo of(final Referenceable<Channel> channel, final ZonedDateTime syncedAt, @Nullable final String externalId) {
        return new SyncInfoImpl(channel.toReference(), externalId, syncedAt);

    }

    Reference<Channel> getChannel();

    @Nullable
    String getExternalId();

    ZonedDateTime getSyncedAt();
}
