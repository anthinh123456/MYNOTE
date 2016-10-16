package code.android.ngocthai.mynote.Modules.Work;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Adapter.ListWorkAdapter;
import code.android.ngocthai.mynote.Common.Object.Work;
import code.android.ngocthai.mynote.Common.Utils.RecyclerTouchListener;
import code.android.ngocthai.mynote.Common.Utils.SimpleDividerItemDecoration;
import code.android.ngocthai.mynote.Common.Utils.Utils;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.Modules.Ui.BaseFragment;
import code.android.ngocthai.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListWorkFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ArrayList<Work> listWork;
    private ListWorkAdapter adapter;
    private LinearLayoutManager llm;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list_work;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState, View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rcvListWork);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        recyclerView.setHasFixedSize(true);
        listWork = queryData();
        adapter = new ListWorkAdapter(listWork);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Work work = listWork.get(position);
                Toast.makeText(getActivity(), "" + work.getID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditWorkActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public ArrayList<Work> queryData() {
        String date = Utils.getCurrentDate();
        ArrayList<Work> ls = new ArrayList<>();
        DBController db = new DBController(getActivity());
        ls = db.getAllWork("'" + date + "'");
        return ls;
    }


}
