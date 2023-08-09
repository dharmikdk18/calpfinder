package com.app.sdkads.adsType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.browser.customtabs.CustomTabsIntent;

import com.app.sdkads.App;
import com.app.sdkads.R;
import com.app.sdkads.utils.Const;
import com.app.sdkads.utils.utils;

import java.util.Random;

public class Interstitial_Qureka {

    public interface OnclickInter {
        void clicked();
    }

    public static void showInterstitial(Activity activity, OnclickInter onclickInter) {
        if (new Random().nextInt(2) + 1 == 2) {
            Dialog qurekaDialog = new Dialog(activity, android.R.style.Theme_DeviceDefault);
            qurekaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            qurekaDialog.setContentView(R.layout.qureka_interstial_ads);
            qurekaDialog.setCancelable(false);
            qurekaDialog.show();

            final int random_1 = new Random().nextInt((6 - 1) + 1) + 1;

            RelativeLayout qtop = qurekaDialog.findViewById(R.id.qtop);
            RelativeLayout qbottom = qurekaDialog.findViewById(R.id.qbottom);
            RelativeLayout qintclose = qurekaDialog.findViewById(R.id.qintclose);
            ImageView imgqint_1 = qurekaDialog.findViewById(R.id.imgqint1);
            ImageView imgblur = qurekaDialog.findViewById(R.id.imgblur);

            qtop.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down));
            qbottom.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));

            if (random_1 == 1) {
                imgqint_1.setBackgroundResource(R.drawable.q_int1);
                imgblur.setBackgroundResource(R.drawable.q_int1);
            } else if (random_1 == 2) {
                imgqint_1.setBackgroundResource(R.drawable.q_int2);
                imgblur.setBackgroundResource(R.drawable.q_int2);
            } else if (random_1 == 3) {
                imgqint_1.setBackgroundResource(R.drawable.q_int3);
                imgblur.setBackgroundResource(R.drawable.q_int3);
            } else if (random_1 == 4) {
                imgqint_1.setBackgroundResource(R.drawable.q_int4);
                imgblur.setBackgroundResource(R.drawable.q_int4);
            } else if (random_1 == 5) {
                imgqint_1.setBackgroundResource(R.drawable.q_int5);
                imgblur.setBackgroundResource(R.drawable.q_int5);
            } else {
                imgqint_1.setBackgroundResource(R.drawable.q_int6);
                imgblur.setBackgroundResource(R.drawable.q_int6);
            }

            qtop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog(activity,qurekaDialog, onclickInter);
                }
            });

            qbottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog(activity,qurekaDialog, onclickInter);
                }
            });

            qintclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (qurekaDialog != null) {
                                qurekaDialog.dismiss();
                                onclickInter.clicked();
                            }
                        }
                    }, 250);
                }
            });

        } else {
            Dialog qurekaDialog = new Dialog(activity, android.R.style.Theme_DeviceDefault);
            qurekaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            qurekaDialog.setContentView(R.layout.qureka_interstial_ads2);
            qurekaDialog.setCancelable(false);
            qurekaDialog.show();

            RelativeLayout qtop = qurekaDialog.findViewById(R.id.qtop);
            RelativeLayout qbottom = qurekaDialog.findViewById(R.id.qbottom);
            RelativeLayout qintclose = qurekaDialog.findViewById(R.id.qintclose);
            ImageView imgqint_2 = qurekaDialog.findViewById(R.id.imgqint2);

            qbottom.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));

            final int random_2 = new Random().nextInt((6 - 1) + 1) + 1;
            Log.d("random_q_int :2", random_2 + "");
            if (random_2 == 1) {
                imgqint_2.setBackgroundResource(R.drawable.q_int1);
            } else if (random_2 == 2) {
                imgqint_2.setBackgroundResource(R.drawable.q_int2);
            } else if (random_2 == 3) {
                imgqint_2.setBackgroundResource(R.drawable.q_int3);
            } else if (random_2 == 4) {
                imgqint_2.setBackgroundResource(R.drawable.q_int4);
            } else if (random_2 == 5) {
                imgqint_2.setBackgroundResource(R.drawable.q_int5);
            } else {
                imgqint_2.setBackgroundResource(R.drawable.q_int6);
            }

            qtop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog(activity,qurekaDialog, onclickInter);
                }
            });

            qbottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog(activity,qurekaDialog, onclickInter);
                }
            });

            qintclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            qurekaDialog.dismiss();
                            onclickInter.clicked();
                        }
                    }, 250);

                }
            });

        }
    }

    private static void closeDialog(Activity activity,Dialog qurekaDialog, OnclickInter onclickInter) {
        onclickInter.clicked();
        utils.qurekaClick(activity, App.getString(Const.Qureka_link));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                qurekaDialog.dismiss();
            }
        }, 250);
    }


}