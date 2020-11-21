package com.example.hypnochi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

public class HypnoImages extends AppCompatActivity {

    ImageSwitcher sw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        final TinyDB tb = new TinyDB(getApplicationContext());
        tb.putInt("index", 0);

        final MaterialButton[] buttons = {findViewById(R.id.next), findViewById(R.id.back)};
        final int[] imgs = {R.drawable.h1, R.drawable.h2,R.drawable.hb1, R.drawable.h3,
                R.drawable.h4,R.drawable.hb2, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.hb3};
        sw = findViewById(R.id.switcher);

        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {

                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);

                return img;
            }
        });

        sw.setImageResource(imgs[0]);

        for(int i=0; i<2; i++) {
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int z = tb.getInt("index");
                    if(finalI == 0) {
                        z++;
                        if (z<imgs.length)
                            tb.putInt("index", z);
                        else
                            tb.putInt("index", 0);

                        sw.setImageResource(imgs[tb.getInt("index")]);
                    }

                    else if(finalI == 1) {
                        z = z-1;
                        if (z>=0)
                            tb.putInt("index", z);
                        else
                            tb.putInt("index", imgs.length-1);

                        sw.setImageResource(imgs[tb.getInt("index")]);
                    }
                }
            });
        }
    }
}
