package com.tw.recycleviewtest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> dataList = new ArrayList<>();
        int size = 101;
        for (int i = 0;i < size;i++) {
            dataList.add("item:" + i);
        }

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setAdapter(new Adapter(this, dataList));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Context mContext;
        private List<String> mList;
        public Adapter(Context context, List<String> list) {
            if (list == null) {
                list = new ArrayList();
            }
            mContext = context;
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final String item = mList.get(i);
            viewHolder.textView.setText(item);
            viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, item + " clicked", Toast.LENGTH_SHORT).show();
                }
            });
            if (i % 3 == 0) {
                viewHolder.itemView.setBackgroundColor(Color.RED);
            } else if (i % 3 == 1) {
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
            } else {
                viewHolder.itemView.setBackgroundColor(Color.YELLOW);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_icon);
            textView = itemView.findViewById(R.id.text_title);
            this.itemView = itemView;
        }
    }
}
