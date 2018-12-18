package template.solainteractive.com.androidsolatemplate.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import template.solainteractive.com.androidsolatemplate.Contract.ShowTerminalContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.Presenter.Terminal.ShowTerminalPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.controller.TerminalAdapter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.Terminal;
import template.solainteractive.com.androidsolatemplate.model.TerminalModel;
import template.solainteractive.com.androidsolatemplate.view.SignIn.ChangePasswordActivity;
import template.solainteractive.com.androidsolatemplate.view.SignIn.SigninActivity;


public class ShowTerminalActivity extends BaseActivity implements ShowTerminalContract.View {

    @BindView(R.id.toolbar_title)
    ImageView toolbarTitle;
    @BindView(R.id.toolbarList)
    Toolbar toolbarList;
    @BindView(R.id.llShowTerminal)
    LinearLayout llShowTerminal;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabAddNewTerminal)
    FloatingActionButton fabAddNewTerminal;
//    @BindView(R.id.tv_title_toolbar)
//    TextView tvTitleToolbar;
//    @BindView(R.id.et_terminal_search)
//    EditText etTerminalSearch;
//    @BindView(R.id.iv_clear)
//    ImageView ivClear;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.app_bar_search)
//    AppBarLayout appBarSearch;

    //INI BIND BARU UNTUK TOOLBAR SEARCH
//    @BindView(R.id.my_toolbar)
//    Toolbar myToolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.search_bar_hint_icon)
    ImageView ivSearch;
    @BindView(R.id.ll_empty_data)
    LinearLayout emptyData;
    @BindView(R.id.tv_empty_data)
    TextView emptyDataText;
    @BindView(R.id.tv_empty_data_line2)
    TextView emptyDataText2;


    private ShowTerminalContract.Presenter showTerminalPresenter;
    String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_terminal);
        ButterKnife.bind(this);

        showTerminalPresenter = new ShowTerminalPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        setSupportActionBar(toolbarList);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MyApplication.getInstance().getLoginStatus();


        showTerminalPresenter.onGetTerminalAPI();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabAddNewTerminal.isShown())
                    fabAddNewTerminal.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabAddNewTerminal.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //INI BARU
        etSearch.addTextChangedListener(textWatcher);

//        searchText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                keywords = searchText.getText().toString();
//                showTerminalPresenter.onSearchTerminal(keywords);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.start_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
        switch (item.getItemId()) {
            case R.id.btnMapView:
                Intent map = new Intent(ShowTerminalActivity.this, MapListActivity.class);
                startActivity(map);
                break;
            case R.id.btn_change_password:
                Intent changePassword = new Intent(ShowTerminalActivity.this, ChangePasswordActivity.class);
                startActivity(changePassword);
                break;
            case R.id.btnSignOut:
                showTerminalPresenter.onPostLogoutAPI();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.fabAddNewTerminal)
    public void onfabAddNewTerminalClicked() {
        fabAddNewTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowTerminalActivity.this, FormTerminalActivity.class));
            }
        });
    }

    @Override
    public void setAdapter(TerminalModel terminalModel, List<Terminal> terminalList) {
        onfabAddNewTerminalClicked();
        llShowTerminal.setVisibility(View.VISIBLE);
        emptyData.setVisibility(View.GONE);
        TerminalAdapter terminalAdapter = new TerminalAdapter(terminalModel.getTerminalList(), ShowTerminalActivity.this);
        recyclerView.setAdapter(terminalAdapter);
        //set text jam
        showTerminalPresenter.onClick(terminalModel, terminalList);

    }

    @Override
    public void intentToSignInActivity(String message) {
        Utils.showSnackBar(llShowTerminal,message);
        Intent i = new Intent(ShowTerminalActivity.this, SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void showSnackbar() {
        Utils.showSnackBar(llShowTerminal,"No Internet Connection");
    }

    @Override
    public void setOnClick(final TerminalModel terminalModel, final List<Terminal> terminalList) {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String id = terminalModel.getTerminalList().get(position).getTerminalId();
                String name = terminalModel.getTerminalList().get(position).getTerminalName();
                Double lati = terminalModel.getTerminalList().get(position).getTerminalLatitude();
                Double longi = terminalModel.getTerminalList().get(position).getTerminalLongitude();
                String address = terminalModel.getTerminalList().get(position).getTerminalAddress();
                String open = terminalModel.getTerminalList().get(position).getTerminalOpenTime();
                String closed = terminalModel.getTerminalList().get(position).getTerminalClosedTime();
                int typeId = terminalModel.getTerminalList().get(position).getTerminalTypeId();
                String metadata = terminalModel.getTerminalList().get(position).getMetadata();
                String description = terminalModel.getTerminalList().get(position).getDescription();
                String networkType = terminalModel.getTerminalList().get(position).getNetworkType();
                String postalCode = terminalModel.getTerminalList().get(position).getPostalCode();
                String activeStatus = terminalModel.getTerminalList().get(position).getTerminalActiveStatus();
                int rateId = terminalModel.getTerminalList().get(position).getRateId();
                String avatarPicture = terminalModel.getTerminalList().get(position).getAvatarPicture();

                Bundle b = new Bundle();
                b.putString("terminal_id", id);
                b.putString("terminal_name", name);
                b.putDouble("terminal_latitude", lati);
                b.putDouble("terminal_longitude", longi);
                b.putString("terminal_address", address);
                b.putString("terminal_open", open);
                b.putString("terminal_closed", closed);
                b.putInt("terminal_type_id", typeId);
                b.putString("metadata", metadata);
                b.putString("description", description);
                b.putString("network_type", networkType);
                b.putString("postal_code", postalCode);
                b.putString("terminal_active_status", activeStatus);
                b.putInt("rate_id", rateId);
                if (avatarPicture != "" || avatarPicture != null) {
                    b.putString("avatar_picture", avatarPicture);
                }
                Intent i = new Intent(ShowTerminalActivity.this, FormTerminalActivity.class);
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, final int position) {
//                Toast.makeText(ShowTerminalActivity.this, "MAU DELETE YA KAK ?\nHohoho Tidak semudah itu Fergusso", Toast.LENGTH_SHORT).show();
//////                terminalList.remove(terminalList.get(position).getTerminalId());
////                position = terminalList.indexOf(terminalModel);
////                terminalList.remove(position);
//                showTerminalPresenter.onDeleteTerminalAPI(terminalModel.terminalList.get(position).getTerminalId());
//                System.out.println("TERMINAL_ID itu ini loh : " + terminalModel.terminalList.get(position).getTerminalId());
//                terminalList.remove(position);
//
//                final Dialog dialog = new Dialog(ShowTerminalActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.layout_dialog_delete);

                // custom dialog
                final Dialog dialog = new Dialog(ShowTerminalActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_delete);

                // set the custom dialog components - text, image and button
                TextView text = dialog.findViewById(R.id.txt_dia);
                text.setText("Do you want to delete " + terminalModel.terminalList.get(position).getTerminalName() + " ?");

                Button btnYes = dialog.findViewById(R.id.btnYes);
                Button btnNo = dialog.findViewById(R.id.btnNo);

                // YES
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        System.out.println("YES DELETE" + terminalModel.terminalList.get(position).getTerminalId());
                        showTerminalPresenter.onDeleteTerminalAPI(terminalModel.terminalList.get(position).getTerminalId());
//                        System.out.println("TERMINAL_ID itu ini loh : " + terminalModel.terminalList.get(position).getTerminalId());
//                        dialog.dismiss();
                    }
                });

                // NO
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }));
    }

    @Override
    public void intentToSignInActivityforLogout() {
        Intent i = new Intent(ShowTerminalActivity.this, SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplicationContext().startActivity(i);
    }

    @Override
    public void getRefreshTerminalList() {
        Utils.showSnackBar(llShowTerminal,"Delete Success");
        Intent refresh = new Intent(this, ShowTerminalActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(refresh);
        this.finish();
    }

    @Override
    public void showStatusMessage(MainResponse mainResponse) {
        Utils.showSnackBar(llShowTerminal,mainResponse.getMessage());
    }

    //INI BARU
    @Override
    public void searchMesaage(String message) {
        Utils.showSnackBar(llShowTerminal,message);
    }

    @Override
    public void emptyData() {
        emptyData.setVisibility(View.VISIBLE);
        llShowTerminal.setVisibility(View.GONE);
    }

    //INI UNTUK CEK PER KATA KIRIM KE API UNTUK SEARCH
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            btnSearchClick();
        }
    };
    //SAMPAI SINI

    public void btnSearchClick(){
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywords = etSearch.getText().toString();
                if (keywords == ""){
                    showTerminalPresenter.onGetTerminalAPI();
                }
                showTerminalPresenter.onSearchTerminal(keywords);
            }
        });
    }
}
