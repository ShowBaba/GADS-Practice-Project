package com.example.gadsleaderboard.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboard.util.ApiUtil;
import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.model.Skills;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Samuel Shoyemi on 8/29/2020.
 */
public class IqFragment extends Fragment {
    View view;
    RecyclerView recyclerView;

    CustomIqAdapter adapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        try {
            URL url = ApiUtil.buildIqUrl();
            new SkillQueryTask().execute(url);
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }

        return view;
    }

    public class SkillQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(url);
            }
            catch(IOException e){
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Skills> skillsArr = ApiUtil.parseJson(result);
            adapter = new CustomIqAdapter(skillsArr);
            recyclerView.setAdapter(adapter);
        }
    }

    public class CustomIqAdapter extends RecyclerView.Adapter<CustomIqAdapter.viewHolder>{

        ArrayList<Skills> mSkill;

        public CustomIqAdapter(ArrayList<Skills> skills) {
            this.mSkill = skills;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list, viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position){
            Skills skills = mSkill.get(position);
            viewHolder.bind(skills);
        }
        @Override
        public int getItemCount() {
            return mSkill.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView image;
            TextView summary;

            public viewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.image);
                summary = itemView.findViewById(R.id.summary);
            }

            public void bind(Skills skills){
                name.setText(skills.name);
                String buildSummary = skills.score + " skill IQ Score, " + skills.country;
                summary.setText(buildSummary);
                if(!skills.badgeUrl.equals("")){
                    Picasso.get().load(skills.badgeUrl)
                            .into(image);
                }
            }
        }
    }
}
