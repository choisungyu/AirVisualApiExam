package com.csg.airvisualapiexam.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csg.airvisualapiexam.MainViewModel;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.databinding.DialogMarkerBinding;
import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Pollutions;


public class MapInfoFragment extends DialogFragment {

    private static final String KEY_FAVORITE = "favorite";
    private static final String KEY_POLLUTIONS = "pollution";
    private Favorite mFavorite;
    private Pollutions mPollutions;


    public static MapInfoFragment newInstance(Favorite favorite, Pollutions pollution) {


        MapInfoFragment fragment = new MapInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FAVORITE, favorite);
        args.putSerializable(KEY_POLLUTIONS, pollution);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavorite = (Favorite) getArguments().getSerializable(KEY_FAVORITE);
            mPollutions = (Pollutions) getArguments().getSerializable(KEY_POLLUTIONS);

        } else {
            throw new IllegalArgumentException("favorite값 필수");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // activity의 viewModel을 가져와야 하는 것
        final MainViewModel viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);


        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_marker, (ViewGroup) getView(), false);
        final DialogMarkerBinding dialogMarkerBinding = DialogMarkerBinding.bind(view);
        dialogMarkerBinding.setViewModel(viewModel);

        // 마지막 해주기
        dialogMarkerBinding.setFavorite(mFavorite);
        dialogMarkerBinding.setPollutions(mPollutions);

//        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Memo saveMemo = new Memo();
//                // 즐겨찾기 하는 버튼
//                saveMemo.setId(mFavorite.getMemoId());
//
//                // dialog close
//                dismiss();
//            }
//        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setView(view);

        return builder.create();


    }
}
