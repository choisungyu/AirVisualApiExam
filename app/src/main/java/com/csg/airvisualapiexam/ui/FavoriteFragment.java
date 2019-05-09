package com.csg.airvisualapiexam.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csg.airvisualapiexam.MainViewModel;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.databinding.ItemFavoriteBinding;
import com.csg.airvisualapiexam.models.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final FavoriteAdapter adapter = new FavoriteAdapter();

        recyclerView.setAdapter(adapter);

        // xml 로 해보기
        viewModel.favorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                adapter.setItems(favorites);
            }
        });

    }

    private static class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
        interface OnFavoriteClickListener {
            void setOnClickListener(Favorite model);
        }

        private OnFavoriteClickListener mListener;

        private List<Favorite> mItems = new ArrayList<>();

        public FavoriteAdapter() {
        }

        public FavoriteAdapter(OnFavoriteClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Favorite> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_favorite, parent, false);
            final FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Favorite item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.setOnClickListener(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
            Favorite item = mItems.get(position);
            // TODO : 데이터를 뷰홀더에 표시하시오
            holder.binding.setFavorite(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
            // TODO : 뷰홀더 완성하시오
            ItemFavoriteBinding binding;


            public FavoriteViewHolder(@NonNull View itemView) {
                super(itemView);
                // TODO : 뷰홀더 완성하시오
                binding = ItemFavoriteBinding.bind(itemView);
            }
        }
    }

}
