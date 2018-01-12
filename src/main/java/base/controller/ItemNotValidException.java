package base.controller;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ItemNotValidException extends RuntimeException {
    private String itemName;
    private List<ObjectError> validationMessages;

    public ItemNotValidException(String itemName, List<ObjectError> validationMessages) {
        this.itemName = itemName;
        this.validationMessages = validationMessages;
    }

    public String getItemName() {
        return itemName;
    }

    public List<ObjectError> getValidationMessages() {
        return validationMessages;
    }

}
