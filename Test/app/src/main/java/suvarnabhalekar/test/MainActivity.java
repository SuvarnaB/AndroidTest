package suvarnabhalekar.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.devsmart.android.ui.HorizontalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    HorizontalListAdapter adapter;
    List<Integer> images = new ArrayList<Integer>();
    List<String> Image_title = new ArrayList<String>();

    private SliderLayout mDemoSlider;

    RelativeLayout background_color;
    TextView get_listview_title,color_text;
    Button red_button,blue_button,green_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            //w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //Question 1 : Horizontal List View

        //Add item to arraylist for Horizontal List view
        images.clear();
        Image_title.clear();

        images.add(R.drawable.image1);
        Image_title.add("Android 1");

        images.add(R.drawable.image2);
        Image_title.add("Android 2");

        images.add(R.drawable.image3);
        Image_title.add("Android 3");

        images.add(R.drawable.image4);
        Image_title.add("Android 4");

        images.add(R.drawable.image5);
        Image_title.add("Android 5");


        get_listview_title = (TextView)findViewById(R.id.textView6);

        //Add item in Horizontal List view
        HorizontalListView listview = (HorizontalListView)findViewById(R.id.listview);
        adapter=new HorizontalListAdapter(getLayoutInflater(), images,Image_title);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                //Question 3 : Set text to text view on Horizontal list click

                //Select item into horizontal list view and assign to textview
                String title = Image_title.get(arg2);
                get_listview_title.setText(title);


            }
        });

        //Question 2 : Page Viwer Using indicator

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        //Add item to slider view using has map
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Farm", R.drawable.image4);
        file_maps.put("Garden", R.drawable.image3);
        file_maps.put("Main Gate", R.drawable.image5);
        file_maps.put("Garden1", R.drawable.image1);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);



        //Question 5 : Change Backgroung color via button click

        red_button = (Button)findViewById(R.id.button);
        blue_button = (Button)findViewById(R.id.button1);
        green_button = (Button)findViewById(R.id.button2);

        background_color = (RelativeLayout)findViewById(R.id.Lay_color);
        color_text = (TextView)findViewById(R.id.textView7);

        //Click event on Red Button
        red_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set Background color of button to view
                background_color.setBackgroundColor(Color.parseColor("#b90000"));

                //Change text colour and text
                color_text.setText("You clicked on Red Button");
                color_text.setTextColor(Color.parseColor("#ffffff"));


            }
        });

        //Click event on Blue Button
        blue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set Background color of button to view
                background_color.setBackgroundColor(Color.parseColor("#02409d"));

                //Change text colour and text
                color_text.setText("You clicked on Blue Button");
                color_text.setTextColor(Color.parseColor("#ffffff"));

            }
        });

        //Click event on Grren Button
        green_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set Background color of button to view
                background_color.setBackgroundColor(Color.parseColor("#01760b"));

                //Change text colour and text
                color_text.setText("You clicked on Green Button");
                color_text.setTextColor(Color.parseColor("#ffffff"));

            }
        });



    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
