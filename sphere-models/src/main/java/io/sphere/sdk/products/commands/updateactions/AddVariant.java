package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductUpdateScope;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Adds a variant to a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addVariant()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveVariant
 */
public class AddVariant extends StageableProductUpdateAction {
    @Nullable
    private final String sku;
    private final List<Price> prices;
    private final List<AttributeDraft> attributes;

    private AddVariant(final List<AttributeDraft> attributes, final List<Price> prices, @Nullable final String sku, final ProductUpdateScope productUpdateScope) {
        super("addVariant", productUpdateScope);
        this.attributes = attributes;
        this.prices = prices;
        this.sku = sku;
    }

    public List<AttributeDraft> getAttributes() {
        return attributes;
    }

    public List<Price> getPrices() {
        return prices;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<Price> prices, @Nullable final String sku, final ProductUpdateScope productUpdateScope) {
        return new AddVariant(attributes, prices, sku, productUpdateScope);
    }

    public static AddVariant of(final List<AttributeDraft> attributes, final List<Price> prices, final ProductUpdateScope productUpdateScope) {
        return new AddVariant(attributes, prices, null, productUpdateScope);
    }
}