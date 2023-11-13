package com.berenberg.library.service;

import com.berenberg.library.Utils.CalculateDate;
import com.berenberg.library.Utils.ConvertDate;
import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.dto.generalResponse.GeneralResponseEnum;
import com.berenberg.library.exceptions.LibraryException;
import com.berenberg.library.model.Item;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bolaji
 *         created on 05/09/2023
 */

@Slf4j
@Service
public class CsvServiceImplementation {

    @Synchronized
    public BorrowedItemResponse borrowItem(BorrowedItemRequest request) {

        BorrowedItemResponse response = new BorrowedItemResponse();
        Item itemObj = new Item();
        Item item = request.getItem();

        log.info("item.getUniqueId()******" + item.getUniqueId());
        boolean confirmation = this.isItemAvailable(item.getUniqueId());
        log.info("confirmation******" + confirmation);
        if (!confirmation) {
            throw new LibraryException(GeneralResponseEnum.NOT_FOUND,
                    "The Item you have requested for is unavailable at the moment. Kindly check back");
        }

        if (request.getUserId() != null && item.getItemId() != null && item.getUniqueId() != null) {
            Date now = new Date();
            Date returnDate = CalculateDate.calculateReturnDate(now);
            String finalreturnDate = returnDate.toString();

            this.UpdateCSV(item.getItemId(), item.getUniqueId(), "N", finalreturnDate, request.getUserId());

            itemObj.setItemId(item.getItemId());
            itemObj.setItemtitle(item.getItemtitle());
            itemObj.setItemType(item.getItemType());

            response.setUserId(request.getUserId());
            response.setUserName(request.getUserName());
            response.setItem(itemObj);
            response.setBorrowedItemDate(now);
            response.setItemDueDate(returnDate);
        }

        return response;
    }

    

    @Synchronized
    public Void updateReturnedCSV(String loanUniqueId, String action) {
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        String targetId = loanUniqueId;
        log.info("targetId******" + targetId);
        List<String[]> updatedRows = new ArrayList<>();

        // Read data from the CSV file and update records
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord rec : csvParser) {
                String uniqueId = rec.get("UniqueID");
                String type = rec.get("Type");
                String itemId2 = rec.get("ItemID");
                String title = rec.get("Title");
                String availabilityFlag = rec.get("Availability");
                String userId = rec.get("UserId");
                String dueDate = rec.get("DueDate");

                if (uniqueId.equals(targetId)) {
                    log.info("Updating record for UniqueID: " + uniqueId);
                    log.info("Updating action for UniqueID: " + action);
                    String[] updatedRow = new String[rec.size()];
                    for (int i = 0; i < rec.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = action;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = "";
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = "";
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = rec.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                } else {

                    // updatedRows.add(record.values());
                    String[] updatedRow = new String[rec.size()];
                    for (int i = 0; i < rec.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = availabilityFlag;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = dueDate;
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = userId;
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = rec.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Server Busy, Kindly try again later");
        }

        // Write the updated data to a new CSV file
        try (Writer writer = new FileWriter(
                "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv");
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("UniqueID", "ItemID", "Type", "Title", "Availability", "DueDate",
                                "UserId"))) {

            for (String[] row : updatedRows) {
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Server Busy, Kindly try again later");
        }

        return null;
    }

    @Synchronized
    public boolean isItemAvailable(String uniqueId) {
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";

        List<CSVRecord> records = new ArrayList<>();

        // Read data from the CSV file
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                records.add(csvRecord);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred while checking availability of item");
        }

        // Update the data
        for (CSVRecord rec : records) {
            String availability = rec.get("Availability");
            String targetItem = rec.get("UniqueID");

            log.info("availability******1  " + availability);
            if ((targetItem.equals(uniqueId))) {
                return !availability.equals("N");
            }
        }

        return true;
    }

    @Synchronized
    public Void confirmAvailability(String uniqueItemId) {
        boolean confirmation = isItemAvailable(uniqueItemId);
        log.info("confirmation******" + confirmation);

        if (!confirmation) {
            throw new LibraryException(GeneralResponseEnum.NOT_FOUND,
                    "The Item you have requested for is unavailable at the moment. Kindly check back");
        }

        return null;
    }

    @Synchronized
    public void UpdateCSV(String itemId, String loanUniqueId, String action, String dueDate, String userId) {
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";

        String targetId = loanUniqueId; // New age for Bob
        log.info("targetId****** {}", targetId);
        List<String[]> updatedRows = new ArrayList<>();

        // Read data from the CSV file and update records
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord rec : csvParser) {
                String uniqueId = rec.get("UniqueID");
                String type = rec.get("Type");
                String itemId2 = rec.get("ItemID");
                String title = rec.get("Title");
                String availabilityFlag = rec.get("Availability");
                String userIdOld = rec.get("UserId");
                String dueDateOld = rec.get("DueDate");

                if (uniqueId.equals(targetId)) {
                    // log.info("Updating record for UniqueID: " + uniqueId);

                    String[] updatedRow = new String[rec.size()];
                    for (int i = 0; i < rec.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = action;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = dueDate;
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = userId;
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = rec.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                } else {

                    // updatedRows.add(record.values());
                    String[] updatedRow = new String[rec.size()];
                    for (int i = 0; i < rec.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = availabilityFlag;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = dueDateOld;
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = userIdOld;
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = rec.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Server Busy");
        }

        // Write the updated data to a new CSV file
        try (Writer writer = new FileWriter(
                "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv");
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("UniqueID", "ItemID", "Type", "Title", "Availability", "DueDate",
                                "UserId"))) {

            for (String[] row : updatedRows) {
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Server Busy");
        }
    }

    @Synchronized
    public List<Item> fetchOther(String targetId) {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String availabilityflag = csvRecord.get(4);

                // Access columns by index
                if (availabilityflag.equals("N")) {
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);
                }

            }
            log.info("fetchedList =====" + fetchedList);
            return fetchedList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred when fetching borrowed item");
        }
    }

    @Synchronized
    public List<Item> fetchBorrowedItems() {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String availabilityflag = csvRecord.get(4);

                // Access columns by index
                if (availabilityflag.equals("N")) {
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);
                }

            }
            log.info("fetchedList =====" + fetchedList);
            return fetchedList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred when fetching borrowed item");
        }
    }

    @Synchronized
    public List<Item> fetch(String targetId) {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String userId = csvRecord.get(6);
                log.info("User*************" + userId);
                log.info("c*************");
                // Access columns by index
                if (userId.equals(targetId)) {
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);
                }

            }
            log.info("fetchedList =====" + fetchedList);
            return fetchedList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred when fetching borrowed item");
        }
    }

    @Synchronized
    public List<Item> getCurrentInventory() {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<>();
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String availability = csvRecord.get(4);

                // Access columns by index
                if (availability.equals("Y")) {
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);

                }

            }

            return fetchedList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred when fetching borrowed item");
        }
    }

    @Synchronized
    public List<Item> fetchOverDue() {
         Date now =new Date();
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<>();
         String dt=now.toString();
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                //Date now = new Date();
                
                fetchedItem = new Item();
                // String availability = csvRecord.get(4);
                String age = csvRecord.get(5);

                if (age == null || age== "" ) {
                     //dt = (CalculateDate.calculateNullDate(now)).toString();
                     age=now.toString();
                     
                      //dt = (CalculateDate.calculateNullDate(now)).toString();
                }

                log.info("age    "+age);
                log.info("dt     "+dt);

                // Access columns by index
                
                if (!(ConvertDate.convertDates(age).before(ConvertDate.convertDates(dt)))) {

                    
                    log.info("entre  true  ");
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);

                }

            }

            return fetchedList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new LibraryException(GeneralResponseEnum.ERROR, "Error occurred when fetching borrowed item");
        }
    }
}
