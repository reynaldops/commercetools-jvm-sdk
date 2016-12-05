package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.CartShippingInfo;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.zones.Zone;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Shipping Methods define where orders can be shipped and what the costs are.
 *
 *
 * @see io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommand
 * @see io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand
 * @see io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteCommand
 * @see io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery
 * @see io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdGet
 * @see CartShippingInfo#getShippingMethod()
 * @see io.sphere.sdk.orders.OrderShippingInfo#getShippingMethod()
 */
@JsonDeserialize(as = ShippingMethodImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "\n" +
        "    default ShippingMethodQuery byName(final String name) {\n" +
        "        return withPredicates(ShippingMethodQueryModel.of().name().is(name));\n" +
        "    }\n" +
        "\n" +
        "    default ShippingMethodQuery byTaxCategory(final io.sphere.sdk.models.Referenceable<io.sphere.sdk.taxcategories.TaxCategory> taxCategory) {\n" +
        "        return withPredicates(m -> m.taxCategory().is(taxCategory));\n" +
        "    }\n" +
        "\n" +
        "    default ShippingMethodQuery byIsDefault() {\n" +
        "        return withPredicates(m -> m.isDefault().is(true));\n" +
        "    }")
@ResourceInfo(pluralName = "shipping methods", pathElement = "shipping-methods")
@HasByIdGetEndpoint(javadocSummary = "Fetches a shipping method by ID.", includeExamples = "io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdGetIntegrationTest#execution()")
@HasCreateCommand(javadocSummary = "Creates a {@link io.sphere.sdk.shippingmethods.ShippingMethod}.", includeExamples = "io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandIntegrationTest#execution()")
@HasUpdateCommand
@HasDeleteCommand
@HasQueryModel
public interface ShippingMethod extends Resource<ShippingMethod> {
    String getName();

    @Nullable
    String getDescription();

    Reference<TaxCategory> getTaxCategory();

    @QueryModelHint(type = "ZoneRateCollectionQueryModel<ShippingMethod>", impl = "return new ZoneRateCollectionQueryModelImpl<>(this, fieldName);")
    List<ZoneRate> getZoneRates();

    default List<ShippingRate> getShippingRatesForZone(final Referenceable<Zone> zone) {
        return getZoneRates().stream()
                .filter(rate -> rate.getZone().hasSameIdAs(zone.toReference()))
                .findFirst()
                .map(rate -> rate.getShippingRates())
                .orElse(Collections.emptyList());
    }

    default List<Reference<Zone>> getZones() {
        return getZoneRates().stream().map(rate -> rate.getZone()).collect(toList());
    }

    @JsonProperty("isDefault")
    Boolean isDefault();

    @Override
    default Reference<ShippingMethod> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "shipping-method";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<ShippingMethod> typeReference() {
        return new TypeReference<ShippingMethod>() {
            @Override
            public String toString() {
                return "TypeReference<ShippingMethod>";
            }
        };
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<ShippingMethod> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
