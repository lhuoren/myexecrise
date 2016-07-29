package com.myexercuse.myexercise.mvp.ui.Fragment.BaseFrament;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by asus on 2016/3/26.
 */
public abstract class LazyFragment extends Fragment {

    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    private boolean isFirstLoad = true;//是否是第一次加载数据
    private Toast mToast;
    private boolean mIsPause;
//    private LoadingProgressDialog loadingProgressDialog;
    private View convertView;
    private SparseArray<View> mViews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.e("","   " + this.getClass().getSimpleName());
        View view = initViews(inflater, container, savedInstanceState);
        mViews = new SparseArray<>();
//        loadingProgressDialog = new LoadingProgressDialog(getActivity());
        return view;
    }

    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LogUtil.m("   " + this.getClass().getSimpleName());
        convertView = view;
        isInitView = true;
        lazyLoadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        LogUtil.m("context" + "   " + this.getClass().getSimpleName());

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        LogUtil.m("isVisibleToUser " + isVisibleToUser + "   " + this.getClass().getSimpleName());
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();

        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
//            LogUtil.m("第一次加载 " + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass().getSimpleName());
        } else {
//            LogUtil.m("不是第一次加载" + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass().getSimpleName());
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
//            LogUtil.m("不加载" + "   " + this.getClass().getSimpleName());
            return;
        }

//        LogUtil.m("完成数据第一次加载"+ "   " + this.getClass().getSimpleName());
        initData();
        isFirstLoad = false;
    }

    /**
     * 加载页面布局文件
     * @return
     */
//    protected abstract int getLayoutId();

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
//    protected abstract void initView();

    /**
     * 加载要显示的数据
     */
    protected abstract void initData();

    /**
     * fragment中可以通过这个方法直接找到需要的view，而不需要进行类型强转
     * @param viewId
     * @param <E>
     * @return
     */
    protected <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    public void toast(String text) {
        if (!this.mIsPause) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

//    public void showLoadingDialog() {
//        if (isVisible == false){
//            return;
//        }
//        if (loadingProgressDialog == null) {
//            loadingProgressDialog = new LoadingProgressDialog(getActivity());
//            loadingProgressDialog.setMessage("正在加载中…");
//            loadingProgressDialog.setCancelable(false);
//        }
////        if (!loadingProgressDialog.isShowing()){
////            loadingProgressDialog.show();
////        }
//    }

//    public void dismissLoadingDialog() {
//        try {
//            if (loadingProgressDialog != null) {
//                loadingProgressDialog.dismiss();
//                loadingProgressDialog = null;
//            }
//        } catch (Exception ex) {
//
//        }
//    }

}
