package com.trisoft.ecommerce_new.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.trisoft.ecommerce_new.R;

import java.util.ArrayList;
import java.util.List;



public class Dialogs {
    Context context;
    List<Models> modelsList;

    public Dialogs(Context context) {
        this.context = context;
    }


    public void singleSelectDialog(List<String> cityList, final TextView editText, String select_mother_tongue, final String selectedposition) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_multiselect_seach);
        dialog.setTitle(select_mother_tongue);
        final SearchView filterText = dialog.findViewById(R.id.etSearchBox);


        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        LinearLayout liBottom = dialog.findViewById(R.id.liBottom);
        final Button btCancel = dialog.findViewById(R.id.btCancel);
        tv_title.setText(select_mother_tongue);

        SharedPreferences sp = context.getSharedPreferences("select_for",Context.MODE_PRIVATE);
        String dialog_for =sp.getString("dialog_for","");



        liBottom.setVisibility(View.GONE);
        final ListView list = dialog.findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, cityList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {


                if(select_mother_tongue.equals("Select Quantity")) {

                    editText.setText("Qty:"+list.getItemAtPosition(position) + "");

                }
                else {

                    editText.setText(list.getItemAtPosition(position) + "");

                }
                dialog.dismiss();




            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterText.setQuery("", true);
            }
        });

        filterText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                } else {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                adapter.getFilter().filter(s);
                return false;
            }
        });

        dialog.show();
    }

    public void CountrysingleSelectDialog(String[] cityList, final EditText editText, final EditText editText1) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_multiselect_seach);
        dialog.setTitle("Select Country");
        final SearchView filterText = dialog.findViewById(R.id.etSearchBox);
        LinearLayout liBottom = dialog.findViewById(R.id.liBottom);
        final Button btCancel = dialog.findViewById(R.id.btCancel);
        liBottom.setVisibility(View.GONE);
        final ListView list = dialog.findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if (list.getItemAtPosition(position).equals("Select Quantity")) {
                    editText1.setText("");
                    editText1.setVisibility(View.VISIBLE);
                } else {
                    editText1.setVisibility(View.GONE);
                }

                Log.d("Suresh", "Selected Item is = " + list.getItemAtPosition(position));
                editText.setText(list.getItemAtPosition(position) + "");
                dialog.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterText.setQuery("", true);
            }
        });
        filterText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                } else {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                adapter.getFilter().filter(s);
                return false;
            }
        });

        dialog.show();
    }


    public void multiselectDialog(String[] cityList, final EditText textView) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_multiselect_seach);
        dialog.setTitle("Select Country");
        final SearchView filterText = dialog.findViewById(R.id.etSearchBox);
        final ListView list = dialog.findViewById(R.id.listView);
        ImageView toggle = dialog.findViewById(R.id.toggle);
        final Button btCancel = dialog.findViewById(R.id.btCancel);
        Button btDone = dialog.findViewById(R.id.btDone);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityList);
        modelsList = new ArrayList<>();
        for (int i = 0; i < cityList.length; i++) {

            Models models1 = new Models();
            models1.setStr(cityList[i]);
            models1.setSelect(false);
            modelsList.add(models1);

        }
        final MultiDialogAdapter adapter = new MultiDialogAdapter();
        list.setAdapter(adapter);
        filterText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                } else {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                adapter.getFilter().filter(s);
                return false;
            }
        });
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = "";
                for (int i = 0; i < modelsList.size(); i++) {
                    if (modelsList.get(i).isSelect()) {
                        res += modelsList.get(i).getStr() + ",";
                    }
                }

                if (res.endsWith(",")) {
                    res = res.substring(0, res.length() - 1);
                }
                textView.setText(res);
                dialog.dismiss();
            }
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterText.setQuery("", true);
            }
        });
        dialog.show();
    }

    public void multiselectcountryDialog(String[] cityList, final EditText textView, final EditText editText1) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_multiselect_seach);
        dialog.setTitle("Select Country");
        final SearchView filterText = dialog.findViewById(R.id.etSearchBox);
        final ListView list = dialog.findViewById(R.id.listView);
        ImageView toggle = dialog.findViewById(R.id.toggle);
        final Button btCancel = dialog.findViewById(R.id.btCancel);
        Button btDone = dialog.findViewById(R.id.btDone);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityList);
        modelsList = new ArrayList<>();
        for (int i = 0; i < cityList.length; i++) {
            Models models1 = new Models();
            models1.setStr(cityList[i]);
            models1.setSelect(false);
            modelsList.add(models1);
        }
        final MultiDialogAdapter adapter = new MultiDialogAdapter();
        list.setAdapter(adapter);


        filterText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                } else {
                    btCancel.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                adapter.getFilter().filter(s);
                return false;
            }
        });


        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = "";
                for (int i = 0; i < modelsList.size(); i++) {
                    if (modelsList.get(i).isSelect()) {
                        res += modelsList.get(i).getStr() + ",";
                    }
                }
                if (res.endsWith(",")) {
                    res = res.substring(0, res.length() - 1);
                }
                if (res.contains("India")) {
                    editText1.setVisibility(View.VISIBLE);
                    textView.setText(res);
                } else {
                    editText1.setVisibility(View.GONE);
                    textView.setText(res);
                }

                dialog.dismiss();
            }
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterText.setQuery("", true);
            }
        });
        dialog.show();
    }


    public class MultiDialogAdapter extends BaseAdapter implements Filterable {
        List<Models> modelsList1;
        private ValueFilter valueFilter;

        public MultiDialogAdapter() {
            this.modelsList1 = modelsList;
        }

        @Override
        public int getCount() {
            return modelsList1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_search_layout, parent, false);
            final Models models = modelsList1.get(position);
            RelativeLayout reMain = view.findViewById(R.id.reMain);
            ImageView imCheck = view.findViewById(R.id.imCheck);
            TextView tvText = view.findViewById(R.id.tvText);
            tvText.setText(models.getStr());

//            cbText.setChecked(models.isSelect());

            if (models.isSelect()) {
                imCheck.setVisibility(View.VISIBLE);
            } else {
                imCheck.setVisibility(View.GONE);
            }


            reMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (models.isSelect()) {
                        models.setSelect(false);
                        modelsList1.set(position, models);
                        for (int i = 0; i < modelsList.size(); i++) {
                            if (modelsList.get(i).getStr().equals(models.getStr())) {
                                modelsList.set(i, models);
                            }
                        }
                    } else {
                        models.setSelect(true);
                        modelsList1.set(position, models);
                        for (int i = 0; i < modelsList.size(); i++) {
                            if (modelsList.get(i).getStr().equals(models.getStr())) {
                                modelsList.set(i, models);
                            }
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {
                valueFilter = new ValueFilter();
            }
            return valueFilter;
        }

        private class ValueFilter extends Filter {
            //Invoked in a worker thread to filter the data according to the constraint.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Models> filterList = new ArrayList<Models>();
                    for (int i = 0; i < modelsList.size(); i++) {
                        if ((modelsList.get(i).getStr().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            Models contacts = new Models();
                            contacts.setStr(modelsList.get(i).getStr());
                            contacts.setSelect(modelsList.get(i).isSelect());
                            filterList.add(contacts);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = modelsList.size();
                    results.values = modelsList;
                }
                return results;
            }

            //Invoked in the UI thread to publish the filtering results in the user interface.
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                modelsList1 = (ArrayList<Models>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    public class Models {
        String str;
        boolean isSelect;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
