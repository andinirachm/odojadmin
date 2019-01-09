package id.odojadmin.view.fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.odojadmin.R;
import id.odojadmin.controller.FormatRekapanController;
import id.odojadmin.controller.GroupController;
import id.odojadmin.event.GetGroupByAdminIdEvent;
import id.odojadmin.event.GroupClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.helper.Symbol;
import id.odojadmin.model.FormatRekapan;
import id.odojadmin.model.Group;
import id.odojadmin.utils.ItemDecorationAlbumColumns;
import id.odojadmin.view.activity.BaseFragment;
import id.odojadmin.view.activity.DetailGroupActivity;
import id.odojadmin.view.adapter.GroupAdapter;

public class GroupFragment extends BaseFragment {
    @BindView(R.id.recycler_view_group)
    RecyclerView recyclerViewGroup;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    private AlertDialog alertDialog;

    private GroupController controller;
    private List<Group> groupList = new ArrayList<>();
    private GroupAdapter adapter;
    private FormatRekapanController formatRekapanController;

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this, SubscriberPriority.HIGH);
        controller = new GroupController();
        formatRekapanController = new FormatRekapanController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        unbinder = ButterKnife.bind(this, view);
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
        adapter = new GroupAdapter(getActivity(), groupList);
        recyclerViewGroup.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewGroup.addItemDecoration(new ItemDecorationAlbumColumns(
                30,
                2));
        recyclerViewGroup.setAdapter(adapter);
    }

    private void showDialogNewGroup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_create_group, null);
        final EditText editTextNoGroup = dialogView.findViewById(R.id.edit_text_no_group);
        final EditText editTextBatasLapor = dialogView.findViewById(R.id.edit_text_batas_lapor);
        final EditText editTextAsmin = dialogView.findViewById(R.id.edit_text_asmin);
        Button btnCreate = dialogView.findViewById(R.id.btn_create);

        editTextBatasLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (String.valueOf(minutes).length() == 1) {
                            editTextBatasLapor.setText(hourOfDay + ":0" + minutes);
                        } else {
                            editTextBatasLapor.setText(hourOfDay + ":" + minutes);
                        }

                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group group = new Group(editTextNoGroup.getText().toString().trim(), 0, 0,
                        PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_USER_ID), editTextBatasLapor.getText().toString().trim(),
                        PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_NAME), editTextAsmin.getText().toString().trim());
                controller.createGroup(group);

                FormatRekapan formatRekapan = new FormatRekapan(Integer.parseInt(group.getId()), Symbol.divider, Symbol.star,
                        "", Symbol.recycle, Symbol.mosque, Symbol.kabah,
                        Symbol.tandaSilang, Symbol.home, getString(R.string.default_spirit_words), PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_USER_ID));
                formatRekapanController.addRekapan(formatRekapan);

                alertDialog.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
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
        Intent i = new Intent(getActivity(), DetailGroupActivity.class);
        i.putExtra("groupId", Integer.parseInt(event.getGroup().getId()));
        startActivity(i);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        showDialogNewGroup();
    }
}
