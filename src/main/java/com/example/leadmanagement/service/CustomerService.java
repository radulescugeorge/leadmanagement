/**
 * I do have more loyalty cards than customers. We need to implement a method that when a card is replaced,
 * the old one should be deleted.
 * <p>
 * [SOLVED:] I put a condition in Controller and if one of the fields are empty
 * Also, PATCH method (replace customer) should work if only all the fields in the DTO are completed
 * (including loyalty card). If you need to modify only one field, should use update instead.
 */
package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.CustomerDto;
import com.example.leadmanagement.mapper.impl.CustomerMapper;
import com.example.leadmanagement.mapper.impl.LoyaltyCardMapper;
import com.example.leadmanagement.persistence.entity.Customer;
import com.example.leadmanagement.persistence.entity.LoyaltyCard;
import com.example.leadmanagement.persistence.repository.CustomerRepository;
import com.example.leadmanagement.persistence.repository.LoyaltyCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LoyaltyCardRepository loyaltyCardRepository;
    private final CustomerMapper customerMapper;
    private final LoyaltyCardMapper loyaltyCardMapper;


    public CustomerService(CustomerRepository customerRepository,
                           LoyaltyCardRepository loyaltyCardRepository,
                           CustomerMapper customerMapper, LoyaltyCardMapper loyaltyCardMapper) {
        this.customerRepository = customerRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.customerMapper = customerMapper;
        this.loyaltyCardMapper = loyaltyCardMapper;
    }

    public CustomerDto createCustomerWithLoyaltyCard(CustomerDto customerDto) {

        Customer customer = customerMapper.mapToEntity(customerDto);

        LoyaltyCard loyaltyCard = loyaltyCardMapper.mapToEntity(customerDto.getLoyaltyCard());
        LoyaltyCard savedLoyaltyCard = loyaltyCardRepository.save(loyaltyCard);

        customer.setLoyaltyCard(savedLoyaltyCard);
        savedLoyaltyCard.setCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDto savedCustomerDto = customerMapper.mapToDto(savedCustomer);
        savedCustomerDto.setLoyaltyCard(loyaltyCardMapper.mapToDto(savedLoyaltyCard));

        return savedCustomerDto;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::mapToDto)
                .toList();
    }

    public List<CustomerDto> getCustomersByName(String name){
        List<Customer> list = customerRepository
                .findByNameContainingIgnoreCase(name);
        return list.stream()
                .map(customerMapper::mapToDto)
                .toList();
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void deleteCustomerById(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        if (customer.getLoyaltyCard() != null) {
            loyaltyCardRepository.delete(customer.getLoyaltyCard());
        }

        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(long id, CustomerDto customerDto) {

        // find the customer by the id provided. If not found say so.
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        // if found, check every field in DTO . If it's not null, then update.
        if (customerDto.getName() != null) customer.setName(customerDto.getName());
        if (customerDto.getCity() != null) customer.setCity(customerDto.getCity());
        if (customerDto.getPhone() != null) customer.setPhone(customerDto.getPhone());
        if (customerDto.getEmail() != null) customer.setEmail(customerDto.getEmail());


        //Check the DTO in the request if LoyaltyCard is null. If it's not null then update.
        if (customerDto.getLoyaltyCard() != null) {
            LoyaltyCard loyaltyCard = customer.getLoyaltyCard();
            if (loyaltyCard == null) {
                loyaltyCard = new LoyaltyCard();
                customer.setLoyaltyCard(loyaltyCard);
                loyaltyCard.setCustomer(customer);

            }

            if (customerDto.getLoyaltyCard().getSerialNumber() != null) {
                loyaltyCard.setSerialNumber(customerDto.getLoyaltyCard().getSerialNumber());
            }
            loyaltyCard.setDiscount(customerDto.getLoyaltyCard().getDiscount());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.mapToDto(updatedCustomer);
    }

    public CustomerDto replaceCustomer(long id, CustomerDto customerDto) {

        //search the customer. If not found say so.
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        //then replace all fields.
        customer.setName(customerDto.getName());
        customer.setCity(customerDto.getCity());
        customer.setPhone(customerDto.getPhone());
        customer.setEmail(customerDto.getEmail());

        LoyaltyCard loyaltyCard = loyaltyCardMapper.mapToEntity(customerDto.getLoyaltyCard());
        loyaltyCard.setCustomer(customer);
        customer.setLoyaltyCard(loyaltyCard);

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.mapToDto(updatedCustomer);

    }

}
