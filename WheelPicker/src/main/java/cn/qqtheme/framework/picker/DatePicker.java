package cn.qqtheme.framework.picker;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.qqtheme.framework.util.DateUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * 日期选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/14
 * Created By Android Studio
 */
public class DatePicker extends WheelPicker {
    private OnDatePickListener onDatePickListener;
    private String yearLabel = "年", monthLabel = "月", dayLabel = "日";
    private int startYear = 1970, endYear = 2050;
    private int selectedYear = 0, selectedMonth = 0, selectedDay = 0;
    private Mode mode = Mode.YEAR_MONTH_DAY;

    public enum Mode {
        //年月日
        YEAR_MONTH_DAY,
        //年月
        YEAR_MONTH,
        //月日
        MONTH_DAY
    }

    public DatePicker(Activity activity) {
        this(activity, Mode.YEAR_MONTH_DAY);
    }

    public DatePicker(Activity activity, Mode mode) {
        super(activity);
        this.mode = mode;
    }

    public void setLabel(String yearLabel, String monthLabel, String dayLabel) {
        this.yearLabel = yearLabel;
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
    }

    /**
     * @see Mode#YEAR_MONTH_DAY
     * @see Mode#YEAR_MONTH
     */
    public void setRange(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public void setSelectedItem(int year, int mouth, int day) {
        selectedYear = year;
        selectedMonth = mouth;
        selectedDay = day;
    }

    public void setSelectedItem(int yearOrMonth, int mouthOrDay) {
        if (mode.equals(Mode.MONTH_DAY)) {
            selectedMonth = yearOrMonth;
            selectedDay = mouthOrDay;
        } else {
            selectedYear = yearOrMonth;
            selectedMonth = mouthOrDay;
        }
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    @Override
    protected View initContentView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        WheelView yearView = new WheelView(activity);
        yearView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        yearView.setTextSize(textSize);
        yearView.setTextColor(textColorNormal, textColorFocus);
        yearView.setLineVisible(lineVisible);
        yearView.setLineColor(lineColor);
        yearView.setOffset(offset);
        layout.addView(yearView);
        TextView yearTextView = new TextView(activity);
        yearTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        yearTextView.setTextSize(textSize);
        yearTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(yearLabel)) {
            yearTextView.setText(yearLabel);
        }
        layout.addView(yearTextView);
        WheelView monthView = new WheelView(activity);
        monthView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        monthView.setTextSize(textSize);
        monthView.setTextColor(textColorNormal, textColorFocus);
        monthView.setLineVisible(lineVisible);
        monthView.setLineColor(lineColor);
        monthView.setOffset(offset);
        layout.addView(monthView);
        TextView monthTextView = new TextView(activity);
        monthTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        monthTextView.setTextSize(textSize);
        monthTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(monthLabel)) {
            monthTextView.setText(monthLabel);
        }
        layout.addView(monthTextView);
        final WheelView dayView = new WheelView(activity);
        dayView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dayView.setTextSize(textSize);
        dayView.setTextColor(textColorNormal, textColorFocus);
        dayView.setLineVisible(lineVisible);
        dayView.setLineColor(lineColor);
        dayView.setOffset(offset);
        layout.addView(dayView);
        TextView dayTextView = new TextView(activity);
        dayTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dayTextView.setTextSize(textSize);
        dayTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(dayLabel)) {
            dayTextView.setText(dayLabel);
        }
        layout.addView(dayTextView);
        if (mode.equals(Mode.YEAR_MONTH)) {
            dayView.setVisibility(View.GONE);
            dayTextView.setVisibility(View.GONE);
        } else if (mode.equals(Mode.MONTH_DAY)) {
            yearView.setVisibility(View.GONE);
            yearTextView.setVisibility(View.GONE);
        }
        if (!mode.equals(Mode.MONTH_DAY)) {
            if (!TextUtils.isEmpty(yearLabel)) {
                yearTextView.setText(yearLabel);
            }
            ArrayList<String> years = new ArrayList<String>();
            for (int i = startYear; i <= endYear; i++) {
                years.add(String.valueOf(i));
            }
            if (selectedYear == 0) {
                yearView.setItems(years);
            } else {
                yearView.setItems(years, selectedYear);
            }
            yearView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                    selectedYear = stringToYearMonthDay(item);
                    //需要根据年份及月份动态计算天数
                    int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
                    ArrayList<String> days = new ArrayList<String>();
                    for (int i = 1; i <= maxDays; i++) {
                        days.add(DateUtils.fillZore(i));
                    }
                    dayView.setItems(days, isUserScroll ? days.get(0) : String.valueOf(selectedDay));
                }
            });
        }
        if (!TextUtils.isEmpty(monthLabel)) {
            monthTextView.setText(monthLabel);
        }
        ArrayList<String> months = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            months.add(DateUtils.fillZore(i));
        }
        if (selectedMonth == 0) {
            monthView.setItems(months);
        } else {
            monthView.setItems(months, selectedMonth);
        }
        monthView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedMonth = stringToYearMonthDay(item);
                //需要根据年份及月份动态计算天数
                int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
                ArrayList<String> days = new ArrayList<String>();
                for (int i = 1; i <= maxDays; i++) {
                    days.add(DateUtils.fillZore(i));
                }
                dayView.setItems(days, isUserScroll ? days.get(0) : String.valueOf(selectedDay));
            }
        });
        if (!mode.equals(Mode.YEAR_MONTH)) {
            if (!TextUtils.isEmpty(dayLabel)) {
                dayTextView.setText(dayLabel);
            }
            //年月日选择时，最大天数根据年月来计算
            int maxDays;
            if (mode.equals(Mode.YEAR_MONTH_DAY)) {
                maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
            } else {
                maxDays = DateUtils.calculateDaysInMonth(selectedMonth);
            }
            ArrayList<String> days = new ArrayList<String>();
            for (int i = 1; i <= maxDays; i++) {
                days.add(DateUtils.fillZore(i));
            }
            if (selectedDay == 0) {
                dayView.setItems(days);
            } else {
                dayView.setItems(days, selectedDay);
            }
            dayView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                    if (TextUtils.isEmpty(item)) {
                        return;
                    }
                    selectedDay = stringToYearMonthDay(item);
                }
            });
        }
        return layout;
    }

    private int stringToYearMonthDay(String text) {
        if (text.startsWith("0")) {
            //截取掉前缀0以便转换为整数
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        super.setContentViewAfter(contentView);
        super.setOnConfirmListener(new OnConfirmListener() {
            @Override
            public void onConfirm() {
                if (onDatePickListener != null) {
                    String day = DateUtils.fillZore(selectedDay);
                    String month = DateUtils.fillZore(selectedMonth);
                    String year = String.valueOf(selectedYear);
                    switch (mode) {
                        case YEAR_MONTH:
                            ((OnYearMonthPickListener) onDatePickListener).onDatePicked(year, month);
                            break;
                        case MONTH_DAY:
                            ((OnMonthDayPickListener) onDatePickListener).onDatePicked(month, day);
                            break;
                        default:
                            ((OnYearMonthDayPickListener) onDatePickListener).onDatePicked(year, month, day);
                            break;
                    }
                }
            }
        });
    }

    protected interface OnDatePickListener {

    }

    public interface OnYearMonthDayPickListener extends OnDatePickListener {

        void onDatePicked(String year, String month, String day);

    }

    public interface OnYearMonthPickListener extends OnDatePickListener {

        void onDatePicked(String year, String month);

    }

    public interface OnMonthDayPickListener extends OnDatePickListener {

        void onDatePicked(String month, String day);

    }

}
