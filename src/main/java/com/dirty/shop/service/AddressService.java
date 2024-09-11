package com.dirty.shop.service;

import com.dirty.shop.dto.request.*;
import com.dirty.shop.model.Address;
import com.dirty.shop.model.District;
import com.dirty.shop.model.Province;
import com.dirty.shop.model.Ward;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    Address save(AddressRequest request);

    Page<Address> findAll(FindAddressRequest request);

    String update(Long id, AddressRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    Address findById(Long id);

    List<Province> findAllProvinces(ProvinceRequest request);

    List<District> findAllDistricts(DistrictRequest request);

    List<Ward> findAllWards(WardRequest request);
}
