package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = SuggestionImpl.class)
public interface Suggestion {
    String getText();

    @JsonIgnore
    static Suggestion of(final String text) {
        return new SuggestionImpl(text);
    }
}
