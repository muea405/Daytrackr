package com.aesse.daytrackr;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aesse.daytrackr.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private Callbacks mCallbacks;
    // Hosts activities
    public interface Callbacks {
        void onItemSelected(Item item);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.item_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menItem) {
        switch (menItem.getItemId()) {
            case R.id.new_item:
                Item item = new Item();
                ItemsList.get(getActivity()).addItem(item);
                updateUI();
                mCallbacks.onItemSelected(item);
                return true;
            default:
                return super.onOptionsItemSelected(menItem);
        }
    }


    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mDateTextView;
        private TextView mMoodTextView;
        private Item mItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);

            mDateTextView = (TextView) itemView.findViewById(R.id.itemDate);
            mMoodTextView = (TextView) itemView.findViewById(R.id.itemMood);

        }

        public void bind(Item item) {
            mItem = item;
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd");
            String strDate = formatter.format(mItem.getDate());

            mDateTextView.setText(strDate);
            //mMoodTextView.setText(mItem.getMood()); need to change this once each number has unique picture
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onItemSelected(mItem);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mItems;
        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item);

        }
        @Override
        public int getItemCount() {
            return mItems.size();
        }
        public void setItems(List<Item> items) {
            mItems = items;
        }
    }

    public void updateUI() {
        ItemsList itemsList = ItemsList.get(getActivity());
        List<Item> items = itemsList.getItems();
        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


}
