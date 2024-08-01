package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.AddressRequest;
import com.dirty.shop.dto.request.FindAddressRequest;
import com.dirty.shop.model.Address;
import com.dirty.shop.model.User;
import com.dirty.shop.repository.AddressRepository;
import com.dirty.shop.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address save(AddressRequest request) {
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Address address = Address.builder()
                .userId(userPrincipal.getId())
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

        if (Boolean.TRUE.equals(request.getUserOnly())) {
            User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            request.setUserId(userPrincipal.getId());
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

        addressRepository.save(address);
        return "Update address successful";
    }

    @Override
    public String delete(Long id) {
        Address address = addressRepository.findById(id).orElseThrow();
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
        return addressRepository.findById(id).orElseThrow();
    }
}
