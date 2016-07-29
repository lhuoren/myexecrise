package com.myexercuse.myexercise.mvp.ui.Fragment.BaseFrament;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
//    protected Subscription subscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
    }
}
