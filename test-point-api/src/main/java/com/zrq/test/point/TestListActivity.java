package com.zrq.test.point;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);
        initRecyclerView(recyclerView);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(new TestListAdapter(TestEntryPointInit.getAllModuleTestEntryPointInfoList()));
    }

    class TestListAdapter extends RecyclerView.Adapter<TestListViewHolder> {
        private final List<TestEntryPointInfo> list;

        public TestListAdapter(List<TestEntryPointInfo> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public TestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestListViewHolder(new Button(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull TestListViewHolder holder, int position) {
            final TestEntryPointInfo item = list.get(position);
            Button button = (Button) holder.itemView;
            button.setText(item.name);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // type：1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法、5：非静态无参方法（TestListActivity子类）
                    if (item.type == 1) {
                        // Activity
                        try {
                            startActivity(new Intent(getApplicationContext(), Class.forName(item.className)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (item.type == 2) {
                        // Fragment
                        startActivity(TestDesActivity.newIntent(getApplicationContext(), false, item.className));
                    } else if (item.type == 3) {
                        // Support Fragment
                        startActivity(TestDesActivity.newIntent(getApplicationContext(), true, item.className));
                    } else if (item.type == 4 || item.type == 5) {
                        // 4：静态无参方法、5：非静态无参方法（TestListActivity子类）
                        try {
                            Class<?> clazz = Class.forName(item.className);
                            Method method = clazz.getDeclaredMethod(item.methodName);
                            method.setAccessible(true);
                            method.invoke(item.type == 4 ? clazz : TestListActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class TestListViewHolder extends RecyclerView.ViewHolder {

        public TestListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
