package id.odojadmin.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.odojadmin.R;
import id.odojadmin.controller.GroupController;
import id.odojadmin.event.GetGroupByAdminIdEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.model.Group;
import id.odojadmin.view.activity.BaseFragment;
import id.odojadmin.view.adapter.FormatRekapanAdapter;

public class FormatRekapanFragment extends BaseFragment {

    @BindView(R.id.text_view_test)
    TextView textViewTest;
    @BindView(R.id.edit_text_test)
    EditText editTextTest;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.tab)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private GroupController controller;
    private List<String> groupList = new ArrayList<>();
    private Unbinder unbinder;
    private FormatRekapanAdapter pagerAdapter;

    public static FormatRekapanFragment newInstance() {
        FormatRekapanFragment fragment = new FormatRekapanFragment();
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
        View view = inflater.inflate(R.layout.fragment_format_rekapan, container, false);
        unbinder = ButterKnife.bind(this, view);
        controller.getAllGroupByAdminId(PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_USER_ID));
        return view;
    }

    public void onEventMainThread(GetGroupByAdminIdEvent event) {
        if (event.isSuccess()) {
            groupList.clear();
            for (Group g : event.getGroupList()) {
                groupList.add(g.getId());
            }

            pagerAdapter =
                    new FormatRekapanAdapter(getActivity(), getFragmentManager(), groupList);
            viewPager.setAdapter(pagerAdapter);
            tabs.setupWithViewPager(viewPager);

            for (int i = 0; i < tabs.getTabCount(); i++) {
                TabLayout.Tab tab = tabs.getTabAt(i);
                tab.setCustomView(pagerAdapter.getTabView(i));
            }

            tabs.getTabAt(0).getCustomView().setSelected(true);
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
