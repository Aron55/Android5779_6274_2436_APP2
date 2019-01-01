package com.arons.android5779_6274_2436_app2.Model.Backend;


import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Datasource.SQL_DBManager;

public class DBManager_Factory {

    public static DBManager db = null;

     public static DBManager getInstance(){
         if (db == null)
             db = new SQL_DBManager();
         return db;
     }
}
