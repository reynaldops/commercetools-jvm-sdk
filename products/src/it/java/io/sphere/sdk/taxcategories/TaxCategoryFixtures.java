package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static io.sphere.sdk.test.SphereTestUtils.*;

public final class TaxCategoryFixtures {

    public static final String STANDARD_TAX_CATEGORY = "Standard tax category";

    private TaxCategoryFixtures() {
    }

    public static void withTaxCategory(final TestClient client, final Consumer<TaxCategory> user) {
        final Predicate<TaxCategory> predicate = TaxCategoryQuery.model().name().is(STANDARD_TAX_CATEGORY);
        final List<TaxCategory> results = client.execute(new TaxCategoryQuery().withPredicate(predicate)).getResults();
        final TaxCategory taxCategory;
        if (results.isEmpty()) {
            final List<TaxRate> taxRates = asList(TaxRate.of("5% US", 0.05, false, US), TaxRate.of("19% MwSt", 0.19, true, DE));
            taxCategory = client.execute(new TaxCategoryCreateCommand(TaxCategoryDraft.of(STANDARD_TAX_CATEGORY, taxRates)));
        } else {
            taxCategory = results.get(0);
        }
        user.accept(taxCategory);
    }

    public static void withTransientTaxCategory(final TestClient client, final Consumer<TaxCategory> user) {
        withTaxCategory(client, randomString(), user);
    }

    public static void withTaxCategory(final TestClient client, final String name, final Consumer<TaxCategory> user) {
        final TaxCategoryDraft de19 = TaxCategoryDraft.of(name, asList(TaxRateBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        final PagedQueryResult<TaxCategory> results = client.execute(new TaxCategoryQuery().byName(name));
        results.getResults().forEach(tc -> client.execute(new TaxCategoryDeleteByIdCommand(tc)));
        final TaxCategory taxCategory = client.execute(new TaxCategoryCreateCommand(de19));
        user.accept(taxCategory);
        client.execute(new TaxCategoryDeleteByIdCommand(taxCategory));
    }
}