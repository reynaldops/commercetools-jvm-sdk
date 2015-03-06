package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=CustomLineItemImpl.class)
public interface CustomLineItem extends LineItemLike {
    LocalizedStrings getName();

    MonetaryAmount getMoney();

    String getSlug();

    List<ItemState> getState();

    Reference<TaxCategory> getTaxCategory();

    Optional<TaxRate> getTaxRate();

    @Override
    String getId();

    @Override
    long getQuantity();
}
