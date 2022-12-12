package it.albx79.telepass.cusdev.customers;

import org.springframework.data.repository.CrudRepository;

public interface CustomersRepo extends CrudRepository<CustomerDto, Integer> {}
