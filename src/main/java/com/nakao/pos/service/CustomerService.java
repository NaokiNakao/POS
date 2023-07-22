package com.nakao.pos.service;

import com.nakao.pos.dao.CustomerDAO;
import com.nakao.pos.exception.CustomerDeletionException;
import com.nakao.pos.exception.CustomerNotFoundException;
import com.nakao.pos.exception.UniqueIdentifierGenerationException;
import com.nakao.pos.model.Customer;
import com.nakao.pos.repository.CustomerRepository;
import com.nakao.pos.repository.OrderRepository;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/22/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDAO customerDAO;
    private final OrderRepository orderRepository;

    public List<Customer> getCustomers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.getContent();
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
    }

    public Customer createCustomer(Customer customer) {
        customer.setId(IdentifierGenerator.generateIdentifier(Customer.ID_PATTERN));

        if (!customerRepository.existsById(customer.getId())) {
            customerDAO.insert(customer);
            return customer;
        } else {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Customer");
        }
    }

    public Customer updateCustomer(String id, Customer customer) {
        Customer updatedCustomer = getCustomerById(id);
        BeanUtils.copyProperties(customer, updatedCustomer);
        updatedCustomer.setId(id);
        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(String id) {
        if (isValidCustomerDeletion(getCustomerById(id).getId())) {
            customerRepository.deleteById(id);
        }
        else {
            throw new CustomerDeletionException("Unable to delete Customer with ID: " + id);
        }
    }

    private Boolean isValidCustomerDeletion(String id) {
        return orderRepository.countByCustomerId(id) == 0;
    }

}
