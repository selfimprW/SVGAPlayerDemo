package com.selfimpr.svgaplayerdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * description：   <br/>
 * ===============================<br/>
 * creator：Jiacheng<br/>
 * create time：2019-10-29 17:40<br/>
 * ===============================<br/>
 * reasons for modification：  <br/>
 * Modifier：  <br/>
 * Modify time：  <br/>
 */
public class Util {
    public static final Bitmap generatePriceBitmap(Context context, long price) {
        String priceStr = convertPriceToStr(price);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        Bitmap decodeRMBResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.op_ic_auction_bidding_num_rmb);
        int width = decodeRMBResource.getWidth();
        int height = decodeRMBResource.getHeight();
        bitmaps.add(decodeRMBResource);
        int wholeWidth = width;
        int drawableId;
        for (int i = 0, length = priceStr.length(); i < length; i++) {
            switch (priceStr.charAt(i)) {
                case '.':
                    drawableId = R.drawable.op_ic_auction_bidding_num_dot;
                    break;
                case '0':
                    drawableId = R.drawable.op_ic_auction_bidding_num_0;
                    break;
                case '1':
                    drawableId = R.drawable.op_ic_auction_bidding_num_1;
                    break;
                case '2':
                    drawableId = R.drawable.op_ic_auction_bidding_num_2;
                    break;
                case '3':
                    drawableId = R.drawable.op_ic_auction_bidding_num_3;
                    break;
                case '4':
                    drawableId = R.drawable.op_ic_auction_bidding_num_4;
                    break;
                case '5':
                    drawableId = R.drawable.op_ic_auction_bidding_num_5;
                    break;
                case '6':
                    drawableId = R.drawable.op_ic_auction_bidding_num_6;
                    break;
                case '7':
                    drawableId = R.drawable.op_ic_auction_bidding_num_7;
                    break;
                case '8':
                    drawableId = R.drawable.op_ic_auction_bidding_num_8;
                    break;
                case '9':
                    drawableId = R.drawable.op_ic_auction_bidding_num_9;
                    break;
                default:
                    drawableId = 0;
                    break;
            }
            if (drawableId > 0) {
                Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), drawableId);
                wholeWidth += decodeResource.getWidth();
                bitmaps.add(decodeResource);
            }
        }
        float f2 = 100F;
        float f3 = (height * 1.0f) / f2;
        float f4 = 600 * f3;
        Bitmap createBitmap = Bitmap.createBitmap((int) f4, (int) (f2 * f3), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f5 = (f4 - wholeWidth) / 2.0f;
        for (Bitmap bitmap : bitmaps) {
            canvas.drawBitmap(bitmap, f5, 0.0f, null);
            f5 += bitmap.getWidth();
        }
        Matrix matrix = new Matrix();
        float f6 = ((float) 1) / f3;
        matrix.postScale(f6, f6);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, createBitmap.getWidth(), createBitmap.getHeight(), matrix, true);
        return createBitmap2;
    }

    /**
     * @param price 单位是分
     * @return
     */
    public static String convertPriceToStr(long price) {
        StringBuilder sb = new StringBuilder();
        sb.append(integer(price));
        sb.append(decimal(price));
        return sb.toString();
    }

    public static String integer(long price) {
        return String.valueOf(price / 100);
    }

    public static String decimal(long price) {
        long decimal = price % 100;
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        if (decimal >= 10) {
            sb.append(decimal);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("0");
            sb2.append(decimal);
        }
        return sb.toString();
    }
}
