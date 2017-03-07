package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = DiscountedLineItemPriceForQuantityImpl.class)
public interface DiscountedLineItemPriceForQuantity {
    static DiscountedLineItemPriceForQuantity of(final DiscountedLineItemPrice discountedLineItemPrice, final long quantity) {
        return new DiscountedLineItemPriceForQuantityImpl(discountedLineItemPrice, quantity);
    }

    DiscountedLineItemPrice getDiscountedPrice();

    Long getQuantity();
}
