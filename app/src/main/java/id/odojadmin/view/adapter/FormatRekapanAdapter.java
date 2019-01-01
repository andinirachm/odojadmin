package id.odojadmin.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.odojadmin.R;
import id.odojadmin.view.fragment.ItemFormatRekapanFragment;
import id.odojadmin.widget.TAGSemiBoldText;

public class FormatRekapanAdapter extends FragmentStatePagerAdapter {
    Context context;
    List<String> groupList = new ArrayList<>();

    public FormatRekapanAdapter(Context context, FragmentManager fragmentManager, List<String> groupList) {
        super(fragmentManager);
        this.context = context;
        this.groupList = groupList;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFormatRekapanFragment.newInstance();
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_format_rekapan, null);
        TAGSemiBoldText textViewTitle = v.findViewById(R.id.text_view_title);
        textViewTitle.setText(groupList.get(position));
        return v;
    }
}
