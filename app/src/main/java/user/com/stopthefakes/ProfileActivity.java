package user.com.stopthefakes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;
    @BindView(R.id.spinnerCity)
    Spinner spinnerCity;
    String[] dataCity;
    ArrayAdapter<String> adapterCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUnbinder(ButterKnife.bind(this));


     //   Russia (Moscow, Kazan, Omsk), USA (New York, Los Angeles, Detroit, Portland), France (Paris, Lyon, Marseille, Nice), Italy (Rome, Milan, Naples)) 

        String[] data = new String[]{"County", "Russia", "USA", "France", "Italy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        spinnerCity.setAdapter(null);
                        break;
                    case 1:
                        dataCity = new String[]{"City", "Moscov", "Kazan", "Omsk"};
                        adapterCity = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, dataCity);
                        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCity.setAdapter(adapterCity);
                        break;
                    case 2:
                        dataCity = new String[]{"City", "New York", "Los Angeles", "Detroit", "Portland"};
                        adapterCity = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, dataCity);
                        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCity.setAdapter(adapterCity);
                        break;
                    case 3:
                        dataCity = new String[]{"City", "Paris", "Lyon", "Marseille", "Nice"};
                        adapterCity = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, dataCity);
                        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCity.setAdapter(adapterCity);
                        break;
                    case 4:
                        dataCity = new String[]{"City", "Rome", "Milan", "Naples"};
                        adapterCity = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, dataCity);
                        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCity.setAdapter(adapterCity);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
