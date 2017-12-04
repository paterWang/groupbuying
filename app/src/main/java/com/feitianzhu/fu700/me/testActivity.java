package com.feitianzhu.fu700.me;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.view.pickview.LoopView;
import com.feitianzhu.fu700.view.pickview.OnItemSelectedListener;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * Created by Vya on 2017/8/29 0029.
 */

public class testActivity extends BaseActivity {

//    @BindView(R.id.purchaseInfo_recycler)
//    RecyclerView purchaseInfo_recycler;

    private int selectOneIndex,selectTwoIndex,selectThreeIndex;
    private  AlertDialog alertDialog;
    private  ArrayList<String> list;
    @Override
    protected int getLayoutId() {
        return R.layout.test_activity;
    }

    @Override
    protected void initView() {

        list = new ArrayList<>();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.clickButton)
    public void onClick(View v)
    {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_view_three, null,false);
        dialogBuilder.setView(dialogView);

        LoopView loopView1 = (LoopView) dialogView.findViewById(R.id.loopView1);
        LoopView loopView2 = (LoopView) dialogView.findViewById(R.id.loopView2);
        LoopView loopView3 = (LoopView) dialogView.findViewById(R.id.loopView3);
        loopView1.setDividerColor(getColor(R.color.dialog_rect_bg));
        loopView1.setNotLoop();
        loopView2.setDividerColor(getColor(R.color.dialog_rect_bg));
        loopView2.setNotLoop();
        loopView3.setDividerColor(getColor(R.color.dialog_rect_bg));
        loopView3.setNotLoop();
        Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure) ;
        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel) ;

        for (int i = 0; i < 15; i++) {
            list.add("item " + i);
        }
        // 滚动监听
        loopView1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectOneIndex = index;
            }
        });
        loopView2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectTwoIndex = index;
            }
        });
        loopView3.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectThreeIndex = index;
            }
        });
        // 设置原始数据
        loopView1.setItems(list);
        loopView2.setItems(list);
        loopView3.setItems(list);

        alertDialog = dialogBuilder.create();
        alertDialog.show();


        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(testActivity.this,"点击："+list.get(selectOneIndex)+"----"+
                        list.get(selectTwoIndex)+"----"+list.get(selectThreeIndex)+"----",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


}
