package com.example.login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.login.DataContainer.Me;
import com.example.login.DataContainer.Post;
import com.example.login.DataContainer.PostPreview;
import com.example.login.DataContainer.Someone;
import com.example.login.Database.UserDAOImpl;
import com.example.login.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ReportPage extends AppCompatActivity {
    private PieChart pieChart, pieChart2, pieChart3;
    TextView postCount;
    TextView likeCount;
    TextView viewCount;
    TextView yourViews, yourLikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_activiry);

        postCount = findViewById(R.id.textView_report_postCount);
        likeCount = findViewById(R.id.textView_report_likesCount);
        viewCount = findViewById(R.id.textView_report_totalViewCount);
        pieChart = (PieChart) findViewById(R.id.pieChart_report_likePercent);
        pieChart2 = (PieChart) findViewById(R.id.pieChart_report_gender_audiance);
        pieChart3 = (PieChart) findViewById(R.id.pieChart_report_favourite);
        yourViews = findViewById(R.id.total_posts_you_viewed);

        int numberOfPost = 0;
        int numberOfLikes = 0;
        int numberOfDislikes=0;
        int numberOfViews = 0;
        // Gender of audiance
        int numberOfMales = 0;
        int numberOfFemale = 0;
        int numberOfOther = 0;

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

                // Calculate Gender Distribution
                for (String username : post.getViews()){
                    Someone someone = userDAO.getSomeoneData(username);
                    if (someone != null){
                        if (someone.getGender() != null){
                            switch (someone.getGender()){
                                case MALE: numberOfMales++; break;
                                case FEMALE: numberOfFemale++; break;
                                case OTHER: numberOfOther++; break;
                            }
                        }
                    }
                }
            }
        }

        likeCount.setText(Integer.toString(numberOfLikes));
        postCount.setText(Integer.toString(numberOfPost) );
        viewCount.setText(Integer.toString(numberOfViews));
        setupPieChart();
        loadPieChartData(numberOfLikes,numberOfViews);
        setupPieChart2();
        loadPieChartData2(numberOfMales, numberOfFemale, numberOfOther);



        // ========================= ANALYSE HISTORY =========================================== //

        int anime = 0, games = 0, manga = 0, novels = 0, art = 0, study = 0, music = 0, memes =0, cosplay = 0, other = 0;
        int yourViewsCount = 0;

        Me me = Me.getInstance();
        ArrayList<PostPreview> history = userDAO.getSelectPosts(Integer.MAX_VALUE, me.getHistory());
        for (PostPreview p : history){
            String tag = p.tag;
            switch(tag){
                case "ANIME": anime++; break;
                case "GAMES": games++; break;
                case "MANGA": manga++; break;
                case "LIGHT-NOVELS": novels++; break;
                case "ART": art++; break;
                case "STUDY": study++; break;
                case "MUSIC": music++; break;
                case "MEMES": memes++; break;
                case "COSPLAY": cosplay++; break;
                case "OTHER": other++; break;
            }
            yourViewsCount ++;
        }
        yourViews.setText("You have viewed: " + yourViewsCount + " posts!");

        setupPieChart3();
        loadPieChartData3(anime, games, manga, novels, art, study, music, memes, cosplay, other);

    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Proportion of view with like");
        pieChart.setCenterTextSize(20);
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

    private void setupPieChart2(){
        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setUsePercentValues(true);
        pieChart2.setEntryLabelTextSize(12);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.setCenterText("Gender distribution of your audiance");
        pieChart2.setCenterTextSize(20);
        pieChart2.getDescription().setEnabled(false);

        Legend l = pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }


    private void loadPieChartData2(int male, int female, int other){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(male,"Male"));
        entries.add(new PieEntry(female,"Female"));
        entries.add(new PieEntry(other,"Other"));
        //colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        PieDataSet dataSet = new PieDataSet(entries,"Gender Distribution");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);
        pieData.setValueFormatter(new PercentFormatter(pieChart2));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart2.setData(pieData);
        pieChart2.invalidate();
    }

    private void setupPieChart3(){
        pieChart3.setDrawHoleEnabled(true);
        pieChart3.setUsePercentValues(true);
        pieChart3.setEntryLabelTextSize(12);
        pieChart3.setEntryLabelColor(Color.BLACK);
        pieChart3.setCenterText("Different types of posts you viewed");
        pieChart3.setCenterTextSize(20);
        pieChart3.getDescription().setEnabled(false);

        Legend l = pieChart3.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }


    private void loadPieChartData3(int anime, int games, int manga, int novels, int art, int study, int music, int memes, int cosplay, int other){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(anime,"Anime"));
        entries.add(new PieEntry(games,"Games"));
        entries.add(new PieEntry(manga,"Manga"));
        entries.add(new PieEntry(novels,"Novels"));
        entries.add(new PieEntry(art,"Art"));
        entries.add(new PieEntry(study,"Study"));
        entries.add(new PieEntry(music,"Music"));
        entries.add(new PieEntry(memes,"Memes"));
        entries.add(new PieEntry(cosplay,"Cosplay"));
        entries.add(new PieEntry(other,"Other"));
        ArrayList<Integer> colors = new ArrayList<>();
        Collections.addAll(colors, Color.BLUE, Color.RED, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.MAGENTA, Color.YELLOW,Color.LTGRAY, Color.GREEN);
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);
        pieData.setValueFormatter(new PercentFormatter(pieChart3));
        pieData.setValueTextSize(5f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart3.setData(pieData);
        pieChart3.invalidate();
    }
}