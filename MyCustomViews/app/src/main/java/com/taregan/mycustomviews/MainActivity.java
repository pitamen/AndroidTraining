package com.taregan.mycustomviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.taregan.mycustomviews.views.MyCustomButton;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    //TODO 1: create new package - views
    //TODO 2: create custom view class 'MyCustomButton' by extending AppCompactButton
    //TODO 3: define constructors in MyCustomButton Class
    //TODO 4: override onDraw Method
    //TODO 5: create drawable resources for background drawable
    //TODO 6: setBackgroundDrawable to custombutton
    //TODO 7: Specify custom attributes in attrs.xml
    //TODO 8: use specified attributes in MyCustomButtonClass

    MyCustomButton myCustomButton,myCustomButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCustomButton = (MyCustomButton) findViewById(R.id.myCustomButton);
        myCustomButton2 =(MyCustomButton) findViewById(R.id.myCustomButton2);

        myCustomButton.setOnClickListener(this);
        myCustomButton2.setOnClickListener(this);


//        myCustomButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                //
//            }
//        });
//
//        myCustomButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //
//            }
//        });

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.myCustomButton:
                //
                break;
            case R.id.myCustomButton2:
                //
                break;
        }
    }
}
