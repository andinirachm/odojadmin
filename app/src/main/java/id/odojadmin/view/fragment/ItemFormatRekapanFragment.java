package id.odojadmin.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.odojadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFormatRekapanFragment extends Fragment {


    public static ItemFormatRekapanFragment newInstance() {
        ItemFormatRekapanFragment fragment = new ItemFormatRekapanFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_format_rekapan, container, false);
    }

}
