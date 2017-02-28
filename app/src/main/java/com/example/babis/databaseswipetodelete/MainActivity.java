package com.example.babis.databaseswipetodelete;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText nameET , surnameET , ratingET;
    RecyclerView recyclerView;
    MyAdapter adapter;
    Cursor cursor;
    Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        myDb = new DatabaseHelper(this);
        cursor  = myDb.getAllData();
        adapter = new MyAdapter(cursor,this);
        nameET = (EditText)findViewById(R.id.name);
        surnameET = (EditText)findViewById(R.id.surname);
        ratingET = (EditText)findViewById(R.id.ratings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);







        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String id = (String)viewHolder.itemView.getTag(R.string.key);

                myDb.deleteDataSwipping(id);
;
                adapter.swapCursor(myDb.getAllData());

            }
        }).attachToRecyclerView(recyclerView);


    }

    public void addDataClicked(View view) {
        boolean isInserted;
        String name = nameET.getText().toString();
        String surname = surnameET.getText().toString();
        String rating = ratingET.getText().toString();
        if(myToast!=null){
            myToast.cancel();
        }
        if(!name.isEmpty()&&!surname.isEmpty()&&!rating.isEmpty()) {
           isInserted =  myDb.insertData(name,surname,rating);
            if(!isInserted){
                myToast.makeText(this,"Data didn't make it to our database :(",Toast.LENGTH_SHORT).show();
            }else {
                myToast.makeText(this,"Data inserted successful :)",Toast.LENGTH_SHORT).show();
            }
        }else {
            myToast.makeText(this,"Plz fill all the require data (name surname and rating)",Toast.LENGTH_SHORT).show();
        }

        adapter.swapCursor(myDb.getAllData());

    }

    public void showDataClicked(View view) {

        Cursor cursorResult = myDb.getAllData();
        if(myToast!=null){
            myToast.cancel();
        }

        if(cursorResult.getCount() == 0){
            myToast.makeText(this,"There is nothing to show",Toast.LENGTH_SHORT).show();
            return;
        }
         StringBuilder  buffer= new StringBuilder();
        while(cursorResult.moveToNext()){
            buffer.append("Name " + cursorResult.getString(1)+"\n"+"Surname "
                    + cursorResult.getString(2)+"\n"+"Rating "
                    + cursorResult.getString(3)+"\n");

        }


        adapter.swapCursor(myDb.getAllData());




    }

    public void deleteClicked(View view) {
        if(myToast!=null){
            myToast.cancel();
        }
        long isdeleted =myDb.deleteData(nameET.getText().toString());
        if(isdeleted==0){
            myToast.makeText(this,"Nothing deleted",Toast.LENGTH_SHORT).show();
        }else {
            myToast.makeText(this,"Delete succeed",Toast.LENGTH_SHORT).show();
            showDataClicked(view);
        }

        adapter.swapCursor(myDb.getAllData());

    }


    public void updateClicked(View view) {

        myDb.updateData(nameET.getText().toString(),surnameET.getText().toString(),ratingET.getText().toString());
        showDataClicked(view);

        adapter.swapCursor(myDb.getAllData());
    }



}
