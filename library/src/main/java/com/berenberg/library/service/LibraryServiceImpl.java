package com.berenberg.library.service;


import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Bolaji
 * created on 05/09/2023
 */

 
@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {


    private final CsvServiceImplementation csvServiceImplementation;


    @Override
    public List<Item> getBorrowedItem(int itemId) {      
            return csvServiceImplementation.fetchBorrowedItems();
    }

    @Override
    public BorrowedItemResponse borrowItem(BorrowedItemRequest request) {

        return csvServiceImplementation.borrowItem(request);
    }

    @Override
    public Void returnItem(String uniqueId) {

        return csvServiceImplementation.updateReturnedCSV(uniqueId, "Y");
    }

    @Override
    public List<Item> getCurrentInventory() {

        return csvServiceImplementation.getCurrentInventory();
    }

    @Override
    public Void checkItemAvailability(String uniqueItemId) {

        return csvServiceImplementation.confirmAvailability(uniqueItemId);
    }

    @Override
    public List<Item> getAllItemByUserId(String userId) {

        return csvServiceImplementation.fetch(userId);
    }

    @Override
    public List<Item> getOverdueItems() {

       // return csvServiceImplementation.fetchOverDue();
       return csvServiceImplementation.fetchOverDue();
    }

}
