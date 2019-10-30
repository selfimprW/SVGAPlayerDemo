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

public class MainActivity extends AppCompatActivity {
    private SVGAImageView mSVGAImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://yjmf.bs2dl.yy.com/Yjk3YTdlNDEtNzFhZC00ZTljLWE1NTAtMTA1OGZhYmM5ZTU2.svga";
        url = "https://yjmf.bs2dl.yy.com/MjQ0ZWQzODYtMTJjNC00ZDU4LTlkOTgtZmVkMWI2NzRjMjNh.svga";

        loadAnimation();
    }

    /**
     * http://svga.io/intro.html
     */
    private void loadAnimation() {
        mSVGAImageView = findViewById(R.id.svga);
        SVGAParser svgaParser = new SVGAParser(this);
        svgaParser.decodeFromAssets("bid.svga", new SVGAParser.ParseCompletion() {
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

        /*try { // new URL needs try catch.
            svgaParser.parse(new URL("http://github.com/yyued/SVGAPlayer-Android/blob/master/app/src/main/assets/heartbeat.svga"), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
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
        }*/
    }
}
