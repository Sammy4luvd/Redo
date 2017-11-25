package redo.tribaxy.com.models;

import java.util.UUID;

/**
 * Created by dalafiari on 11/17/17.
 */

public class HomeCardItem {

    /**
     * Parameters to be used when passing the {@link HomeCardItem} object around.
     */
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String CATEGORY_TITLE = "CATEGORY_TITLE";
    public static final String CATEGORY_COLOUR = "CATEGORY_COLOUR";
    public static final String CATEGORY_COUNT = "CATEGORY_COUNT";
    public static final String SELECTED_CATEGORY_IN_ADAPTER = "SELECTED_CATEGORY_IN_ADAPTER";

    private UUID categoryId;
    private String categoryTitle;
    private int categoryCount;
    private String categoryColour;

    /**
     * No need for generating random unique ids, the constructor calls the UUID.randomUUI() method
     * when a new object is to be created.
     */
    public HomeCardItem() {
        this.categoryId = UUID.randomUUID();
    }

    /**
     * @param categoryId     Category Unique Identifier from the database, how about UUID?
     * @param categoryTitle  Category Title,
     * @param categoryCount  Category Count and
     * @param categoryColour Category Colour from database respectively.
     */
    public HomeCardItem(UUID categoryId, String categoryTitle, int categoryCount, String categoryColour) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryCount = categoryCount;
        this.categoryColour = categoryColour;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getCategoryColour() {
        return categoryColour;
    }

    public void setCategoryColour(String categoryColour) {
        this.categoryColour = categoryColour;
    }
}
