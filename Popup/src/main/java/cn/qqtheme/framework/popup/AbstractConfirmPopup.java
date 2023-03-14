package cn.qqtheme.framework.popup;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 确认选择弹窗
 *
 * @author liyujiang
 * @date 2019/5/8 10:04
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractConfirmPopup<V extends View> extends BasePopup<AbstractConfirmPopup> {
    private RelativeLayout rlHeader;
    private TextView tvCancel;
    private TextView tvTitle;
    private TextView tvConfirm;
    private View headerDivider;
    private V bodyView;

    private LayoutProvider layoutProvider;

    public AbstractConfirmPopup(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    protected abstract V createBodyView(Context context);

    public void setLayoutProvider(LayoutProvider layoutProvider) {
        this.layoutProvider = layoutProvider;
    }

    @Override
    public View createContentView(FragmentActivity activity) {
        if (layoutProvider == null) {
            layoutProvider = new ConfirmLayoutProvider();
        }
        View view = View.inflate(activity, layoutProvider.provideLayoutRes(), null);
        rlHeader = view.findViewById(R.id.rl_header);
        tvCancel = view.findViewById(layoutProvider.specifyCancelIdRes());
        tvTitle = view.findViewById(layoutProvider.specifyTitleIdRes());
        tvConfirm = view.findViewById(layoutProvider.specifyConfirmIdRes());
        headerDivider = view.findViewById(R.id.header_divider);
        View bodyViewContainer = view.findViewById(layoutProvider.specifyBodyIdRes());
        if (!(bodyViewContainer instanceof ViewGroup)) {
            throw new NullPointerException("bodyViewContainer must be ViewGroup, such as FrameLayout");
        }
        ViewGroup bodyViewGroup = (ViewGroup) bodyViewContainer;
        if (bodyViewGroup.getChildCount() > 0) {
            bodyViewGroup.removeAllViews();
        }
        bodyView = createBodyView(activity);
        bodyViewGroup.addView(bodyView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View contentView) {
        if (tvCancel != null) {
            tvCancel.setOnClickListener(this);
        }
        if (tvConfirm != null) {
            tvConfirm.setOnClickListener(this);
        }
        if (bodyView == null) {
            throw new NullPointerException("bodyView == null");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == layoutProvider.specifyCancelIdRes()) {
            onCancel();
        } else if (id == layoutProvider.specifyConfirmIdRes()) {
            onConfirm();
        }
    }

    protected void onCancel() {
        dismiss();
    }

    protected void onConfirm() {
        dismiss();
    }

    private void checkContentView() {
        if (contentView == null) {
            throw new RuntimeException("Please show popup at first");
        }
    }

    public final RelativeLayout getHeaderView() {
        checkContentView();
        return rlHeader;
    }

    public final TextView getCancelTextView() {
        checkContentView();
        return tvCancel;
    }

    public final TextView getTitleTextView() {
        checkContentView();
        return tvTitle;
    }

    public final TextView getConfirmTextView() {
        checkContentView();
        return tvConfirm;
    }

    public final View getHeaderDividerView() {
        checkContentView();
        return headerDivider;
    }

    public final V getBodyView() {
        checkContentView();
        return bodyView;
    }

    /**
     * 自定义布局提供者接口
     */
    public interface LayoutProvider {

        /**
         * 提供布局文件
         *
         * @return {@link LayoutRes}
         */
        @LayoutRes
        int provideLayoutRes();

        /**
         * 提供取消的资源ID
         *
         * @return {@link IdRes}
         */
        @IdRes
        int specifyCancelIdRes();

        /**
         * 提供标题的资源ID
         *
         * @return {@link IdRes}
         */
        @IdRes
        int specifyTitleIdRes();

        /**
         * 提供确认的资源ID
         *
         * @return {@link IdRes}
         */
        @IdRes
        int specifyConfirmIdRes();

        /**
         * 提供主体视图的资源ID
         *
         * @return {@link IdRes}
         */
        int specifyBodyIdRes();

    }

    private static class ConfirmLayoutProvider implements LayoutProvider {

        @Override
        public int provideLayoutRes() {
            return R.layout.popup_confirm;
        }

        @Override
        public int specifyCancelIdRes() {
            return R.id.tv_cancel;
        }

        @Override
        public int specifyTitleIdRes() {
            return R.id.tv_title;
        }

        @Override
        public int specifyConfirmIdRes() {
            return R.id.tv_confirm;
        }

        @Override
        public int specifyBodyIdRes() {
            return R.id.fl_body;
        }

    }

}
