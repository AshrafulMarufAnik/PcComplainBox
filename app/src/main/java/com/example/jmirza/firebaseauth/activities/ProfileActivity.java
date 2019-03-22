package com.example.jmirza.firebaseauth.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.fragments.AllComplaintsFragment;
import com.example.jmirza.firebaseauth.fragments.ComplainFragment;
import com.example.jmirza.firebaseauth.fragments.ManageUsersFragment;
import com.example.jmirza.firebaseauth.fragments.MyComplaintsFragment;
import com.example.jmirza.firebaseauth.fragments.MyDeptComplaintsFragment;
import com.example.jmirza.firebaseauth.fragments.ProfileFragment;
import com.example.jmirza.firebaseauth.fragments.SolvedComplaintsFragment;
import com.example.jmirza.firebaseauth.fragments.PendingComplaintsFragment;
import com.example.jmirza.firebaseauth.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private NavigationView navigationView;
    private FirebaseAuth uAuth;
    private DatabaseReference myRef;
    private String uId;
    private FirebaseUser user;
    private User UserInfo;
    private android.support.v7.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        initialization();
        onClick();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nv_profile);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }

        });
    }

    public void initialization() {
        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        // setting up custom toolbar or actionbar
        toolbar = findViewById(R.id.toolbarID);
        toolbarTitle = findViewById(R.id.toolbar_title);
        setActionBarTitle("Profile");
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawerLayoutID);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);

        uId = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("users").child(uId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo = dataSnapshot.getValue(User.class);
                if (UserInfo != null) {
                    final String userType = UserInfo.occupation;
                    final String userName = UserInfo.name;

                    if (userType.equals("Student")) {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.drawer_menu_student);
                        View header = navigationView.getHeaderView(0);
                        TextView uName = header.findViewById(R.id.nav_header_user_name);
                        TextView uType = header.findViewById(R.id.nav_header_user_type);
                        uName.setText(userName);
                        uType.setText(userType);
                    } else if (userType.equals("Personnel")) {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.drawer_menu_personnel);
                        View header = navigationView.getHeaderView(0);
                        TextView uName = header.findViewById(R.id.nav_header_user_name);
                        TextView uType = header.findViewById(R.id.nav_header_user_type);
                        uName.setText(userName);
                        uType.setText(userType);
                    } else if (userType.equals("Admin")) {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.drawer_menu_admin);
                        View header = navigationView.getHeaderView(0);
                        TextView uName = header.findViewById(R.id.nav_header_user_name);
                        TextView uType = header.findViewById(R.id.nav_header_user_type);
                        uName.setText(userName);
                        uType.setText(userType);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onClick() {
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nv_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nv_complain:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ComplainFragment()).commit();
                break;
            case R.id.nv_my_complain:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyComplaintsFragment()).commit();
                break;
            case R.id.nv_all_complain:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllComplaintsFragment()).commit();
                break;
            case R.id.nv_solved_complaint:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SolvedComplaintsFragment()).commit();
                break;
            case R.id.nv_unsolved_complaint:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PendingComplaintsFragment()).commit();
                break;
            case R.id.nv_manage_users:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ManageUsersFragment()).commit();
                break;
            case R.id.nv_my_dept_complaint:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyDeptComplaintsFragment()).commit();
                break;
            case R.id.nv_share:
                Toast.makeText(this, "Share is selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.nv_signout:
                uAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.searchID);
        searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint(getString(R.string.search));
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }*/

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();

        return true;
    }

    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }
}
