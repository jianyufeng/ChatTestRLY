//package com.fule.myapplication.contact;
//
//import android.app.Fragment;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.fule.myapplication.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/4/19.
// */
//
//public class ContactFragment extends Fragment {
//    private ListView lv;
//    private Context mContext;
//    private List<String> datas;
//    private ArrayAdapter<String> adapter;
//
//    public ContactFragment() {
//    }
//
//    public ContactFragment(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_contact, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        datas = new ArrayList<>();
//        datas.add("jianyufeng");
//        datas.add("38");
//        datas.add("liming");
//        datas.add("123456");
//        datas.add("hehe");
////        initListView(view);
//    }
//
////    private void initListView(View view) {
////        lv= (ListView) view.findViewById(R.id.fragment_contact_lvID);
////        adapter = new ArrayAdapter<String>(mContext,R.layout.fragment_contact_item,R.id.fragment_contact_itemID,datas);
////        lv.setAdapter(adapter);
////        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                String item = adapter.getItem(position);
//////                Intent intent = new Intent(mContext, ChattingActivity.class);
////                intent.putExtra("toUserID",item);
////                getActivity().startActivity(intent);
////            }
////        });
////    }
//}
