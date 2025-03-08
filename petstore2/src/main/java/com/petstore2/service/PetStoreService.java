package com.petstore2.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petstore2.controller.model.PetStoreData;
import com.petstore2.dao.PetStoreDao;
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


@Transactional(readOnly = true)
public void deletePetStoreById(long petStoreId) {
   PetStore petStore = findPetStoreByID(petStoreId);
   petStoreDao.delete(petStore);
}


}