package com.example.demo.converter;

import com.example.demo.dto.ShippingAddressDto;
import com.example.demo.models.ShippingAddress;

public class ShippingAddressConverter {

    public static ShippingAddress ShippingAddressDtoShippingAddress(ShippingAddressDto shippingAddressDto){
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId( shippingAddressDto.getId());
        shippingAddress.setCity( shippingAddressDto.getCity());
        shippingAddress.setStreet( shippingAddressDto.getStreet());
        shippingAddress.setNumberStreet( shippingAddressDto.getNumberStreet());
        shippingAddress.setCountry( shippingAddressDto.getCountry());
        shippingAddress.setZipCode( shippingAddressDto.getZipCode());

        return shippingAddress;
    }
}
