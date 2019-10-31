package com.selfimpr.svgaplayerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/svga/SVGA-Samples
 */
public class MainActivity extends AppCompatActivity {

    private static List<String> SVGAUrls;

    static {
        SVGAUrls = new ArrayList<>();
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/Yjk3YTdlNDEtNzFhZC00ZTljLWE1NTAtMTA1OGZhYmM5ZTU2.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/MjQ0ZWQzODYtMTJjNC00ZDU4LTlkOTgtZmVkMWI2NzRjMjNh.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/YjQ0YzQxZmItNmZjNy00ZGFmLWIyOWQtODdhMjdkN2VmMzhj.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/YTljY2E2ZjUtN2U4YS00NzA4LWJjNDEtMDE0ZWU4OWI4ZWM5.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/MzBiOWM0ZDQtNDI3OS00MGYyLWE2ZDktYmZiYjczOTA1NGFi.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/MDA0N2ZiNDctNTY3MS00M2ZlLWEzZGYtYzMzNjQ1ZWFmZGRj.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/MzZjMTIzYmUtODQ5NC00NmJjLTg1MjktMmM2MjZmMWJiYTAw.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/YzY2N2M5MTYtZDdlZS00ZTQyLWE2MGYtNjE1YzZhMWIwYzFk.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/Njg2YmYzN2EtODM1Ny00OGE1LTkwN2ItOTYwOGY3NDRiMTAw.svga");
        SVGAUrls.add("https://yjmf.bs2dl.yy.com/YmE1N2ViMjAtMTFmOC00ZTkwLTliYzktNjk0NGExMWU4ZWZh.svga");
    }

    private int mClickCount;

    private SVGAImageView mSVGAImageView;
    private SVGAParser mSVGAParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSVGAImageView = findViewById(R.id.svga);
        mSVGAParser = new SVGAParser(this);

//        loadAnimationFromAssets();

        loadAnimationFromNet(SVGAUrls.get(0));
    }

    /**
     * http://svga.io/intro.html
     */
    private void loadAnimationFromAssets() {
        mSVGAParser.decodeFromAssets("bid.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(SVGAVideoEntity mSVGAVideoEntity) {
                mSVGAImageView.setVisibility(View.VISIBLE);
                SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                SVGADrawable drawable = new SVGADrawable(mSVGAVideoEntity, dynamicEntity);

                //设置bitmap
                Bitmap bitmap = Util.generatePriceBitmap(MainActivity.this, 111L);
                dynamicEntity.setDynamicImage(bitmap, "price"); // Here is the KEY implementation.

                //设置文字
                /*TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(30);
                textPaint.setFakeBoldText(true);
                textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
                textPaint.setShadowLayer((float) 1.0, (float) 0.0, (float) 1.0, Color.BLUE); // 各种配置
                //解压.svga可以看到所有的图片，填入图片名称
                dynamicEntity.setDynamicText("TEXT!", textPaint, "price");*/

                mSVGAImageView.setImageDrawable(drawable);
                mSVGAImageView.startAnimation();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAnimationFromNet(String url) {
        try {
            mSVGAParser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity videoItem) {
                    mSVGAImageView.setVideoItem(videoItem);
                    mSVGAImageView.startAnimation();
                }

                @Override
                public void onError() {
                    Toast.makeText(MainActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "解析失败：" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void next(View view) {
        mClickCount++;
        loadAnimationFromNet(SVGAUrls.get(mClickCount % (SVGAUrls.size() - 1)));
    }
}
