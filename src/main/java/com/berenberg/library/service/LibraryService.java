package com.berenberg.library.service;




import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.model.Item;

import java.util.List;

/**
 * @author Bolaji
 * created on 05/09/2023
 */
public interface LibraryService {

        List<Item> getBorrowedItem(int itemId);
        BorrowedItemResponse borrowItem(BorrowedItemRequest request);
        Void returnItem(String  uniqueId);
        List<Item> getCurrentInventory();
        Void  checkItemAvailability(String uniqueItemId);
        List<Item> getAllItemByUserId(String userId);
        List<Item> getOverdueItems();
}
