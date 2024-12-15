package org.mithwick93.accountable.controller.mapper;

import org.mithwick93.accountable.cache.UserCache;
import org.mithwick93.accountable.controller.dto.response.UserResponse;
import org.mithwick93.accountable.model.AssetType;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.LiabilityType;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCache userCache;

    protected AssetType mapAssetTypeString(String type) {
        return AssetType.valueOf(type.toUpperCase());
    }

    protected String mapAssetType(AssetType type) {
        return type.name();
    }

    protected LiabilityType mapLiabilityTypeString(String type) {
        return LiabilityType.valueOf(type.toUpperCase());
    }

    protected String mapLiabilityType(LiabilityType type) {
        return type.name();
    }

    protected Currency mapCurrencyString(String currency) {
        return Currency.valueOf(currency.toUpperCase());
    }

    protected String mapCurrency(Currency currency) {
        return currency.name();
    }

    protected UserResponse mapUser(int userId) {
        User user = userCache.getUser(userId);
        return userMapper.toUserResponse(user);
    }

}
