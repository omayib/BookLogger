package id.co.technomotion.booklogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by streethardy on 21/05/15.
 */
public class BukuAdapter  extends ArrayAdapter<Buku> {
    public BukuAdapter(Context context, ArrayList<Buku> users){
        super(context, 0, users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Buku user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itembuku, parent, false);
        }
        // Lookup view for data population
        TextView judulBuku = (TextView) convertView.findViewById(R.id.judulBuku);
        TextView namaPengarang = (TextView) convertView.findViewById(R.id.namaPengarang);
        TextView jumlahHalaman = (TextView) convertView.findViewById(R.id.jumlahHalaman);
        // Populate the data into the template view using the data object
        judulBuku.setText(user.judul);
        namaPengarang.setText(user.pengarang);
        jumlahHalaman.setText(user.halaman);
        // Return the completed view to render on screen
        return convertView;
    }
}
