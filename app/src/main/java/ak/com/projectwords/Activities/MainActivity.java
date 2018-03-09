package ak.com.projectwords.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.widget.TextView;

import ak.com.projectwords.Fragments.DictionaryFragment;
import ak.com.projectwords.Fragments.SearchFragment;
import ak.com.projectwords.R;

public class MainActivity extends AppCompatActivity  implements SearchFragment.OnFragmentInteractionListener,
DictionaryFragment.OnFragmentInteractionListener{

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    showSearchFragment();
                    return true;
                case R.id.navigation_vocabulary:
                    showDictionaryFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);

    }

    protected void showSearchFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_location, new SearchFragment().newInstance()).commit();

    }

    protected void showDictionaryFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_location, new DictionaryFragment().newInstance()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
