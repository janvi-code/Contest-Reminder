package android.example.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder>{
    private List<item> list;
    private Context context;

    public customAdapter(List<item> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      item i = list.get(position);
      holder.status.setText(i.getStatus());
      holder.dS.setText(i.getDateStart());
      holder.dE.setText(i.getDateEnd());
      holder.tS.setText(i.getTimeStart());
      holder.tE.setText(i.getTimeEnd());
      holder.tim1.setText(i.getTime1());
      holder.tim2.setText(i.getTime2());
      holder.nam.setText(i.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView status;
        public TextView dS;
        public TextView tS;
        public TextView dE;
        public TextView tE;
        public TextView tim1;
        public  TextView tim2;
        public  TextView nam;
        public ViewHolder(View itemView) {
            super(itemView);
            status = (TextView) itemView.findViewById(R.id.status);
            dS = (TextView) itemView.findViewById(R.id.dateStart);
            tS = (TextView) itemView.findViewById(R.id.timeStart);
            dE = (TextView) itemView.findViewById(R.id.dateEnd);
            tE = (TextView) itemView.findViewById(R.id.timeEnd);
            tim1 = (TextView) itemView.findViewById(R.id.exactTimeStart);
            tim2 = (TextView) itemView.findViewById(R.id.exactTimeEnd);
            nam = (TextView) itemView.findViewById(R.id.name);

        }
    }
}
