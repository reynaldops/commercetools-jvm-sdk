package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.*;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFilterSearchModel<T> extends ProductVariantAvailabilityFilterSearchModelCommon<T> {

    @Override
    TermFilterSearchModel<T, Boolean> isOnStock();

    @Override
    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();

    ChannelsProductVariantAvailabilityFilterSearchModel<T> channels();

    FilterSearchModel<T, String> isOnStockInChannels();
    // TODO A method like this, through channels() or a method that accepts only channel IDs?
}
