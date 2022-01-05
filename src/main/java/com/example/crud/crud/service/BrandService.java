package com.example.crud.crud.service;

import com.example.crud.crud.entity.Brand;
import com.example.crud.crud.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public List<Brand> getBrandByFilter(String filter) {
        return brandRepository.search(filter);
    }

    public void addBrand(Brand brand) {
        brandRepository.save(brand);
    }
    public void deleteBrand(Brand brand){
        brandRepository.delete(brand);
    }


}
