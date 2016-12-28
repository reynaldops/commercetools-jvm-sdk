package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;

final class ProductVariantAvailabilityFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ProductVariantAvailabilityFilterSearchModel<T>, ChannelProductVariantAvailabilityFilterSearchModel<T> {

    ProductVariantAvailabilityFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, Boolean> isOnStock() {
        return booleanSearchModel("isOnStock").filtered();
    }

    @Override
    public RangeTermFilterSearchModel<T, BigDecimal> availableQuantity() {
        return numberSearchModel("availableQuantity").filtered();
    }

    @Override
    public ChannelsProductVariantAvailabilityFilterSearchModel<T> channels() {
        return new ChannelsProductVariantAvailabilityFilterSearchModelImpl<>(this, "channels");
    }

    @Override
    public FilterSearchModel<T, String> isOnStockInChannels() {
        return stringSearchModel("isOnStockInChannels").filtered();
    }
}
