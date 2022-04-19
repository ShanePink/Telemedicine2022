package com.example.iheartproject;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HospEquipmentListAdapter extends ArrayAdapter {

    private ArrayList<EquipmentItem> eqpList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        // Set the object from custom_list.xml
        TextView txtQty;
        TextView txtEqpName;
        CheckBox checkBox;
    }

    public HospEquipmentListAdapter(ArrayList<EquipmentItem> data, Context context) {
        super(context, R.layout.custom_list, data);
        this.eqpList = data;
        this.mContext = context;
    }



    @Override
    public int getCount() {
        return eqpList.size();
    }

    @Override
    public EquipmentItem getItem(int position) {
        return eqpList.get(position);
    }

    public ArrayList<EquipmentItem> getAllCheckedItem() {
        ArrayList<EquipmentItem> checkedEqpList = new ArrayList<EquipmentItem> ();
        for(EquipmentItem eqp : eqpList){
            if(eqp.getDonateStatus())
                checkedEqpList.add(eqp);
        }
        return checkedEqpList;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);

            // Assign ur buttons and text views
            viewHolder.txtEqpName = (TextView) convertView.findViewById(R.id.lvName);
            viewHolder.txtQty = (TextView) convertView.findViewById(R.id.lvQty);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.lvChkBx);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        EquipmentItem item = getItem(position);

        // Set ur item info in UI
        viewHolder.txtEqpName.setText(item.getEquipmentType());
        viewHolder.txtQty.setText(item.getQty().toString());
        viewHolder.checkBox.setChecked(item.getDonateStatus());


        return result;
    }
}