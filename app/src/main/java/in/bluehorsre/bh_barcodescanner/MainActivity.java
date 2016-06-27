package in.bluehorsre.bh_barcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

     BarcodeDetector detector;
     ImageView myImageView;
     Frame frame;
     Button btnScan,btnCamera;
     TextView tvResult;
     SparseArray<Barcode> barcodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        onClick();
    }

    private void initialize() {
        tvResult = (TextView)findViewById(R.id.tvResult);
        myImageView = (ImageView) findViewById(R.id.imgBarcode);

        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.barcode_image);
        myImageView.setImageBitmap(myBitmap);

        frame = new Frame.Builder().setBitmap(myBitmap).build();
        detector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            tvResult.setText("Could not set up the detector!");
            return;
        }
        barcodes = detector.detect(frame);

        btnScan = (Button)findViewById(R.id.btnScan);
        btnCamera = (Button)findViewById(R.id.btnCamera);
    }

    private void onClick() {
        btnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Barcode thisCode = barcodes.valueAt(0);
                tvResult.setText(thisCode.rawValue);
            }
        });

        btnCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
            }
        });
    }
}
