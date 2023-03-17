package cn.qqtheme.framework.wheelpicker.interfaces.impl;

/**
 * 生日格式化
 *
 * @author liyujiang
 * @date 2019/5/14 14:31
 */
public class BirthdayDateFormatter extends SimpleDateFormatter {

    @Override
    public String formatYear(int year) {
        return super.formatYear(year) + "年";
    }

    @Override
    public String formatMonth(int month) {
        return super.formatMonth(month) + "月";
    }

    @Override
    public String formatDay(int day) {
        return super.formatDay(day) + "日";
    }

}
