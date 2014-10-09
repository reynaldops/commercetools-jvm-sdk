package io.sphere.sdk.meta;


import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.PartialProductCatalogDataQueryModel;
import io.sphere.sdk.products.queries.ProductDataQueryModel;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.Sort;
import io.sphere.sdk.queries.SortDirection;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.queries.SortDirection.ASC;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;

public class QueryDocumentationTest {

    public void queryForAllDemo() {
        final ProductQuery query = new ProductQuery();
    }

    public void queryBySlug() {
        final QueryDsl<Product> queryBySlug = new ProductQuery()
                .bySlug(CURRENT, ENGLISH, "blue-t-shirt");
    }

    public void queryByNames() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    public void queryByNamesDesugared() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    @Test
    public void testX() throws Exception {
        final Predicate<Product> safePredicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final Predicate<Product> unsafePredicate =
                Predicate.of("masterData(current(name(en in (\"blue t-shirt\", \"blue jeans\"))))");
        assertThat(unsafePredicate).isEqualTo(safePredicate);
    }

    public void predicateOrExample() {
        final Predicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Predicate<Product> idIsBar = ProductQuery.model().id().is("bar");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFoo.or(idIsBar));
    }

    public void predicateAndExample() {
        final Predicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Reference<Category> cat1 = Category.reference("cat1");
        final Predicate<Product> isInCat1 = ProductQuery.model().masterData().current()
                .categories().isIn(cat1);
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFoo.and(isInCat1));
    }

    public void predicateAndWithWhereExample() {
        final Reference<Category> cat1 = Category.reference("cat1");
        final Predicate<Product> nameIsFooAndIsInCat1 = ProductQuery.model().masterData().current()
                .where(cur -> cur.name().lang(ENGLISH).is("foo").and(cur.categories().isIn(cat1)));
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFooAndIsInCat1);
    }

    public void predicateNotExample() {
        final Predicate<Product> nameIsNotFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isNot("foo");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsNotFoo);
    }

    public void sortByName() {
        final Sort<Product> byNameAsc = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final QueryDsl<Product> query = new ProductQuery().withSort(asList(byNameAsc));
    }

    @Test
    public void sortCreationByString() {
        final Sort<Product> safeSort = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final Sort<Product> unsafeSort = Sort.of("masterData.current.name.en asc");
        assertThat(safeSort).isEqualTo(unsafeSort);
    }
}