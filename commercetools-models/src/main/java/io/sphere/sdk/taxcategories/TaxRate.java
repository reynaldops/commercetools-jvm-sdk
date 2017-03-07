package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@ResourceValue
@JsonDeserialize(as=TaxRateImpl.class)
public interface TaxRate {
    @Nullable
    String getId();

    String getName();

    Double getAmount();

    @JsonProperty("includedInPrice")
    Boolean isIncludedInPrice();

    CountryCode getCountry();

    @Nullable
    String getState();

    @Nonnull
    List<SubRate> getSubRates();
}
