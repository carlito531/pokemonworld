package com.imie.android.api;

import java.util.List;

/**
 * Created by charly on 05/08/2016.
 */
public class DataProvider<T> {

    //private Class<T> clazz;

    private List<T> listItems;

    public List<T> getItems() {
        return listItems;
    }

    public void setItems(List<T> listItems) {
        this.listItems = listItems;
    }

    public static DataProvider getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private static final DataProvider instance = new DataProvider();
    }
}
