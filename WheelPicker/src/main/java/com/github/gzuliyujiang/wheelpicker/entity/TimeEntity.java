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

package com.github.gzuliyujiang.wheelpicker.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 时间数据实体
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2019/6/17 15:29
 */
@SuppressWarnings({"unused"})
public class TimeEntity implements Serializable {
    private int hour;
    private int minute;
    private int second;

    public static TimeEntity target(int hour, int minute, int second) {
        TimeEntity entity = new TimeEntity();
        entity.setHour(hour);
        entity.setMinute(minute);
        entity.setSecond(second);
        return entity;
    }

    public static TimeEntity now() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return target(hour, minute, second);
    }

    public static TimeEntity minuteOnFuture(int minute) {
        TimeEntity entity = now();
        entity.setMinute(entity.getMinute() + minute);
        return entity;
    }

    public static TimeEntity hourOnFuture(int hour) {
        TimeEntity entity = now();
        entity.setHour(entity.getHour() + hour);
        return entity;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @NonNull
    @Override
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }

}
