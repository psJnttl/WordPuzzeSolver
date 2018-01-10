package base.controller;


public class ItemNotFoundException extends RuntimeException {

    private long id;
    private String itemName;

    public ItemNotFoundException(long id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

}
