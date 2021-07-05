package com.customer.service.customer;

import com.customer.model.Customer;
import com.customer.model.Province;
import com.customer.service.IGeneralService;

public interface ICustomerService extends IGeneralService<Customer> {
    Iterable<Customer> findAllByProvince(Province province);
}