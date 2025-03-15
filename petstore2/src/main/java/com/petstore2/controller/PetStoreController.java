package com.petstore2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.petstore2.controller.model.PetStoreData;
import com.petstore2.controller.model.PetStoreData.PetStoreCustomer;
import com.petstore2.controller.model.PetStoreData.PetStoreEmployee;
import com.petstore2.service.PetStoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pet_store")
public class PetStoreController {  

     @Autowired
    private PetStoreService petStoreService;


    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public PetStoreData savePetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Received request to save pet store data: {}", petStoreData);

        // Call service to save the pet store data and return the saved data.
        return petStoreService.savePetStore(petStoreData);
    }
//modify petstore

     @PutMapping("/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        log.info("Received request to update pet store with ID: {}", petStoreId);

        // Set the pet store ID in the PetStoreData object
        petStoreData.setPetStoreId(petStoreId);

        // Log the data being passed for update
        log.info("Updating pet store with data: {}", petStoreData);

        // Call the savePetStore method to update the pet store data in the service layer
        return petStoreService.savePetStore(petStoreData);
    }
    //Contributor in relation to petparks/pet_parks = pet_store in relation to employees
    //ContributorData = PetStoreData
    //Contributor = PetStore
    // pet_park in relation to amenties = pet_store in relation to customers
    //he writes amenties as a string in relation to petparks when it should be a object of the class ie customer 
    // only need @GetMapping nothing else that means no @GetMapping ("")

    @GetMapping
    public List<PetStoreData> retrieveAllPetStores() {
        log.info("retrieve all Pet Stores Called");
        //may have to make PetStoreService lowercase p
        return petStoreService.retrieveAllPetStores();
    }

    @GetMapping ("/{petStoreId}")
    public PetStoreData retrievePetStoreById(@PathVariable long petStoreId) {
        log.info("Retrieving PetStore with ID={}", petStoreId);
        return petStoreService.retrievePetStoreById(petStoreId);
    }
    @DeleteMapping("/petstore")
    public void deleteAllPetStore() {
        log.info("Attempting to delete all petstores");
        throw new UnsupportedOperationException (
            "Deleting all petstores is not allowed."); 
        
    }

    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(
        @PathVariable long petStoreId) {
            log.info("Deleting petstore with ID={}", petStoreId);

            petStoreService.deletePetStoreById(petStoreId);

            return Map.of("Message", "Deletion of petstore with ID=" + petStoreId + " was successful.");
        }
   
         @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)  // Return 201 Created status
    public PetStoreEmployee addEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee employee) {
        // Log the incoming request
        log.info("Request to add employee to pet store with ID: {}", petStoreId);

        // Call the service method to save the employee and return the result
        return petStoreService.saveEmployee(petStoreId, employee);

    }
    


    
    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)  // Return 201 Created status
    public PetStoreCustomer addCustomer(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer customer) {
        // Log the incoming request
        log.info("Request to add customer to pet store with ID: {}", petStoreId);

        // Call the service method to save the employee and return the result
        return petStoreService.saveCustomer(petStoreId, customer);

}
}