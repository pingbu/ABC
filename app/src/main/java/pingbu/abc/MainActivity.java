package pingbu.abc;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final char[] ABC = "ABCDEFGHIJKLMNOPQ RSTUVW XYZ".toCharArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        GridView v = new GridView(this);
        v.setNumColumns(7);
        v.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ABC.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = new TextView(MainActivity.this);
                v.setText(ABC[position] + "" + Character.toLowerCase(ABC[position]));
                v.setTextSize(16 * dm.density);
                v.setTypeface(Typeface.MONOSPACE);
                v.setGravity(Gravity.CENTER);
                v.setPadding(0, (int) (14 * dm.density), 0, (int) (14 * dm.density));
                return v;
            }
        });
        final MediaPlayer mediaPlayer = new MediaPlayer();
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                char c = ABC[position];
                if (c != ' ') {
                    try {
                        AssetFileDescriptor assetFileDescriptor = getAssets().openFd(c + ".mp3");
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        setContentView(v);
    }
}
