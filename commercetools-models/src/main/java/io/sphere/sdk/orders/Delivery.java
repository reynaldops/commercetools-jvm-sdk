package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.CreationTimestamped;
import io.sphere.sdk.models.Identifiable;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Deliveries are compilations of information on how the articles are being shipped to the customers. A delivery can contain multiple items. All items in a delivery can be shipped with several parcels. To create a delivery, it is necessary to have a shipment method assigned to the order. A sample use case for a delivery object is to create a delivery note.
 *
 * @see io.sphere.sdk.orders.commands.updateactions.AddDelivery
 * @see io.sphere.sdk.orders.messages.DeliveryAddedMessage
 */
@ResourceValue
@JsonDeserialize(as = DeliveryImpl.class)
public interface Delivery extends Identifiable<Delivery>, CreationTimestamped {
    @Override
    String getId();

    @Override
    ZonedDateTime getCreatedAt();

    List<DeliveryItem> getItems();

    List<Parcel> getParcels();

    static Delivery of(final String id, final ZonedDateTime createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        return new DeliveryImpl(createdAt, id, items, parcels);
    }
}
