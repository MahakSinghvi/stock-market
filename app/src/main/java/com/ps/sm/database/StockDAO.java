package com.ps.sm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.ps.sm.dto.CategoryDTO;
import com.ps.sm.dto.ItemDTO;
import com.ps.sm.dto.StoreDTO;
import com.ps.sm.dto.StrategyDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

// local database for survey
public class StockDAO {
    // Survey Table Columns names
    private static final String KEY_ITEM_AUTO_ID = "stock_auto_id";
    private static final String KEY_CATEGORY_AUTO_ID = "category_auto_id";
    private static final String KEY_STRATEGY_AUTO_ID = "strategy_auto_id";
    private static final String KEY_STORE_VALUE_AUTO_ID = "store_value_auto_id";
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_CODE = "code";
    private static final String KEY_ITEM_LOT_SIZE = "lot_size";
    private static final String KEY_ITEM_STRIKE_GAP = "strike_gap";
    private static final String KEY_ITEM_TOTAL_STRIKE = "total_strike";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CMP_VALUE = "cmp_value";
    private static final String KEY_CALL_VALUE = "call_value";
    private static final String KEY_PRICE_VALUE = "price_value";
    private static final String KEY_PUT_VALUE = "put_value";
    private static final String KEY_CAT_ID = "cat_id";
    private static final String KEY_STRATEGY_ID = "strategy_id";


    // -------Database Name--------
    private static final String DATABASE_NAME = "stockapp.db";
    private static final int DATABASE_VERSION = 1;

    // -------Database Table Name--------
    private static final String DATABASE_TABLE_STOCK = "stock";
    private static final String CREATE_TABLE_STOCK = "create table stock(stock_auto_id integer primary key autoincrement,name text,code text,lot_size text,strike_gap text,total_strike text)";
    private static final String DROP_TABLE_STOCK = "drop table if exists stock";

    private static final String DATABASE_TABLE_CATEGORY = "category";
    private static final String CREATE_TABLE_CATEGORY = "create table category(category_auto_id integer primary key autoincrement,name text)";
    private static final String DROP_TABLE_CATEGORY = "drop table if exists category";

    private static final String DATABASE_TABLE_STRATEGY = "strategy";
    private static final String CREATE_TABLE_STRATEGY = "create table strategy(strategy_auto_id integer primary key autoincrement,category_id text,name text)";
    private static final String DROP_TABLE_STRATEGY = "drop table if exists strategy";

    private static final String DATABASE_TABLE_STORE_VALUE = "store_value";
    private static final String CREATE_TABLE_STORE_VALUE = "create table store_value(store_value_auto_id integer primary key autoincrement,stock_auto_id text,cmp_value text,call_value text,price_value text,put_value text,cat_id text,strategy_id text)";
    private static final String DROP_TABLE_STORE_VALUE = "drop table if exists store_value";

    private static Context mContext;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private SimpleDateFormat f;

    public StockDAO(Context context) {
        mContext = context;
        DBHelper = new DatabaseHelper(mContext);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_STOCK);
            db.execSQL(CREATE_TABLE_CATEGORY);
            db.execSQL(CREATE_TABLE_STRATEGY);
            db.execSQL(CREATE_TABLE_STORE_VALUE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_STOCK);
            db.execSQL(DROP_TABLE_CATEGORY);
            db.execSQL(DROP_TABLE_STRATEGY);
            db.execSQL(DROP_TABLE_STORE_VALUE);
            onCreate(db);
        }
    }

    public StockDAO open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }


    //    // ------------------SURVEY ADD ----------------------
    public long addToLocalDB(ItemDTO itemDTO) {
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, itemDTO.getName());
        values.put(KEY_ITEM_CODE, itemDTO.getCode());
        values.put(KEY_ITEM_LOT_SIZE, itemDTO.getLot_size());
        values.put(KEY_ITEM_STRIKE_GAP, itemDTO.getStrike_gap());
        values.put(KEY_ITEM_TOTAL_STRIKE, itemDTO.getTotal_strike());
        return db.insert(DATABASE_TABLE_STOCK, null, values);
    }

    public long addCategoryToLocalDB(CategoryDTO itemDTO) {
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, itemDTO.getName());
        return db.insert(DATABASE_TABLE_CATEGORY, null, values);
    }

    public long addStrategyToLocalDB(StrategyDTO itemDTO) {
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID, itemDTO.getCategory_id());
        values.put(KEY_ITEM_NAME, itemDTO.getName());
        return db.insert(DATABASE_TABLE_STRATEGY, null, values);
    }


    public long addStoreValueToLocalDB(StoreDTO itemDTO) {
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_AUTO_ID, itemDTO.getStock_id());
        values.put(KEY_CMP_VALUE, itemDTO.getCmp());
        values.put(KEY_CALL_VALUE, itemDTO.getCall_val());
        values.put(KEY_PRICE_VALUE, itemDTO.getPrice_val());
        values.put(KEY_PUT_VALUE, itemDTO.getPut_val());
        values.put(KEY_CAT_ID, itemDTO.getCat_id());
        values.put(KEY_STRATEGY_ID, itemDTO.getStrategy_id());
        return db.insert(DATABASE_TABLE_STORE_VALUE, null, values);
    }

    //
    // ------------------GET ALL SURVEY ----------------------
    public ArrayList<ItemDTO> getAllSurveyData(String vSearch) {
        ArrayList<ItemDTO> arrayList = new ArrayList<ItemDTO>();
        String query = "select * from " + DATABASE_TABLE_STOCK + " ORDER BY " + KEY_ITEM_AUTO_ID + " DESC";
        Cursor _cursor = db.rawQuery(query, null);
        if (vSearch.trim().length() > 0) {
            _cursor = db.rawQuery("select * from " + DATABASE_TABLE_STOCK + " where " + KEY_ITEM_NAME + " like ?", new String[]{"%" + vSearch + "%"});
        }
        ItemDTO itemDTO;
        while (_cursor.moveToNext()) {
            itemDTO = new ItemDTO();
            itemDTO.setId(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_AUTO_ID)));
            itemDTO.setName(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_NAME)));
            itemDTO.setCode(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_CODE)));
            itemDTO.setLot_size(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_LOT_SIZE)));
            itemDTO.setStrike_gap(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_STRIKE_GAP)));
            itemDTO.setTotal_strike(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_TOTAL_STRIKE)));
            arrayList.add(itemDTO);
        }
        _cursor.close();
        return arrayList;
    }

    public ArrayList<CategoryDTO> getAllCategory() {
        String query = "select * from " + DATABASE_TABLE_CATEGORY;
        System.out.println("StockDAO\t\t\tquery\t\t\t" + query);
        ArrayList<CategoryDTO> arrayList = new ArrayList<CategoryDTO>();
        Cursor _cursor = db.rawQuery(query, null);
        CategoryDTO categoryDTO;
        while (_cursor.moveToNext()) {
            categoryDTO = new CategoryDTO();
            categoryDTO.setId(_cursor.getString(_cursor
                    .getColumnIndex(KEY_CATEGORY_AUTO_ID)));
            categoryDTO.setName(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_NAME)));

            arrayList.add(categoryDTO);
        }
        _cursor.close();
        return arrayList;
    }

    public ArrayList<StrategyDTO> getStrategy(String vID) {
        String query = "select * from " + DATABASE_TABLE_STRATEGY + " where category_id = " + vID + " ORDER BY " + KEY_STRATEGY_AUTO_ID + " ASC";
        System.out.println("StockDAO\t\t\tgetStrategy\t\t\t" + query);
        ArrayList<StrategyDTO> arrayList = new ArrayList<StrategyDTO>();
        Cursor _cursor = db.rawQuery(query, null);
        StrategyDTO categoryDTO;
        while (_cursor.moveToNext()) {
            categoryDTO = new StrategyDTO();
            categoryDTO.setId(_cursor.getString(_cursor
                    .getColumnIndex(KEY_STRATEGY_AUTO_ID)));
            categoryDTO.setName(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_NAME)));
            categoryDTO.setCategory_id(_cursor.getString(_cursor
                    .getColumnIndex(KEY_CATEGORY_ID)));

            arrayList.add(categoryDTO);
        }
        _cursor.close();
        return arrayList;
    }


    public ArrayList<StoreDTO> getFillValue(String vID) {
        String query = "select * from " + DATABASE_TABLE_STORE_VALUE + " where stock_auto_id = " + vID /*+ " ORDER BY " + KEY_ITEM_AUTO_ID + " ASC"*/;
        System.out.println("StockDAO\t\t\tgetFillValue\t\t\t" + query);
        ArrayList<StoreDTO> arrayList = new ArrayList<StoreDTO>();
        Cursor _cursor = db.rawQuery(query, null);
        StoreDTO storeDTO;
//        _cursor.moveToFirst();
        while (_cursor.moveToNext()) {
            storeDTO = new StoreDTO();
            storeDTO.setId(_cursor.getString(_cursor
                    .getColumnIndex(KEY_STORE_VALUE_AUTO_ID)));
            storeDTO.setStock_id(_cursor.getString(_cursor
                    .getColumnIndex(KEY_ITEM_AUTO_ID)));
            storeDTO.setCmp(_cursor.getString(_cursor
                    .getColumnIndex(KEY_CMP_VALUE)));
            storeDTO.setCall_val(_cursor.getString(_cursor
                    .getColumnIndex(KEY_CALL_VALUE)));
            storeDTO.setPrice_val(Integer.parseInt(_cursor.getString(_cursor
                    .getColumnIndex(KEY_PRICE_VALUE))));
            storeDTO.setPut_val(_cursor.getString(_cursor
                    .getColumnIndex(KEY_PUT_VALUE)));

            arrayList.add(storeDTO);
        }
        _cursor.close();
        return arrayList;
    }


//    public long getSurveyCount() {
//        Cursor c = db.query(DATABASE_TABLE_SURVEY,
//                new String[]{KEY_SURVEY_AUTO_ID}, null, null, null, null,
//                null);
//        long count = c.getCount();
//        c.close();
//        return count;
//    }
//
//    public boolean updateSurveyById(String vSurvey_Auto_id, SurveyDTO surveyDTO) {
//        Gson gson = new Gson();
//        String vSurveyObject = gson.toJson(surveyDTO);
//        ContentValues values = new ContentValues();
//        values.put(KEY_SURVEY_PARAMS_OBJECT, vSurveyObject);
//        return db.update(DATABASE_TABLE_SURVEY, values, KEY_SURVEY_AUTO_ID + "='"
//                + vSurvey_Auto_id + "'", null) > 0;
//    }

    public boolean deleteStockById(String vSurvey_Auto_id) {
        return db.delete(DATABASE_TABLE_STOCK, KEY_ITEM_AUTO_ID + "='"
                + vSurvey_Auto_id + "'", null) > 0;
    }

    public boolean deleteStoreValueById(String vStock_Auto_id) {
        return db.delete(DATABASE_TABLE_STORE_VALUE, KEY_ITEM_AUTO_ID + "='"
                + vStock_Auto_id + "'", null) > 0;
    }


    public boolean deleteAllSurveyFromLocalDB() {
        return db.delete(DATABASE_TABLE_STOCK, "1", null) > 0;
    }


    public boolean deleteStrategyFromLocalDB() {
        return db.delete(DATABASE_TABLE_STRATEGY, null, null) > 0;
    }


}