package com.berenberg.library.controller;


import com.berenberg.library.Utils.ResponseEntityUtils;
import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.dto.TokenRequest;
import com.berenberg.library.dto.TokenResponse;
import com.berenberg.library.dto.generalResponse.GeneralResponseDto;
import com.berenberg.library.model.Item;
import com.berenberg.library.security.jwt.JwtUtils;
import com.berenberg.library.service.LibraryServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
/**
 * @author Bolaji
 * created on 27/08/2023
 */
@RestController
@RequestMapping("/api/v1/library")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor

public class LibraryController {
   
    private final LibraryServiceImpl libraryService;
    private final JwtUtils jwtUtils;

    @PostMapping(value = { "/getToken" } , produces = {"application/json" })
    public TokenResponse getToken(@RequestBody TokenRequest token) {
      
        return jwtUtils.createToken(token);
    }

   


    @GetMapping(value = { "/getBorrowedItem/{itemId}" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<List<Item>>> getBorrowedItem(@RequestParam int itemId) {
        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.getBorrowedItem(itemId));
    }

    @PostMapping(value = { "/borrowItem" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<BorrowedItemResponse>> borrowItem(
            @Valid @RequestBody BorrowedItemRequest request
    ) {

        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.borrowItem(request));
    }

    @PatchMapping(value = { "/returnItem/{uniqueId}" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<Void>> returnItem(String uniqueId) {

        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.returnItem(uniqueId));

    };

    @GetMapping(value = { "/getCurrentInventory" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<List<Item>>> getCurrentInventory(HttpServletRequest servletRequest) {
      
        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.getCurrentInventory());
    };

    @GetMapping(value = { "/checkItemAvailability/{itemId}" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<Void>> checkItemAvailability(@RequestParam String itemId,HttpServletRequest servletRequest) {

        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.checkItemAvailability(itemId));
    };

    @GetMapping(value = { "/getAllItemByUserId/{userId}" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<List<Item>>> getAllItemByUserId(@RequestParam String userId) {

        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.getAllItemByUserId(userId));
    };

    @GetMapping(value = { "/getOverdueItems}" }, produces = {"application/json" })
    public ResponseEntity<GeneralResponseDto<List<Item>>> getOverdueItems() {

        return ResponseEntityUtils.getSuccessfulControllerResponse(libraryService.getOverdueItems());
    };

}
