package com.aesse.daytrackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.aesse.daytrackr.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ItemFragment extends Fragment {
    private Item mItem;
    private EditText noteEntry;
    private Button dateButton, saveButton;
    private Callbacks mCallbacks;

    private static final String ARG_ITEM_ID = "item_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    public interface Callbacks {
        void onItemUpdated(Item item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID itemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
        mItem = ItemsList.get(getActivity()).getItems(itemId);
    }
    @Override
    public void onPause() {
        super.onPause();
        ItemsList.get(getActivity())
                .updateItem(mItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);
        //MOOD
        final RatingBar moodBar = (RatingBar) v.findViewById(R.id.moodrb);
        moodBar.setRating(mItem.getMood());
        moodBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Float moodRating = moodBar.getRating();
                mItem.setMood(moodRating);
                updateItem();
            }
        });
        //STRESS
        final RatingBar stressBar = (RatingBar) v.findViewById(R.id.stressrb);
        stressBar.setRating(mItem.getStress());
        stressBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Float stressRating = stressBar.getRating();
                mItem.setStress(stressRating);
                updateItem();
            }
        });
        //EAT
        final RatingBar eatBar = (RatingBar) v.findViewById(R.id.eatrb);
        eatBar.setRating(mItem.getEat());
        eatBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Float eatRating = eatBar.getRating();
                mItem.setEat(eatRating);
                updateItem();
            }
        });
        //SLEEP
        final RatingBar sleepBar = (RatingBar) v.findViewById(R.id.sleeprb);
        sleepBar.setRating(mItem.getSleep());
        sleepBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Float sleepRating = sleepBar.getRating();
                mItem.setSleep(sleepRating);
                updateItem();
            }
        });
        //EX
        final RatingBar exBar = (RatingBar) v.findViewById(R.id.exrb);
        exBar.setRating(mItem.getEx());
        exBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Float exRating = exBar.getRating();
                mItem.setEx(exRating);
                updateItem();
            }
        });
        //Notes
        noteEntry = (EditText) v.findViewById(R.id.noteEntry);
        noteEntry.setText(mItem.getNotes());
        noteEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mItem.setNotes(s.toString());
                updateItem();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //DATE
        dateButton = (Button) v.findViewById(R.id.dateButton);
        updateDate();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mItem.getDate());
                dialog.setTargetFragment(ItemFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mItem.setDate(date);
            updateItem();
            updateDate();
        }
    }

    private void updateItem() {
        ItemsList.get(getActivity()).updateItem(mItem);
        mCallbacks.onItemUpdated(mItem);
    }

    private void updateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd");
        String strDate = formatter.format(mItem.getDate());
        dateButton.setText(strDate);
    }

    public static ItemFragment newInstance(UUID itemId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, itemId);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

}
