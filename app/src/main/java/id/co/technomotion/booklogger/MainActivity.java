package id.co.technomotion.booklogger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends ActionBarActivity {
    Button btnSimpan;
    ListView listViewBook;
    EditText editTextInput_judul;
    EditText editTextInput_pengarang;
    EditText editTextInput_halaman;

    //menginisiasi arraylist yang akan digunakan untuk menyimpan daftar judul buku
    ArrayList<Buku> listOfBook=new ArrayList<>();

    int editItem = -1;

    //mendeklarasikan arrayadapter
    BukuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewBook= (ListView) findViewById(R.id.listView_output);
        editTextInput_judul= (EditText) findViewById(R.id.editText_inputJudul);
        editTextInput_halaman= (EditText) findViewById(R.id.editText_inputHalaman);
        editTextInput_pengarang= (EditText) findViewById(R.id.editText2_inputPengarang);
        btnSimpan= (Button) findViewById(R.id.btn_simpan);

        //meng-inisiasi arrayadapter
        adapter = new BukuAdapter(this, listOfBook);
        listViewBook.setAdapter(adapter);

        //mengaktifkan fungsi onItemClickListener dan onItemLongClickListener
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //something happen
                Buku ClickedItem = (Buku) parent.getAdapter().getItem(position);
                editBuku(ClickedItem);

            }
        });

        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //something happen
                Buku longClickedItem = (Buku) parent.getAdapter().getItem(position);
                showDeleteDialog(longClickedItem);
                return false;
            }
        });



        // mengaktifkan fungsi button simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //something happen if user click this button
                String title=editTextInput_judul.getText().toString();
                String pengarang=editTextInput_pengarang.getText().toString();
                String halaman=editTextInput_halaman.getText().toString();

                // dilakukan check untuk memastikan bahwa user telah menulis judul buku
                if(!title.isEmpty()){
                    // menambahkan judul buku kedalam listOfBook
                    //listOfBook.add(new Buku(title, pengarang, halaman));
                    if(editItem > -1){
                        listOfBook.get(editItem).judul = title;
                        listOfBook.get(editItem).pengarang=pengarang;
                        listOfBook.get(editItem).halaman=halaman;
                    }else {
                        listOfBook.add(new Buku(title, pengarang, halaman));
                    }
                    // meng-update listview
                    adapter.notifyDataSetChanged();
                    // clear edittext
                    editTextInput_judul.setText("");
                    editTextInput_halaman.setText("");
                    editTextInput_pengarang.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"judul buku wajib diisi",Toast.LENGTH_SHORT).show();
                }

                editItem=-1;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // mmbuat alert dialog untuk fungsi hapus buku
    private void showDeleteDialog(final Buku buku){
        AlertDialog.Builder deleteDialog=new AlertDialog.Builder(this);
        deleteDialog.setMessage("Anda yakin untuk menghapus?");
        deleteDialog.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listOfBook.remove(buku);
                // setelah menghapus, kita perlu meng-update listview
                adapter.notifyDataSetChanged();
            }
        });
        deleteDialog.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteDialog.show();
    }

    private void editBuku(final Buku buku){
        editTextInput_judul.setText(buku.judul);
        editTextInput_pengarang.setText(buku.pengarang);
        editTextInput_halaman.setText(buku.halaman);
        editItem = listOfBook.indexOf(buku);
    }

}
