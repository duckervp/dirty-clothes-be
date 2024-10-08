package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.*;
import com.dirty.shop.enums.Role;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.Address;
import com.dirty.shop.model.District;
import com.dirty.shop.model.Province;
import com.dirty.shop.model.Ward;
import com.dirty.shop.repository.AddressRepository;
import com.dirty.shop.repository.DistrictRepository;
import com.dirty.shop.repository.ProvinceRepository;
import com.dirty.shop.repository.WardRepository;
import com.dirty.shop.service.AddressService;
import com.dirty.shop.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final ProvinceRepository provinceRepository;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    @Override
    public List<Province> findAllProvinces(ProvinceRequest request) {
        return provinceRepository.findProvince(request);
    }

    @Override
    public List<District> findAllDistricts(DistrictRequest request) {
        return districtRepository.findDistrict(request);
    }

    @Override
    public List<Ward> findAllWards(WardRequest request) {
        return wardRepository.findWard(request);
    }

    @Override
    public Address save(AddressRequest request) {
        Address address = Address.builder()
                .userId(AuthUtils.currentUserId())
                .detailAddress(request.getDetailAddress())
                .phone(request.getPhone())
                .postalCode(request.getPostalCode())
                .note(request.getNote())
                .name(request.getName())
                .shippingInfo(request.getShippingInfo())
                .build();

        addressRepository.save(address);
        return address;
    }

    @Override
    public Page<Address> findAll(FindAddressRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);

        if (Boolean.TRUE.equals(request.getAll())) {
            pageable = Pageable.unpaged(sort);
        }

        boolean setUserId = Role.USER.equals(AuthUtils.currentRole())
                || (Role.ADMIN.equals(AuthUtils.currentRole()) && Boolean.TRUE.equals(request.getUserOnly()));
        if (setUserId) {
            request.setUserId(AuthUtils.currentUserId());
        }

        return addressRepository.findAddress(request.getUserId(), pageable);
    }

    @Override
    public String update(Long id, AddressRequest request) {
        Address address = addressRepository.findById(id).orElseThrow();
        address.setDetailAddress(request.getDetailAddress());
        address.setPhone(request.getPhone());
        address.setPostalCode(request.getPostalCode());
        address.setName(request.getName());
        address.setNote(request.getNote());

        if (Role.USER.equals(AuthUtils.currentRole()) && !address.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

        addressRepository.save(address);
        return "Update address successful";
    }

    @Override
    public String delete(Long id) {
        Address address = addressRepository.findById(id).orElseThrow();

        if (Role.USER.equals(AuthUtils.currentRole()) && !address.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

        address.setDeleted(true);

        addressRepository.save(address);
        return "Delete address successful";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Address> addresses = addressRepository.findAllById(ids);
        addresses.forEach(e -> e.setDeleted(true));

        addressRepository.saveAll(addresses);
        return "Delete list addresses successful";
    }

    @Override
    public Address findById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow();

        if (Role.USER.equals(AuthUtils.currentRole()) && !address.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

        return address;
    }
}
