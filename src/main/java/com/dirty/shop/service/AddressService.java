package com.dirty.shop.service;

import com.dirty.shop.dto.request.AddressRequest;
import com.dirty.shop.dto.request.FindAddressRequest;
import com.dirty.shop.model.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    String save(AddressRequest request);

    Page<Address> findAll(FindAddressRequest request);

    String update(Long id, AddressRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    Address findById(Long id);
}
