package code.android.ngocthai.mynote.Modules.Ui;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import code.android.ngocthai.mynote.Common.Utils.LogUtils;

/**
 * Created by NguyenBao on 06/10/2016.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(BaseFragment.class);

    protected abstract int getLayoutResource();

    protected abstract void initVariables(Bundle saveInstanceState, View rootView);

    protected abstract void initData(Bundle saveInstanceState);

    public BroadcastReceiver mReceiver = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), null, false);
        initBroadcastReceiver();
        initVariables(savedInstanceState, rootView);
        initData(savedInstanceState);
        return rootView;
    }

    /**
     * Register Broadcast Receiver for listening to Server
     */
    public void initBroadcastReceiver() {
        LogUtils.LOGI(TAG, "Listening to server response");
    }
}
