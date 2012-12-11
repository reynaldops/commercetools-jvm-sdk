package de.commercetools.internal;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import de.commercetools.sphere.client.filters.expressions.FilterType;

import java.math.BigDecimal;

/** Default values of some constants. */
public class Defaults {
    /** Default page size when paging through results. */
    public static final int pageSize = 25;

    /** Amount of time indication that an OAuth token is about to expire and should be refreshed.
     *  See {@link de.commercetools.internal.oauth.ShopClientCredentials}. */
    public static final long tokenAboutToExpireMs = 60*1000L;  // 1 minute

    /** Size of the chunk that should be enough to fetch all categories from the backend.
     *  See {@link CategoryTreeImpl}. */
    public static final int maxNumberOfCategoriesToFetchAtOnce = 20000;

    /** Used to associate value facet's values to individual facets it creates.
     *  This string should never appear in attribute names. */
    public static final String valueFacetAliasSeparator = "-:-";

    /** Filter type used if not explicitly specified. */
    public static final FilterType filterType = FilterType.RESULTS_AND_FACETS;
}
