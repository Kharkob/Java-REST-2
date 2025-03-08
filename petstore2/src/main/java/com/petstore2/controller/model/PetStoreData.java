

package com.petstore2.controller.model;



import java.util.Set;
import java.util.HashSet;

import com.petstore2.entity.Customer;
import com.petstore2.entity.Employee;
import com.petstore2.entity.PetStore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreData { 
    private Long petStoreId;
    private String petStoreName;
    private String petStoreAddress;
    private String petStoreCity;
    private String petStoreState;
    private String petStoreZip;
    private String petStorePhone;
    private Set <PetStoreCustomer>  customers = new HashSet<> ();
    private  Set <PetStoreEmployee> employees = new HashSet<>();

    public PetStoreData(PetStore petStore)  {
        this.petStoreId = petStore.getPetStoreId();
        this.petStoreName = petStore.getPetStoreName();
        this.petStoreAddress = petStore.getPetStoreAddress();
        this.petStorePhone = petStore.getPetStorePhone();
        this.petStoreCity =  petStore.getPetStoreCity();
        this.petStoreZip = petStore.getPetStoreZip();
        this.petStoreState = petStore.getPetStoreState();
        for (Customer customer:petStore.getCustomers()) {
            customers.add(new PetStoreCustomer(customer));
        }
        for (Employee employee:petStore.getEmployees()) {
            employees.add(new PetStoreEmployee(employee));
    }
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
        private Long customerId;
        private String customerFirstName;
        private String customerLastName;
        private String customerEmail;

        public PetStoreCustomer(Customer customer) {
            this.customerId = customer.getCustomerId();
            this.customerFirstName = customer.getCustomerLastName();
            this.customerLastName = customer.getCustomerFirstName();
            this.customerEmail = customer.getCustomerEmail();
        }
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
        private Long employeeId;
        private String employeeFirstName;
        private String employeeLastName;
        private String employeePhone;
        private String employeeJobTitle;

        public PetStoreEmployee(Employee employee) {
            this.employeeId = employee.getEmployeeId();
            this.employeeFirstName = employee.getEmployeeFirstName();
            this.employeeLastName = employee.getEmployeeLastName();
            this.employeePhone = employee.getEmployeePhone();
            this.employeeJobTitle = employee.getEmployeeJobTitle();
        }
    }
}
