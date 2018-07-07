package tasks.com.pharmproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tasks.com.pharmproject.R;
import tasks.com.pharmproject.model.adapter.HomePostAdapter;

public class HomeFragment extends Fragment {
    TextView mTextViewNoPostsHome;
    RecyclerView mRecyclerViewPostsHome;
    HomePostAdapter mHomePostAdapter;
    ArrayList postsHomeArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mTextViewNoPostsHome = (TextView) rootView.findViewById(R.id.txt_no_posts_home);
        mTextViewNoPostsHome.setVisibility(View.INVISIBLE);
        postsHomeArrayList = new ArrayList();
        mRecyclerViewPostsHome = (RecyclerView) rootView.findViewById(R.id.recycle_posts_home);
        mHomePostAdapter = new HomePostAdapter(postsHomeArrayList);
        mRecyclerViewPostsHome.setNestedScrollingEnabled(false);
        mRecyclerViewPostsHome.setAdapter(mHomePostAdapter);
        mRecyclerViewPostsHome.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mHomePostAdapter.getItemCount() == 0) {
            mTextViewNoPostsHome.setVisibility(View.VISIBLE);
        }
        return rootView;
    }


}
