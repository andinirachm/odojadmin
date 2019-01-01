package id.odojadmin.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.odojadmin.R;
import id.odojadmin.controller.GroupController;
import id.odojadmin.event.GetGroupByAdminIdEvent;
import id.odojadmin.event.GroupClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.DateHelper;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.model.Group;
import id.odojadmin.view.activity.BaseFragment;
import id.odojadmin.view.activity.DetailRekapanActivity;
import id.odojadmin.view.adapter.BerandaAdapter;
import id.odojadmin.widget.TAGBookText;

public class BerandaFragment extends BaseFragment {
    @BindView(R.id.recycler_view_group)
    RecyclerView recyclerViewGroup;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @BindView(R.id.text_view_date)
    TAGBookText textViewDate;
    private AlertDialog alertDialog;

    private GroupController controller;
    private List<Group> groupList = new ArrayList<>();
    private BerandaAdapter adapter;

    public static BerandaFragment newInstance() {
        BerandaFragment fragment = new BerandaFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this, SubscriberPriority.HIGH);
        controller = new GroupController();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        unbinder = ButterKnife.bind(this, view);
        textViewDate.setText(DateHelper.getCurrentDate());

        setRecyclerViewGroup();
        controller.getAllGroupByAdminId(PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_USER_ID));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setRecyclerViewGroup() {
        adapter = new BerandaAdapter(getActivity(), groupList);
        recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewGroup.setAdapter(adapter);
    }

    public void onEventMainThread(GetGroupByAdminIdEvent event) {
        progressBar.setVisibility(View.GONE);
        if (event.isSuccess()) {
            groupList.clear();
            groupList.addAll(event.getGroupList());
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    public void onEventMainThread(GroupClickEvent event) {
        Intent i = new Intent(getActivity(), DetailRekapanActivity.class);
        i.putExtra("groupId", Integer.parseInt(event.getGroup().getId()));
        startActivity(i);
    }
}
