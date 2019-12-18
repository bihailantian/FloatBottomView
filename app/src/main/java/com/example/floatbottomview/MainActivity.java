package com.example.floatbottomview;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<ItemEntry> entryList;
    private RecyclerView mRecyclerView;
    private LinearLayout llItem;
    private LinearLayoutManager layoutManager;
    private int mNumber = new Random().nextInt(1000);
    private TextView tvNumber;
    private ImageView ivUser;
    private TextView tvDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();


    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        llItem = findViewById(R.id.ll_item);
        tvNumber = findViewById(R.id.tv_number);
        ivUser = findViewById(R.id.iv_user);
        tvDesc = findViewById(R.id.tv_desc);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        MyAdapter adapter = new MyAdapter(MainActivity.this, entryList);
        mRecyclerView.setAdapter(adapter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                Log.d(TAG, "onScrollStateChanged");
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled");
                setVisibilityBottomView();
            }
        });


        llItem.setBackgroundResource(R.drawable.bg_image_border);
        ItemEntry entry = entryList.get(mNumber);
        tvNumber.setText(String.valueOf(entry.number));
        ivUser.setImageResource(entry.imgRes);
        tvDesc.setText(entry.desc);


    }

    private void setVisibilityBottomView() {
        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        Log.d(TAG, "     lastCompletelyVisibleItemPosition=" + lastCompletelyVisibleItemPosition);
        llItem.setVisibility(mNumber <= lastCompletelyVisibleItemPosition ? View.GONE : View.VISIBLE);
    }

    private void initData() {
        entryList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            entryList.add(new ItemEntry(i, R.mipmap.ic_launcher, "desc"));
        }
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private Context mContext;
        private List<ItemEntry> entryList;


        public MyAdapter(Context mContext, List<ItemEntry> entryList) {
            this.mContext = mContext;
            this.entryList = entryList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.rly_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ItemEntry entry = entryList.get(position);
            holder.tvNumber.setText(String.valueOf(entry.number));
            holder.ivUser.setImageResource(entry.imgRes);
            holder.tvDesc.setText(entry.desc);
        }

        @Override
        public int getItemCount() {
            return entryList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNumber;
            ImageView ivUser;
            TextView tvDesc;


            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvNumber = itemView.findViewById(R.id.tv_number);
                ivUser = itemView.findViewById(R.id.iv_user);
                tvDesc = itemView.findViewById(R.id.tv_desc);

            }
        }
    }


    private class ItemEntry {
        int number;
        @DrawableRes
        int imgRes;
        String desc;

        ItemEntry(int number, @DrawableRes int imgRes, String desc) {
            this.number = number;
            this.imgRes = imgRes;
            this.desc = desc;
        }
    }
}
