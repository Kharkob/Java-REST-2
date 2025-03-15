package com.petstore2.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petstore2.controller.model.PetStoreData;
import com.petstore2.controller.model.PetStoreData.PetStoreCustomer;
import com.petstore2.controller.model.PetStoreData.PetStoreEmployee;
import com.petstore2.dao.CustomerDao;
import com.petstore2.dao.EmployeeDao;
import com.petstore2.dao.PetStoreDao;
import com.petstore2.entity.Customer;
import com.petstore2.entity.Employee;
import com.petstore2.entity.PetStore;




@Service

public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    
    public PetStoreData savePetStore(PetStoreData petStoreData) {
       PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());


        copyPetStoreFields(petStore, petStoreData);

      
        PetStore savedPetStore = petStoreDao.save(petStore);
        return new PetStoreData(savedPetStore);
    }
//what would email be? find this in Global Error handler video 17:00



    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();  
        } else {
            return petStoreDao.findById(petStoreId)
                    .orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStoreId + " not found."));
        }
}

    private PetStore findPetStoreByID(Long petStoreId) {
        return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("PetStore with ID=" + petStoreId + "was not found."));
    }

private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
    petStore.setPetStoreName(petStoreData.getPetStoreName());
    petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
    petStore.setPetStorePhone(petStoreData.getPetStorePhone());
   
}

@Transactional(readOnly = true)
public  List<PetStoreData> retrieveAllPetStores() {
    List<PetStore> petStores = petStoreDao.findAll();
    List<PetStoreData> response = new LinkedList<>();
   
    for(PetStore petStore : petStores) {
        response.add(new PetStoreData(petStore));
    }

    return response;
}

@Transactional(readOnly = true)
public PetStoreData retrievePetStoreById(long petStoreId) {
	PetStore petStore = findPetStoreByID(petStoreId);
    return new PetStoreData(petStore);
}


@Transactional(readOnly = false)
public void deletePetStoreById(long petStoreId) {
   PetStore petStore = findPetStoreByID(petStoreId);
   petStoreDao.delete(petStore);
}


/*@Transactional
public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee employee) {
    petStore = findPetStoreByID(petStoreId) 
    employeeId =PetStoreEmployee.getEmployeeId()
    employee = findOrCreatePetStore(petStoreId, employeeId)

    copyEmployeeFields(employee, PetStoreEmployee);

   
}

private Employee findor
*/

@Autowired
private EmployeeDao employeeDao;

@Transactional(readOnly = false) 
public Employee findEmployeeById(Long petStoreId, Long employeeId) {
    Employee employee = employeeDao.findById(employeeId).orElseThrow((() -> new NoSuchElementException(" The employee with Id ="+ employeeId + "was not found")));
    
    

    
   
   
    
    if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
        throw new IllegalArgumentException("The employee does not belong to the given pet store.");
    }

    
    return employee;
}

@Transactional(readOnly = false) 
public Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
    if (employeeId == null) {
        // If employeeId is null, create and return a new Employee
        return new Employee();
    } 
        return findEmployeeById(petStoreId, employeeId);
    }



public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
    employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
    employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
    employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
    employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
}

@Transactional(readOnly = false) 
public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
  
    PetStore petStore = findPetStoreByID(petStoreId);

    Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);

    copyEmployeeFields(employee, petStoreEmployee);

    employee.setPetStore(petStore);

    petStore.getEmployees().add(employee);

    
    Employee savedEmployee = employeeDao.save(employee);

    // Convert saved Employee object to PetStoreEmployee and return it
    PetStoreEmployee savedPetStoreEmployee = new PetStoreEmployee(savedEmployee);
    return savedPetStoreEmployee;
}

  @Autowired
    private CustomerDao customerDao;
    @Transactional(readOnly = false)
    public Customer findCustomerById(Long petStoreId, Long customerId) {
        Customer customer = customerDao.findById(customerId).orElseThrow((() -> new NoSuchElementException(" The customer with Id ="+ customerId + "was not found")));;

     

        
        boolean petStoreExists = false;
        for (PetStore petStore : customer.getPetStores()) {
            if (petStore.getPetStoreId().equals(petStoreId)) {
                petStoreExists = true;
                break;
            }
        }

     
        if (!petStoreExists) {
            throw new IllegalArgumentException("Customer does not belong to the specified pet store.");
        }

   
        return customer;
    }

    @Transactional(readOnly = false)
    public Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
        if (customerId == null) {
           
            return new Customer();
        } else {
         
            return findCustomerById(petStoreId, customerId);
        }
    }

    public void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    }

    @Transactional(readOnly = false)
    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
     
        PetStore petStore = findPetStoreByID(petStoreId);

       
        Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), petStoreId);

      
        copyCustomerFields(customer, petStoreCustomer);
        customer.getPetStores().add(petStore); 
        petStore.getCustomers().add(customer);
        Customer savedCustomer = customerDao.save(customer);

        // Convert saved Customer object to PetStoreCustomer and return it
        PetStoreCustomer savedPetStoreCustomer = new PetStoreCustomer(savedCustomer);
        return savedPetStoreCustomer;
   
}
}