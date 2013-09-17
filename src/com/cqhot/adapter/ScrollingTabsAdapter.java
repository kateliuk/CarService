
package com.cqhot.adapter;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cqhot.R;



public class ScrollingTabsAdapter implements TabAdapter {

    private final FragmentActivity activity;
    private List<String> types;

    public ScrollingTabsAdapter(FragmentActivity act,List<String> types) {
        activity = act;
        this.types=types;
    }

    @Override
    public View getView(int position) {
        LayoutInflater inflater = activity.getLayoutInflater();
        final Button tab = (Button)inflater.inflate(R.layout.tabs, null);
//        String[] arry=(String[]) types.toArray();
//        final String[] mTitles = activity.getResources().getStringArray(arry);

        if (position < types.size()){
            tab.setText(types.get(position));
        }

        // Theme chooser
        //ThemeUtils.setTextColor(activity, tab, "tab_text_color");
        return tab;
    }
}
