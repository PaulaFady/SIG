package lib;


import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int number;
    private String date;
    private String name;
    private List<Item> items;

    public Invoice(int number, String date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
        this.items = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public void addItem(Item item) {
        items.add(item);
    }
}
