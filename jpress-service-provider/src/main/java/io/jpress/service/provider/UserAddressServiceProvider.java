package io.jpress.service.provider;

import io.jboot.aop.annotation.Bean;
import io.jboot.db.model.Column;
import io.jboot.service.JbootServiceBase;
import io.jpress.model.UserAddress;
import io.jpress.service.UserAddressService;

import java.util.List;
import java.util.Optional;

@Bean
public class UserAddressServiceProvider extends JbootServiceBase<UserAddress> implements UserAddressService {

    @Override
    public UserAddress findDefaultAddress(long userId) {
        List<UserAddress> userAddresses = findListByUserId(userId);
        if (userAddresses == null || userAddresses.isEmpty()){
            return null;
        }
        Optional<UserAddress> userAddressOptional = userAddresses.stream().filter(userAddress -> userAddress.isDefault()).findFirst();
        return userAddressOptional.isPresent() ? userAddressOptional.get() : null;
    }

    @Override
    public List<UserAddress> findListByUserId(long userId) {
        return DAO.findListByColumn(Column.create("user_id",userId));
    }
}