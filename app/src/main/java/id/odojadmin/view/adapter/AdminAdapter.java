package id.odojadmin.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.event.MemberClickEvent;
import id.odojadmin.model.Admin;
import id.odojadmin.model.Member;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGMediumText;

/**
 * Created by Andini Rachmah on 21/12/18.
 */
public class AdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> userList = new ArrayList<>();

    public AdminAdapter(Context context, List<String> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ItemHolder) {
            final ItemHolder h = (ItemHolder) holder;
            h.setData(context, userList.get(i));
            h.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, userList.get(i) + " deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_name)
        TAGBookText textViewName;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;

        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(Context context, String name) {
            textViewName.setText(name);
        }
    }
}
