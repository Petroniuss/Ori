package com.softy.ori.customization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softy.ori.R;
import com.softy.ori.customization.SkinAdapter.ViewHolder;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class SkinAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final SkinProvider skinProvider;
    private final List<Skin> skins;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();

    @SuppressLint("CheckResult")
    public SkinAdapter(Context context) {
        this.skinProvider = SkinProvider.getInstance(context);
        this.skins = skinProvider.getSkins();

        onClickSubject.subscribe(name -> {
            skinProvider.choose(name);
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.skin_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Skin skin = skins.get(position);

        holder.imageView.setImageDrawable(skin.drawable());
        holder.imageView.setOnClickListener(v -> onClickSubject.onNext(skin.getName()));

        if (skin.getSkinDrawable() == skinProvider.chosen())
            holder.checkView.setVisibility(View.VISIBLE);
        else
            holder.checkView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return skins.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView checkView;

        ViewHolder(LinearLayout parent) {
            super(parent);

            this.imageView = parent.findViewById(R.id.skin_img);
            this.checkView = parent.findViewById(R.id.skin_check);
        }

    }

}
