package com.example.abdelrahmansamir.numbermultiple;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static String jsonResult = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        jsonResult = null;
        super.onBackPressed();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new PlaceholderFragment();
            }
            return new FragmentResult();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Operation";
                case 1:
                    return "Result";
            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        List<Result> jsonResults;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final EditText editText = (EditText) rootView.findViewById(R.id.etNumber);
            final Button button = (Button) rootView.findViewById(R.id.btNumber);
            button.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setEnabled(false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://epic-demo.com/nour/services/test", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                response = new String(response.getBytes("iso-8859-1"), "UTF-8");
                            } catch (UnsupportedEncodingException e) {

                            }
                            Toast.makeText(rootView.getContext(), "Operation Succeed\n You can now go to result tap", Toast.LENGTH_LONG).show();

                            jsonResult = response;
                            try {
                                JSONObject jsonObject = new JSONObject(MainActivity.jsonResult);
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                jsonResults = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonResults.add(new Result(jsonArray.get(i).toString()));
                                }
                                ResultAdapter resultAdapter = new ResultAdapter(jsonResults);
                                FragmentResult.recyclerView.setAdapter(resultAdapter);

                            } catch (JSONException e) {

                            }
                            button.setEnabled(true);
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(rootView.getContext(), "Operation not success please check your connection", Toast.LENGTH_LONG).show();
                            button.setEnabled(true);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Nummber", editText.getText().toString());
                            return params;
                        }
                    };

                    Volley.newRequestQueue(rootView.getContext()).add(stringRequest);

                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (charSequence.length() != 0) {
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            return rootView;
        }
    }
}
