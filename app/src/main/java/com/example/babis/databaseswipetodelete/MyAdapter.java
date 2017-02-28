package com.example.babis.databaseswipetodelete;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Babis on 2/28/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    private Cursor mCursor;
    private Context mContext;

    public MyAdapter(Cursor mCursor, Context mContext) {
        this.mCursor = mCursor;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.text_item,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String id =  mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.DatabaseScheme.DATABASE_ID));
        String name  = mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.DatabaseScheme.NAME));
        String surname = mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.DatabaseScheme.SURNAME));
        String rating= mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.DatabaseScheme.RATING));
        holder.infoText.setText(name +"\n"+ surname +"\n"+rating+"\n");
        holder.itemView.setTag(name);
        holder.itemView.setTag(R.string.key,id);



    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

//this is all teh magic :) every time we make a change to our database we need to swap the cursor
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView infoText;

        public MyViewHolder(View itemView) {
            super(itemView);
            infoText = (TextView)itemView.findViewById(R.id.recycling_text);

        }
    }
}
