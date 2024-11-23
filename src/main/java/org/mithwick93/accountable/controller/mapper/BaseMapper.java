package org.mithwick93.accountable.controller.mapper;

import org.mithwick93.accountable.model.AssetType;
import org.mithwick93.accountable.model.Currency;

public class BaseMapper {

    protected AssetType mapAssetTypeString(String type) {
        return AssetType.valueOf(type.toUpperCase());
    }

    protected Currency mapCurrencyString(String currency) {
        return Currency.valueOf(currency.toUpperCase());
    }

    protected String mapType(AssetType type) {
        return type.name();
    }

    protected String mapCurrency(Currency currency) {
        return currency.name();
    }

}
