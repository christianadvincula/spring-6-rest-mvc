package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    public List<Customer> getCustomers() {
        log.debug("getCustomers of CustomerController");
        return customerService.getCustomers();
    }

    @GetMapping(value = "{id}")
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        log.debug("getCustomerById of CustomerController");
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity saveCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/"+savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateById(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        if(customerService.getCustomerById(id) != null) {
            customerService.updateById(id, customer);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        if(customerService.getCustomerById(id) != null) {
            customerService.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity patchById(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        if(customerService.getCustomerById(id) != null) {
            customerService.patchById(id, customer);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
