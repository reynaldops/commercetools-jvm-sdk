package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;

final class ShippingMethodsByCartGetImpl extends MetaModelGetDslImpl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGetImpl, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodsByCartGet {
    ShippingMethodsByCartGetImpl(final String cartId) {
        super("?cartId=" + cartId, ShippingMethodEndpoint.ENDPOINT.withTypeReference(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }), ShippingMethodExpansionModel.of(), ShippingMethodsByCartGetImpl::new);
    }

    public ShippingMethodsByCartGetImpl(MetaModelGetDslBuilder<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGetImpl, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
