package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void updateById(UUID id, Customer customer);

    void deleteById(UUID id);

    void patchById(UUID id, Customer customer);
}
