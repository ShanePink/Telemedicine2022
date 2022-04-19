package com.example.iheartproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class EquipmentObj {
    private String brand;
    private String equipmentcategory;

    public EquipmentObj(String brand, String equipmentcategory) {
        this.brand = brand;
        this.equipmentcategory = equipmentcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEquipmentcategory() {
        return equipmentcategory;
    }

    public void setEquipmentcategory(String equipmentcategory) {
        this.equipmentcategory = equipmentcategory;
    }

    public static String[] filterEquipmentWithCategory(String equipmentcategory, EquipmentObj[] list){

        ArrayList<String> filteredArrayList = new ArrayList<>();
        for(int i = 0; i < list.length; i++){
            if(list[i].getEquipmentcategory() == equipmentcategory) {
                Log.i("pos",list[i].getBrand());
                filteredArrayList.add(list[i].getBrand());
            }
        }

        return (String[]) filteredArrayList.toArray(new String[0]);
    }

    public static String[] convertEquipmentListtoString(EquipmentObj[] list){
        ArrayList<String> listarr = new ArrayList<>();
        for(int i = 0; i < list.length; i++){
            listarr.add(list[i].getEquipmentcategory());
        }

        String[] allequipments = listarr.toArray(new String[0]);
        Set<String> temp = new LinkedHashSet<>( Arrays.asList( allequipments ) );
        return temp.toArray( new String[temp.size()] );
    }
}
