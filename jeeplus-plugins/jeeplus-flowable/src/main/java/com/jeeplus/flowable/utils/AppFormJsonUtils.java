package com.jeeplus.flowable.utils;

import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class AppFormJsonUtils {
    List <JSONObject> appFields = Lists.newArrayList ( );

    void generateModelForApp(JSONArray genList) {
        for (int i = 0; i < genList.size ( ); i++) {
            JSONObject orderedMap = (JSONObject) genList.get ( i );
            if ( "grid".equals ( orderedMap.get ( "type" ) ) ) {
                JSONArray columns = (JSONArray) orderedMap.get ( "columns" );
                for (int j = 0; j < columns.size ( ); j++) {
                    JSONObject item = ((JSONObject) columns.get ( j ));
                    generateModelForApp ( item.getJSONArray ( "list" ) );
                }
            } else if ("collapse".equals ( orderedMap.get ( "type" ) ) || "tabs".equals ( orderedMap.get ( "type" ) ) ) {
                JSONArray columns = (JSONArray) orderedMap.get ( "tabs" );
                for (int j = 0; j < columns.size ( ); j++) {
                    JSONObject item = ((JSONObject) columns.get ( j ));
                    generateModelForApp ( item.getJSONArray ( "list" ) );
                }
            } else if ( "report".equals ( orderedMap.get ( "type" ) ) ) {
                JSONArray rows = (JSONArray) orderedMap.get ( "rows" );
                for (int j = 0; j < rows.size ( ); j++) {
                    JSONObject rowItem = ((JSONObject) rows.get ( j ));
                    JSONArray columns = (JSONArray) rowItem.get ( "columns" );
                    columns.forEach ( item -> {
                        generateModelForApp ( ((JSONObject) item).getJSONArray ( "list" ) );
                    } );
                }
            } else if ("inline".equals ( orderedMap.get ( "type" ) ) || "card".equals ( orderedMap.get ( "type" ) ) || "dialog".equals ( orderedMap.get ( "type" ) ) ) {
                JSONArray list = (JSONArray) orderedMap.get ( "list" );
                generateModelForApp ( list );
            }  else {

                if ( orderedMap.getJSONObject ( "options" ).get ( "dataBind" ) != null && orderedMap.getJSONObject ( "options" ).getBoolean ( "dataBind" ) ) {
                    this.appFields.add ( orderedMap );
                }


            }
        }
    }


    public List <JSONObject> getFieldsForApp(String json) {
        JSONArray list = JSONObject.fromObject ( json ).getJSONArray ( "list" );
        generateModelForApp ( list );
        return appFields;
    }

    public static AppFormJsonUtils newInstance() {
        return new AppFormJsonUtils ( );
    }
}

