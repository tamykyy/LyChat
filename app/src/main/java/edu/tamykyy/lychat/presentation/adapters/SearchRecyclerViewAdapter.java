package edu.tamykyy.lychat.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.domain.models.UserDomainModel;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.RecyclerViewViewHolder> {

    private final List<UserDomainModel> userList;
    private final SearchItemClickListener onItemClickListener;

    public SearchRecyclerViewAdapter(List<UserDomainModel> userList, SearchItemClickListener onItemClickListener) {
        this.userList = userList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_user_item_resycler_view, parent, false);

        return new RecyclerViewViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        UserDomainModel currUser = userList.get(position);

        Glide.with(holder.avatarImageView.getContext())
                .load(currUser.getProfilePicture())
                .into(holder.avatarImageView);
        holder.getNameTextView().setText(currUser.getFirstName() + " " + currUser.getLastName());
        holder.getOnlineInfoTextView().setText(currUser.getOnlineInfo());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;
        private final TextView onlineInfoTextView;


        public RecyclerViewViewHolder(@NonNull View itemView, SearchItemClickListener onItemClickListener) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            onlineInfoTextView = itemView.findViewById(R.id.onlineInfoTextView);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(v, getBindingAdapterPosition());
                    }
                }
            });
        }

        public ImageView getAvatarImageView() {
            return avatarImageView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getOnlineInfoTextView() {
            return onlineInfoTextView;
        }
    }
}
