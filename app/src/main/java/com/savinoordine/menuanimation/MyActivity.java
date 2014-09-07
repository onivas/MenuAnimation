package com.savinoordine.menuanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MyActivity extends Activity {

    private static final String IS_MENU_OPENED = "IS_MENU_OPENED";

    private boolean menuRotated;

    Button menu;

    FrameLayout container;

    private static final int SHORT_ANIMATION_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final LinearLayout menuItemsLayout = (LinearLayout) findViewById(R.id.menu_items);

        container = (FrameLayout) findViewById(R.id.container);

        menu = (Button) findViewById(R.id.ic_main_menu);

        final RotateAnimation openRotateAnimation = setOpenMenuAnimation();
        final RotateAnimation closeRotateAnimation = setCloseMenuAnimation();

        final ObjectAnimator menuItemsShowAnimation = getMenuItemsShowAnimation(menuItemsLayout);
        final ObjectAnimator menuItemsHideAnimation = getMenuItemsHideAnimation(menuItemsLayout);

        if (savedInstanceState != null) {
            menuRotated = savedInstanceState.getBoolean(IS_MENU_OPENED);
            // TODO: set image upside-down --- Animation is correct!

            menu.startAnimation(openRotateAnimation);

            menuItemsLayout.setVisibility(View.VISIBLE);

        }

        menuItemsShowAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                menu.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        menuItemsHideAnimation.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                menuItemsLayout.setVisibility(View.GONE);
                menu.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setClickable(false);

                if (!menuRotated) {
                    menuItemsLayout.setVisibility(View.VISIBLE);
                    menuItemsShowAnimation.start();
                    view.startAnimation(openRotateAnimation);
                    menuRotated = true;

                } else {
                    menuItemsHideAnimation.start();
                    view.startAnimation(closeRotateAnimation);
                    menuRotated = false;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(IS_MENU_OPENED, menuRotated);
    }

    private ObjectAnimator getMenuItemsHideAnimation(LinearLayout menuItems) {

        ObjectAnimator menuItemsHideAnimation;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            menuItemsHideAnimation = ObjectAnimator.ofFloat(menuItems, View.TRANSLATION_Y, 0f, 500f);
            menuItemsHideAnimation.setDuration(SHORT_ANIMATION_TIME);

        } else {
            menuItemsHideAnimation = ObjectAnimator.ofFloat(menuItems, View.TRANSLATION_X, 0f, 500f);
            menuItemsHideAnimation.setDuration(SHORT_ANIMATION_TIME);
        }

        return menuItemsHideAnimation;
    }

    private ObjectAnimator getMenuItemsShowAnimation(LinearLayout menuItems) {

        ObjectAnimator menuItemsShowAnimation;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            menuItemsShowAnimation = ObjectAnimator.ofFloat(menuItems, View.TRANSLATION_Y, 500f, 0f, 20f, 0f);
            menuItemsShowAnimation.setDuration(SHORT_ANIMATION_TIME);

        } else {
            menuItemsShowAnimation = ObjectAnimator.ofFloat(menuItems, View.TRANSLATION_X, 500f, 0f, 20f, 0f);
            menuItemsShowAnimation.setDuration(SHORT_ANIMATION_TIME);
        }
        return menuItemsShowAnimation;
    }

    private RotateAnimation setOpenMenuAnimation() {

        RotateAnimation openRotateAnimation = new RotateAnimation(0, 135, Animation.RELATIVE_TO_SELF,
                0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        openRotateAnimation.setDuration(SHORT_ANIMATION_TIME);
        openRotateAnimation.setFillAfter(true);
        openRotateAnimation.setFillEnabled(true);

        return openRotateAnimation;
    }

    private RotateAnimation setCloseMenuAnimation() {

        RotateAnimation closeRotateAnimation = new RotateAnimation(135, 0, Animation.RELATIVE_TO_SELF,
                0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        closeRotateAnimation.setDuration(SHORT_ANIMATION_TIME);
        closeRotateAnimation.setFillAfter(true);
        closeRotateAnimation.setFillEnabled(true);

        return closeRotateAnimation;
    }
}
