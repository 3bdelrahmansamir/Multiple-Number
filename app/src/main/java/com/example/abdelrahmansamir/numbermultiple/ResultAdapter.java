package com.example.abdelrahmansamir.numbermultiple;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abdelrahman Samir on 18/12/2016.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {


    List<Result> results;

    public ResultAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_result, parent, false);
        ResultHolder resultHolder = new ResultHolder(view);
        return resultHolder;
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        Result result = results.get(position);
        holder.tvResult.setText(result.textViewResult);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ResultHolder extends RecyclerView.ViewHolder {

        TextView tvResult;

        public ResultHolder(View itemView) {
            super(itemView);
            tvResult = (TextView) itemView.findViewById(R.id.tvJsonResult);
            tvResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_TEXT, ((TextView) view).getText().toString());
                    view.getContext().startActivity(shareIntent);
                }
            });
        }
    }
}
