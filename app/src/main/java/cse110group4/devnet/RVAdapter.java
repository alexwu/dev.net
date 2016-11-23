package cse110group4.devnet;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);

        }
    }

    ArrayList<Post> projects;

    RVAdapter(ArrayList<Post> projects){
        this.projects = projects;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(projects.get(i).getTitle());
        System.out.println("Description: " + projects.get(i).getDescription());
        personViewHolder.personAge.setText(projects.get(i).getDescription());
        final int iteration = i;
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick

                Intent post = new Intent(v.getContext(), PostPage.class);
                post.putExtra("title", projects.get(iteration).getTitle());
                post.putExtra("content", projects.get(iteration).getBody());
                post.putExtra("id", projects.get(iteration).getPostId());

                System.out.println("Clicked");
                v.getContext().startActivity(post);

            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}

