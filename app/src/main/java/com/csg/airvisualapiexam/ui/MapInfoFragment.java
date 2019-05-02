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
import com.csg.airvisualapiexam.models.Memo;


public class MapInfoFragment extends DialogFragment {

    private static final String KEY_FAVORITE = "favorite";
    private Favorite mFavorite;

    public static MapInfoFragment newInstance(Favorite favorite) {


        MapInfoFragment fragment = new MapInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FAVORITE, favorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavorite = (Favorite) getArguments().getSerializable(KEY_FAVORITE);
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

        final DialogMarkerBinding binding = DialogMarkerBinding.bind(view);
//        binding.setViewMdoel(viewModel);

//        TextView nameTextView = view.findViewById(R.id.name_text);
//        TextView addressTextView = view.findViewById(R.id.address_text);
//        TextView latlngTextView = view.findViewById(R.id.latlng_text);
//        final EditText memoEditTextView = view.findViewById(R.id.memo_edit);
//
//        nameTextView.setText(mFavorite.getName());
//        addressTextView.setText(mFavorite.getAddress());
//        latlngTextView.setText(mFavorite.getLatitude() + ", " + mFavorite.getLongitude());


        /*viewModel 의 getMemo 를 하면 Memo 를 리턴하여 파라미터 객체가 Memo가 됨.
         * mFavorite memoId 의 MemoId
         * */

        // 마지막 해주기
        binding.setFavorite(mFavorite);

        // 질문...!!
//        Memo memo = viewModel.getMemo(mFavorite.getMemoId());
//        if (memo != null) {
//            binding.memoEdit.setText(memo.getMemo());
//        }

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo saveMemo = new Memo();
                // 즐겨찾기 하는 버튼
                saveMemo.setId(mFavorite.getMemoId());
                saveMemo.setMemo(binding.memoEdit.getText().toString());
                viewModel.insertOrUpdateMemo(saveMemo);

                // dialog close
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setView(view);

        return builder.create();


    }
}
