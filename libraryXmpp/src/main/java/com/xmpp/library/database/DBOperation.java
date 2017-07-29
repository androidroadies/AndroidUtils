package com.xmpp.library.database;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by ln-222 on 29/2/16.
 */
public interface DBOperation {
    long insert(Object object);

    int update(Object object);

    int delete(Object object);

    List getAll();

    void deleteAll();

    ContentValues getContentValues(Object object);
}
