package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.money.MonetaryAmount;
import java.util.List;

@ResourceValue
@JsonDeserialize(as = DiscountedLineItemPriceImpl.class)
public interface DiscountedLineItemPrice {
    static DiscountedLineItemPrice of(final MonetaryAmount money, final List<DiscountedLineItemPortion> includedDiscounts) {
        return new DiscountedLineItemPriceImpl(includedDiscounts, money);
    }

    List<DiscountedLineItemPortion> getIncludedDiscounts();

    MonetaryAmount getValue();
}
