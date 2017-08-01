package agvsingle.com.switchlanguage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_switch;
    private AlertDialog alertDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        tv_switch = (TextView)findViewById(R.id.tv_switch);
        tv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleAlertDialog();
            }
        });
    }

    public void showSingleAlertDialog(){
        final String[] items = {""+getText(R.string.tv_ch).toString()+"",""+getText(R.string.tv_en).toString()+"",""+getText(R.string.tv_ja).toString()+""};
        final int[] ints = new int[1];
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(""+getText(R.string.tv_lanswitch).toString()+"");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                Toast.makeText(MainActivity.this, items[index], Toast.LENGTH_SHORT).show();
                ints[0] = index;
            }
        });
        alertBuilder.setPositiveButton(""+getText(R.string.confirm).toString()+"", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                switch (ints[0]){
                    case 0:
                        switchLanguage(Locale.CHINA);
                        break;
                    case 1:
                        switchLanguage(Locale.ENGLISH);
                        break;
                    case 2:
                        switchLanguage(Locale.JAPANESE);
                        break;
                }
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton(""+getText(R.string.cancel).toString()+"", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    public void switchLanguage(Locale locale) {
        Resources resources = getBaseContext().getResources();
        Locale.setDefault(locale);
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
        refresh();
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
