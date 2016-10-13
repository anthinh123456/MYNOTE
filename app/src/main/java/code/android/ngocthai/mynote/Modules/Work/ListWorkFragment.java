package code.android.ngocthai.mynote.Modules.Work;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import code.android.ngocthai.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListWorkFragment extends Fragment {


    public ListWorkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_work, container, false);
    }

}
