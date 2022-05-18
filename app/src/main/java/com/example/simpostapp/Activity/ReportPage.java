package com.example.simpostapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.simpostapp.DataContainer.Post;
import com.example.simpostapp.Database.UserDAOImpl;
import com.example.simpostapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ReportPage extends AppCompatActivity {
    private PieChart pieChart;
    TextView postCount;
    TextView likeCount;
    TextView viewCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_activiry);

        postCount = findViewById(R.id.textView_report_postCount);
        likeCount = findViewById(R.id.textView_report_likesCount);
        viewCount = findViewById(R.id.textView_report_totalViewCount);
        pieChart = (PieChart) findViewById(R.id.pieChart_report_likePercent);
        int numberOfPost = 0;
        int numberOfLikes = 0;
        int numberOfDislikes=0;
        int numberOfViews = 0;

        Intent from = getIntent();
        String userNmae = from.getExtras().getString("userName");
        Log.i("reportPage","current user is "+userNmae);
        UserDAOImpl userDAO = new UserDAOImpl(getApplicationContext());
        Set<Post> allPost = userDAO.postAuthorMatch(userNmae);

        if (allPost != null) {
            numberOfPost = allPost.size();
            Iterator iterator = allPost.iterator();
            while (iterator.hasNext()){
                Post post = (Post) iterator.next();
                numberOfLikes =numberOfLikes + post.getLikes().size();
                numberOfViews = numberOfViews + post.getViews().size();

            }
        }
        likeCount.setText(Integer.toString(numberOfLikes));
        postCount.setText(Integer.toString(numberOfPost) );
        viewCount.setText(Integer.toString(numberOfViews));
        setupPieChart();
        loadPieChartData(numberOfLikes,numberOfViews);
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Proportion of view with like");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(int likes, int totalView ){
        ArrayList<PieEntry> entries = new ArrayList<>();
        float likePercent = (float)likes/(totalView);
        float notLikePercent =(float) (totalView - likes)/totalView;
        entries.add(new PieEntry(likePercent,"View with like"));
        entries.add(new PieEntry(notLikePercent,"View without like"));
        //colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        PieDataSet dataSet = new PieDataSet(entries,"Proportion of likes");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}