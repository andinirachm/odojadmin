package id.odojadmin.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.controller.GroupController;
import id.odojadmin.event.GroupClickEvent;
import id.odojadmin.model.Group;

/**
 * Created by Andini Rachmah on 21/12/18.
 */
public class BerandaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Group> groupList = new ArrayList<>();

    public BerandaAdapter(Context context, List<Group> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beranda, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ItemHolder) {
            final ItemHolder h = (ItemHolder) holder;
            final GroupController controller = new GroupController();
            h.setData(groupList.get(i));
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus eventBus = ApplicationMain.getInstance().getEventBus();
                    eventBus.post(new GroupClickEvent(i, groupList.get(i)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_total_member)
        TextView textViewTotalMember;
        @BindView(R.id.text_view_persentase)
        TextView textViewPersentase;
        @BindView(R.id.crpv)
        ColorfulRingProgressView crpv;

        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(Group group) {
            textViewName.setText("Grup " + group.getId());
            textViewTotalMember.setText(group.getTotalMember() + " member");
            float p = ((float) group.getTotalKholas() / (float) group.getTotalMember()) * 100;
            crpv.setPercent(p);
            if (group.getTotalMember() != 0)
                textViewPersentase.setText(String.format("%,.0f", p) + "%");
            else
                textViewPersentase.setText("0%");
        }
    }
}
