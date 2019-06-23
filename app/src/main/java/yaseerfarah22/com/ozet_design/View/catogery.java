package yaseerfarah22.com.ozet_design.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yaseerfarah22.com.ozet_design.Adapter.Category_card_adapter;
import yaseerfarah22.com.ozet_design.R;


public class catogery extends Fragment {
    RecyclerView recyclerView;
    Category_card_adapter categoryCardAdapter;

    public catogery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_catogery, container, false);

        recyclerView =(RecyclerView)view.findViewById(R.id.category_recycle);
        categoryCardAdapter =new Category_card_adapter(view.getContext(),getActivity());
        recyclerView.setAdapter(categoryCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));

        return view;

    }




}
