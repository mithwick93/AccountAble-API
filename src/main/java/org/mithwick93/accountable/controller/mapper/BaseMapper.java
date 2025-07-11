package org.mithwick93.accountable.controller.mapper;

import org.mithwick93.accountable.cache.UserCache;
import org.mithwick93.accountable.controller.dto.response.UserResponse;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.model.enums.AssetType;
import org.mithwick93.accountable.model.enums.Currency;
import org.mithwick93.accountable.model.enums.LiabilityType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper {

    @Autowired
    private UserCache userCache;

    @Autowired
    private UserMapper userMapper;

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
        if (currency == null) {
            return null;
        }
        return currency.name();
    }

    protected UserResponse mapUser(int userId) {
        User user = userCache.getUser(userId);
        return userMapper.toUserResponse(user);
    }

}
