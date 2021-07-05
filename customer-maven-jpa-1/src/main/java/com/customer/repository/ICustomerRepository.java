package com.customer.repository;

import com.customer.model.Customer;
import com.customer.model.Province;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICustomerRepository extends PagingAndSortingRepository<Customer,Long> {
    Iterable<Customer> findAllByProvince(Province province);
}
