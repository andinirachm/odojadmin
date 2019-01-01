package id.odojadmin.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.event.KholasClickEvent;
import id.odojadmin.event.NotKholasClickEvent;
import id.odojadmin.event.WhatsappClickEvent;
import id.odojadmin.model.Member;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGMediumText;

/**
 * Created by Andini Rachmah on 21/12/18.
 */
public class RekapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Member> memberList = new ArrayList<>();

    public RekapAdapter(Context context, List<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rekap, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ItemHolder) {
            final ItemHolder h = (ItemHolder) holder;
            h.setData(context, memberList.get(i));
            h.textViewWA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus eventBus = ApplicationMain.getInstance().getEventBus();
                    eventBus.post(new WhatsappClickEvent(i, memberList.get(i)));
                }
            });

            h.imageBtnKholas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus eventBus = ApplicationMain.getInstance().getEventBus();
                    eventBus.post(new KholasClickEvent(i, memberList.get(i)));
                }
            });

            h.imageBtnNotKholas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus eventBus = ApplicationMain.getInstance().getEventBus();
                    eventBus.post(new NotKholasClickEvent(i, memberList.get(i)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_id)
        TAGMediumText textViewId;
        @BindView(R.id.text_view_name)
        TAGBookText textViewName;
        @BindView(R.id.text_view_wa)
        TAGBookText textViewWA;
        @BindView(R.id.image_btn_wa)
        ImageButton imageBtnWa;
        @BindView(R.id.image_btn_kholas)
        ImageButton imageBtnKholas;
        @BindView(R.id.image_btn_not_kholas)
        ImageButton imageBtnNotKholas;

        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(Context context, Member member) {
            textViewId.setText("" + member.getId());
            textViewName.setText(member.getName() + " ~ " + member.getJuz().replace("-", ""));
            if (member.getKholas().equals("b")) {
                imageBtnKholas.setBackgroundResource(R.drawable.circle_white);
                imageBtnNotKholas.setBackgroundResource(R.drawable.circle_white);
            } else if (member.getKholas().equals("t")) {
                imageBtnKholas.setBackgroundResource(R.drawable.circle_white);
                imageBtnNotKholas.setBackgroundResource(R.drawable.circle_blue_stroke);
            } else if (member.getKholas().equals("k")) {
                imageBtnKholas.setBackgroundResource(R.drawable.circle_blue_stroke);
                imageBtnNotKholas.setBackgroundResource(R.drawable.circle_white);
            }
        }
    }
}
