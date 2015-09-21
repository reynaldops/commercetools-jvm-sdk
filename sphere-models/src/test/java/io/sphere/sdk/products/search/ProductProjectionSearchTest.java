package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static java.math.BigDecimal.valueOf;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchTest {
    private static final ProductProjectionSearchModel MODEL = ProductProjectionSearchModel.of();

    @Test
    public void canAccessProductName() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.name().locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("name.en");
        assertThat(path.filtered().by("foo").toSearchExpression()).isEqualTo("name.en:\"foo\"");
        assertThat(path.sorted().byAsc().toSphereSort()).isEqualTo("name.en asc");
    }

    @Test
    public void canAccessCreatedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.createdAt();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("createdAt");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSearchExpression()).isEqualTo("createdAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted().byDesc().toSphereSort()).isEqualTo("createdAt desc");
    }

    @Test
    public void canAccessLastModifiedAt() throws Exception {
        final DateTimeSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.lastModifiedAt();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("lastModifiedAt");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSearchExpression()).isEqualTo("lastModifiedAt:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted().byAsc().toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void canAccessCategories() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.categories().id();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("categories.id");
        assertThat(path.filtered().by("some-id").toSearchExpression()).isEqualTo("categories.id:\"some-id\"");
    }

    @Test
    public void canAccessProductType() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.productType().id();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("productType.id");
        assertThat(path.filtered().by("some-id").toSearchExpression()).isEqualTo("productType.id:\"some-id\"");
    }

    @Test
    public void canAccessPriceAmount() throws Exception {
        final MoneyCentAmountSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.allVariants().price().centAmount();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.price.centAmount");
        assertThat(path.filtered().by(1000L).toSearchExpression()).isEqualTo("variants.price.centAmount:1000");
        assertThat(path.sorted().byDesc().toSphereSort()).isEqualTo("price desc");
    }

    @Test
    public void canAccessPriceCurrency() throws Exception {
        final CurrencySearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.allVariants().price().currency();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.price.currencyCode");
        assertThat(path.filtered().by(currency("EUR")).toSearchExpression()).isEqualTo("variants.price.currencyCode:\"EUR\"");
        assertThat(path.sorted().byAsc().toSphereSort()).isEqualTo("variants.price.currencyCode asc");
    }

    @Test
    public void canAccessTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofString("brand");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.brand");
        assertThat(path.filtered().by("Apple").toSearchExpression()).isEqualTo("variants.attributes.brand:\"Apple\"");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.brand asc.max");
    }

    @Test
    public void canAccessLocTextCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofLocalizedString("material").locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.material.en");
        assertThat(path.filtered().by("steel").toSearchExpression()).isEqualTo("variants.attributes.material.en:\"steel\"");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.material.en desc.min");
    }

    @Test
    public void canAccessBooleanCustomAttributes() throws Exception {
        final BooleanSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofBoolean("isHandmade");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.isHandmade");
        assertThat(path.filtered().by(true).toSearchExpression()).isEqualTo("variants.attributes.isHandmade:true");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.isHandmade asc.max");
    }

    @Test
    public void canAccessNumberCustomAttributes() throws Exception {
        final NumberSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofNumber("length");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.length");
        assertThat(path.filtered().by(valueOf(4)).toSearchExpression()).isEqualTo("variants.attributes.length:4");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.length desc.min");
    }

    @Test
    public void canCreateDateAttributeExpressions() throws Exception {
        final DateSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofDate("expirationDate");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.expirationDate");
        assertThat(path.filtered().by(date("2001-09-11")).toSearchExpression()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateTimeAttributeExpressions() throws Exception {
        final TimeSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofTime("deliveryHours");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.deliveryHours");
        assertThat(path.filtered().by(time("22:05:09.203")).toSearchExpression()).isEqualTo("variants.attributes.deliveryHours:\"22:05:09.203\"");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.deliveryHours desc.min");
    }

    @Test
    public void canCreateDateTimeAttributeExpressions() throws Exception {
        final DateTimeSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofDateTime("createdDate");
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.createdDate");
        assertThat(path.filtered().by(dateTime("2001-09-11T22:05:09.203+00:00")).toSearchExpression()).isEqualTo("variants.attributes.createdDate:\"2001-09-11T22:05:09.203Z\"");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.createdDate asc.max");
    }

    @Test
    public void canAccessEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofEnum("originCountry").key();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.originCountry.key");
        assertThat(path.filtered().by("Italy").toSearchExpression()).isEqualTo("variants.attributes.originCountry.key:\"Italy\"");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.originCountry.key desc.min");
    }

    @Test
    public void canAccessMoneyCentAmountCustomAttributes() throws Exception {
        final MoneyCentAmountSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofMoney("originalPrice").centAmount();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.originalPrice.centAmount");
        assertThat(path.filtered().by(1000L).toSearchExpression()).isEqualTo("variants.attributes.originalPrice.centAmount:1000");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.originalPrice.centAmount asc.max");
    }

    @Test
    public void canAccessCurrencyCustomAttributes() throws Exception {
        final CurrencySearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofMoney("originalPrice").currency();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.originalPrice.currencyCode");
        assertThat(path.filtered().by(currency("EUR")).toSearchExpression()).isEqualTo("variants.attributes.originalPrice.currencyCode:\"EUR\"");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.originalPrice.currencyCode desc.min");
    }

    @Test
    public void canAccessReferenceCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofReference("recommendedProduct").id();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.recommendedProduct.id");
        assertThat(path.filtered().by("some-id").toSearchExpression()).isEqualTo("variants.attributes.recommendedProduct.id:\"some-id\"");
    }

    @Test
    public void canAccessEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofEnum("originCountry").label();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.originCountry.label");
        assertThat(path.filtered().by("Italy").toSearchExpression()).isEqualTo("variants.attributes.originCountry.label:\"Italy\"");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.originCountry.label asc.max");
    }

    @Test
    public void canAccessLocEnumKeyCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofLocalizableEnum("color").key();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.color.key");
        assertThat(path.filtered().by("ROT").toSearchExpression()).isEqualTo("variants.attributes.color.key:\"ROT\"");
        assertThat(path.sorted().byDescWithMin().toSphereSort()).isEqualTo("variants.attributes.color.key desc.min");
    }

    @Test
    public void canAccessLocEnumLabelCustomAttributes() throws Exception {
        final StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofLocalizableEnum("color").label().locale(ENGLISH);
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.color.label.en");
        assertThat(path.filtered().by("red").toSearchExpression()).isEqualTo("variants.attributes.color.label.en:\"red\"");
        assertThat(path.sorted().byAsc().toSphereSort()).isEqualTo("variants.attributes.color.label.en asc");
    }

    @Test
    public void usesAlias() throws Exception {
        final NumberSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofNumber("size");
        assertThat(path.faceted().withAlias("my-facet").byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(path.faceted().withAlias("my-facet").byTerm(valueOf(38)).toSearchExpression()).isEqualTo("variants.attributes.size:38 as my-facet");
        assertThat(path.faceted().withAlias("my-facet").byLessThan(valueOf(38)).toSearchExpression()).isEqualTo("variants.attributes.size:range(* to 38) as my-facet");
    }

    @Test
    public void unicode() throws Exception {
        final String path = ProductProjectionSearch.ofStaged().withText(GERMAN, "öón").httpRequestIntent().getPath();
        final String expected = "/product-projections/search?text.de=%C3%B6%C3%B3n&staged=true";
        assertThat(path).isEqualTo(expected);
    }

    @Test
    public void canCreateUntypedExpressionsFromVariantAttributes() throws Exception {
        final UntypedSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> path = attributeModel().ofDate("expirationDate").untyped();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("variants.attributes.expirationDate");
        assertThat(path.filtered().by("2001-09-11").toSearchExpression()).isEqualTo("variants.attributes.expirationDate:\"2001-09-11\"");
        assertThat(path.sorted().byAscWithMax().toSphereSort()).isEqualTo("variants.attributes.expirationDate asc.max");
    }

    @Test
    public void canCreateUntypedExpressionsFromProductAttributes() throws Exception {
        final UntypedSearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> path = MODEL.lastModifiedAt().untyped();
        assertThat(path.faceted().byAllTerms().toSearchExpression()).isEqualTo("lastModifiedAt");
        assertThat(path.filtered().by("2001-09-11").toSearchExpression()).isEqualTo("lastModifiedAt:\"2001-09-11\"");
        assertThat(path.sorted().byAsc().toSphereSort()).isEqualTo("lastModifiedAt asc");
    }

    @Test
    public void generatesUntypedModelFromTermModel() throws Exception {
        final CurrencySearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> attributePath = attributeModel().ofMoney("money").currency();
        assertThat(attributePath.untyped().buildPath()).isEqualTo(attributePath.buildPath());
    }

    @Test
    public void generatesUntypedModelFromRangeModel() throws Exception {
        final MoneyCentAmountSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> attributePath = attributeModel().ofMoney("money").centAmount();
        assertThat(attributePath.untyped().buildPath()).isEqualTo(attributePath.buildPath());
    }

    private ProductAttributeSearchModel attributeModel() {
        return MODEL.allVariants().attribute();
    }

    private ZonedDateTime dateTime(final String dateTime) {
        return ZonedDateTime.parse(dateTime);
    }

    private LocalTime time(final String time) {
        return LocalTime.parse(time);
    }

    private LocalDate date(final String date) {
        return LocalDate.parse(date);
    }

    private CurrencyUnit currency(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }
}
