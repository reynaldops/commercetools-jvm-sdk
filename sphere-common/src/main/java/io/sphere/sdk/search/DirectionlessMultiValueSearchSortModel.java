package io.sphere.sdk.search;

import static io.sphere.sdk.search.SearchSortDirection.*;

/**
 * A sort model to decide the direction of a model with multiple values per entity.
 */
public class DirectionlessMultiValueSearchSortModel<T> extends DirectionlessSearchSortModelImpl<T> {

    public DirectionlessMultiValueSearchSortModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    /**
     * When the sort direction is ascending, the minimum value is used.
     * @return the ascending sort direction
     */
    @Override
    public SearchSort<T> byAsc() {
        return super.byAsc();
    }

    /**
     * When the direction is descending, the maximum value is used.
     * @return the descending sort direction
     */
    @Override
    public SearchSort<T> byDesc() {
        return super.byDesc();
    }

    /**
     * Changes the default behaviour of the ascending sort by using the maximum value instead.
     * @return the ascending sort direction using the maximum value
     */
    public SearchSort<T> byAscWithMax() {
        return by(ASC_MAX);
    }

    /**
     * Changes the default behaviour of the descending sort by using the minimum value instead.
     * @return the descending sort direction using the minimum value
     */
    public SearchSort<T> byDescWithMin() {
        return by(DESC_MIN);
    }
}
