package com.saras.pppandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saras.pppandroid.R;
import com.saras.pppandroid.model.Ticket;

import java.util.ArrayList;

public class TicketAdapter extends BaseAdapter {

    Context context;
    ArrayList<Ticket> data;
    private static LayoutInflater inflater = null;

    public TicketAdapter(Context context, ArrayList<Ticket> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.ticket_item, null);
        TextView date = vi.findViewById(R.id.date);
        TextView loc = vi.findViewById(R.id.loc);
        date.setText(data.get(position).getDate());
        loc.setText(data.get(position).getPlace());
        return vi;
    }
}
