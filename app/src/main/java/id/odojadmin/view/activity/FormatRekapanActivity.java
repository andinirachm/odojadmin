package id.odojadmin.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.helper.RekapanHelper;
import id.odojadmin.model.Member;

public class FormatRekapanActivity extends AppCompatActivity {

    @BindView(R.id.text_view_test)
    TextView textViewTest;
    @BindView(R.id.edit_text_test)
    EditText editTextTest;
    @BindView(R.id.btn_test)
    Button btnTest;

    private List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format_rekapan);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Format Rekapan");

        memberList.add(new Member(1, "Ilma", false, "18-b", "0812", false, 137));
        memberList.add(new Member(2, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(3, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(4, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(5, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(6, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(7, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(8, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(9, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(10, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(11, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(12, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(13, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(14, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(15, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(16, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(17, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(18, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(19, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(20, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(21, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(22, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(23, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(24, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(25, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(26, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(27, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(28, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(29, "Dini", false, "29-a", "0812", false, 137));
        memberList.add(new Member(30, "Dini", false, "29-a", "0812", false, 137));
        textViewTest.setText(RekapanHelper.showRekap("137", "Ahad", "17 Desember 2018",
                "Dini", "Dewi", "Kiki", "20:00", memberList));

        //textViewTest.setText(Symbol.admin);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public FormatRekapanActivity() {
    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("rekap", textViewTest.getText().toString());
//        clipboard.setPrimaryClip(clip);

//        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
//        String url = "https://chat.whatsapp.com/KrWptI8O3de0yk6W8YIt8G";
//        intentWhatsapp.setData(Uri.parse(url));
//        intentWhatsapp.putExtra(Intent.EXTRA_TEXT, textViewTest.getText().toString());
//        intentWhatsapp.setPackage("com.whatsapp");
//        startActivity(intentWhatsapp);

        PackageManager pm = getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = textViewTest.getText().toString();

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
