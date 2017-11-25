package redo.tribaxy.com.models;

/**
 * Created by dalafiari on 11/18/17.
 */

public class CategoryItemCard {


    private int id;
    private String title;

    public CategoryItemCard(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
