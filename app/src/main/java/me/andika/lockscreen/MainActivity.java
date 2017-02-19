package me.andika.lockscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import me.andika.lockscreen.utils.LockScreen;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        LockScreen.getInstance().init(this);
        if(LockScreen.getInstance().isActive()){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);

        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    LockScreen.getInstance().active();
                }else{
                    LockScreen.getInstance().deactivate();
                }
            }
        });

    }


}
