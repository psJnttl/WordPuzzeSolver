package base.controller;

public class ItemAlreadyExistsException extends RuntimeException {

    private String itemName;
    private String value;

    public ItemAlreadyExistsException(String itemName, String value) {
        this.itemName = itemName;
        this.value = value;
    }

    public String getItemName() {
        return itemName;
    }

    public String getValue() {
        return value;
    }

}
