package redo.tribaxy.com.interfaces;

/**
 * Created by dalafiari on 11/20/17.
 */

public interface DeleteAndRestorable {

    void removeItem(int position);

    void restoreItem(Object item, int position);
}
