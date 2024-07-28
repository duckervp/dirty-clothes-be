package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.AddressRequest;
import com.dirty.shop.dto.request.FindAddressRequest;
import com.dirty.shop.model.Address;
import com.dirty.shop.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Override
    public Address save(AddressRequest request) {
        return null;
    }

    @Override
    public Page<Address> findAll(FindAddressRequest request) {
        return null;
    }

    @Override
    public Address update(Long id, AddressRequest request) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "";
    }

    @Override
    public String delete(List<Long> ids) {
        return "";
    }

    @Override
    public Address findById(Long id) {
        return null;
    }
}
