package com.example.myapplicationmobile2023;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplicationmobile2023.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private List<User> userList;
    private List<User> filteredList;
    private LayoutInflater inflater;
    private UserFilter filter;
    private Context context; // Declare the context variable

    public UserListAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        this.context = context; // Initialize the context variable
        this.userList = userList;
        this.filteredList = new ArrayList<>(userList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_user, parent, false);
        }

        User currentUser = getItem(position);

        ImageView imageViewAvatar = convertView.findViewById(R.id.imageViewAvatar);
        TextView textViewName = convertView.findViewById(R.id.textViewName);

        // Load the image from the URL using Picasso
        Picasso.get().load(currentUser.getAvatar()).into(imageViewAvatar);

        // Display the name
        textViewName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

        // Handle item click
        imageViewAvatar.setOnClickListener(v -> {
            // Create an Intent to start detailUser activity
            Intent intent = new Intent(context, detailUser.class);

            // Pass user details to detailUser activity without converting to String
            intent.putExtra("user", currentUser);

            // Start detailUser activity
            context.startActivity(intent);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new UserFilter();
        }
        return filter;
    }

    private class UserFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = new ArrayList<>(userList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                List<User> filteredUsers = new ArrayList<>();

                for (User user : userList) {
                    String userName = user.getLastName().toLowerCase();

                    if (userName.contains(filterPattern)) {
                        filteredUsers.add(user);
                    }
                }

                results.values = filteredUsers;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList.clear();
            filteredList.addAll((List<User>) results.values);
            notifyDataSetChanged();
        }
    }
}
