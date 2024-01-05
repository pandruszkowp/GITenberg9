/*
 * Copyright (c) 2016-present 贵州纳雍穿青人李裕江<1032694760@qq.com>
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.github.gzuliyujiang.fallback.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.gzuliyujiang.fallback.R;
import com.github.gzuliyujiang.fallback.bean.GoodsCategoryBean;
import com.github.gzuliyujiang.wheelpicker.ConstellationPicker;
import com.github.gzuliyujiang.wheelpicker.EthnicPicker;
import com.github.gzuliyujiang.wheelpicker.NumberPicker;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.PhoneCodePicker;
import com.github.gzuliyujiang.wheelpicker.SexPicker;
import com.github.gzuliyujiang.wheelpicker.annotation.EthnicSpec;
import com.github.gzuliyujiang.wheelpicker.contract.OnNumberPickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnNumberSelectedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionSelectedListener;
import com.github.gzuliyujiang.wheelpicker.widget.OptionWheelLayout;
import com.github.gzuliyujiang.wheelview.annotation.CurtainCorner;
import com.github.gzuliyujiang.wheelview.contract.WheelFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 单项滚轮选择器
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2019/6/23
 */
public class SinglePickerActivity extends BackAbleActivity implements OnNumberPickedListener, OnOptionPickedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_single);
    }

    @Override
    public void onNumberPicked(int position, Number item) {
        Toast.makeText(this, position + "-" + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionPicked(int position, Object item) {
        Toast.makeText(this, position + "-" + item, Toast.LENGTH_SHORT).show();
    }

    public void onInteger(View view) {
        NumberPicker picker = new NumberPicker(this);
        picker.setOnNumberPickedListener(this);
        picker.getWheelLayout().setOnNumberSelectedListener(new OnNumberSelectedListener() {
            @Override
            public void onNumberSelected(int position, Number item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                return item.toString() + " cm";
            }
        });
        picker.setRange(140, 200, 1);
        picker.setDefaultValue(172);
        picker.setTitle("身高选择");
        picker.show();
    }

    public void onFloat(View view) {
        NumberPicker picker = new NumberPicker(this);
        picker.setBodyWidth(120);
        picker.setOnNumberPickedListener(this);
        picker.getWheelLayout().setOnNumberSelectedListener(new OnNumberSelectedListener() {
            @Override
            public void onNumberSelected(int position, Number item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                DecimalFormat df = new DecimalFormat("0.00");
                return df.format(item);
            }
        });
        picker.setRange(-10f, 10f, 0.1f);
        picker.setDefaultValue(5f);
        picker.getTitleView().setText("温度选择");
        picker.getLabelView().setText("℃");
        picker.show();
    }

    public void onOptionText(View view) {
        OptionPicker picker = new OptionPicker(this);
        picker.setData("测试", "很长很长很长很长很长很长很长很长很长很长很长很长很长很长");
        picker.setOnOptionPickedListener(this);
        picker.getWheelLayout().setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.getWheelView().setStyle(R.style.WheelStyleDemo);
        picker.show();
    }

    public void onOptionBean(View view) {
        List<GoodsCategoryBean> data = new ArrayList<>();
        data.add(new GoodsCategoryBean(1, "食品生鲜"));
        data.add(new GoodsCategoryBean(2, "家用电器"));
        data.add(new GoodsCategoryBean(3, "家居生活"));
        data.add(new GoodsCategoryBean(4, "医疗保健"));
        data.add(new GoodsCategoryBean(5, "酒水饮料"));
        data.add(new GoodsCategoryBean(6, "图书音像"));
        OptionPicker picker = new OptionPicker(this);
        picker.setTitle("货物分类");
        picker.setBodyWidth(140);
        picker.setData(data);
        picker.setDefaultPosition(2);
        picker.setOnOptionPickedListener(this);
        OptionWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setIndicatorEnabled(false);
        wheelLayout.setTextColor(0xFFFF00FF);
        wheelLayout.setSelectedTextColor(0xFFFF0000);
        wheelLayout.setTextSize(15 * view.getResources().getDisplayMetrics().scaledDensity);
        //注：建议通过`setStyle`定制样式设置文字加大，若通过`setSelectedTextSize`设置，该解决方案会导致选择器展示时跳动一下
        //wheelLayout.setStyle(R.style.WheelStyleDemo);
        wheelLayout.setSelectedTextSize(17 * view.getResources().getDisplayMetrics().scaledDensity);
        wheelLayout.setSelectedTextBold(true);
        wheelLayout.setCurtainEnabled(true);
        wheelLayout.setCurtainColor(0xEEFF0000);
        wheelLayout.setCurtainCorner(CurtainCorner.ALL);
        wheelLayout.setCurtainRadius(5 * view.getResources().getDisplayMetrics().density);
        wheelLayout.setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

    public void onSex(View view) {
        SexPicker picker = new SexPicker(this);
        picker.setBodyWidth(140);
        picker.setIncludeSecrecy(false);
        picker.setDefaultValue("女");
        picker.setOnOptionPickedListener(this);
        picker.getWheelLayout().setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

    public void onEthnic(View view) {
        EthnicPicker picker = new EthnicPicker(this);
        picker.setBodyWidth(140);
        picker.setEthnicSpec(EthnicSpec.SEVENTH_NATIONAL_CENSUS);
        picker.setDefaultValueByCode("97");
        //picker.setDefaultValueByName("未定族称人口");
        //picker.setDefaultValueBySpelling("Unrecognized");
        picker.setOnOptionPickedListener(this);
        picker.getWheelLayout().setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

    public void onConstellation(View view) {
        ConstellationPicker picker = new ConstellationPicker(this);
        picker.setBodyWidth(140);
        picker.setIncludeUnlimited(true);
        //picker.setDefaultValueByName("射手座");
        //picker.setDefaultValueByDate(DateEntity.target(1991, 12, 9));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1991, 11, 9);
        picker.setDefaultValueByDate(calendar.getTime());
        picker.setOnOptionPickedListener(this);
        picker.getWheelLayout().setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

    public void onPhoneCode(View view) {
        PhoneCodePicker picker = new PhoneCodePicker(this);
        picker.setBodyWidth(140);
        picker.setOnlyChina(false);
        picker.setDefaultValueByCode("+86");
        picker.setOnOptionPickedListener(this);
        picker.getWheelLayout().setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

}
