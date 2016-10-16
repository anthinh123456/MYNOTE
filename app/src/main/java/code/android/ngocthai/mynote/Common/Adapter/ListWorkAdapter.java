package code.android.ngocthai.mynote.Common.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Object.Work;
import code.android.ngocthai.mynote.R;

/**
 * Created by Thaihn on 16/10/2016.
 */
public class ListWorkAdapter extends RecyclerView.Adapter<ListWorkAdapter.ViewHolder> {

    private ArrayList<Work> listWork;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textHeader, textTitle, textTimeEnd, textTimeStart, textDate, textStatus, textImportant;

        public ViewHolder(View v) {
            super(v);
            textHeader = (TextView) v.findViewById(R.id.txtHeaderWork);
            textTitle = (TextView) v.findViewById(R.id.txtTitleWork);
            textImportant = (TextView) v.findViewById(R.id.txtImportantWork);
            textDate = (TextView) v.findViewById(R.id.txtDateWork);
            textStatus = (TextView) v.findViewById(R.id.txtStatusWork);
            textTimeStart = (TextView) v.findViewById(R.id.txtTimeStart);
            textTimeEnd = (TextView) v.findViewById(R.id.txtTimeEnd);
        }
    }

    public ListWorkAdapter(ArrayList<Work> ls) {
        this.listWork = ls;
    }

    @Override
    public ListWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_work, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListWorkAdapter.ViewHolder holder, int position) {
        Work currentWork = listWork.get(position);
        holder.textTimeEnd.setText(currentWork.getTIMEEND());
        holder.textHeader.setText(currentWork.getHEADER());
        holder.textDate.setText(currentWork.getDATE());
        holder.textTitle.setText(currentWork.getTITLE());
        holder.textStatus.setText(currentWork.getSTATUS());
        holder.textImportant.setText(currentWork.getIMPORTANT());
        holder.textTimeStart.setText(currentWork.getTIMESTART());
    }

    @Override
    public int getItemCount() {
        return listWork.size();
    }
}
