package com.aesse.daytrackr;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.aesse.daytrackr.R;

public class ItemListActivity extends SingleFragmentActivity
        implements ItemListFragment.Callbacks, ItemFragment.Callbacks {
    @Override
    protected Fragment createFragment(){
            return new ItemListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onItemSelected(Item item) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ItemPagerActivity.newIntent(this, item.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = ItemFragment.newInstance(item.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onItemUpdated(Item item) {
        ItemListFragment listFragment = (ItemListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

}