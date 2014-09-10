package com.savinoordine.menuanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;


public class MyActivity extends Activity {

    private static final String IS_MENU_OPENED = "IS_MENU_OPENED";
    private static final String  ANIMATION1 = "ANIMATION1";
    private static final String  ANIMATION2 = "ANIMATION2";
    private static final String  ANIMATION3 = "ANIMATION3";
    private static final String  CHECKBOX_ROTATION = "CHECKBOX_ROTATION";

    private boolean menuRotated;

    Button menu;

    Button menuButton1;
    Button menuButton2;
    Button menuButton3;

    private static final int SHORT_ANIMATION_TIME = 200;
    private static final int MEDIUM_ANIMATION_TIME = 300;
    private static final int LONG_ANIMATION_TIME = 400;

    int animation_button1;
    int animation_button2;
    int animation_button3;

    private EditText animation_button1_button;
    private EditText animation_button2_button;
    private EditText animation_button3_button;

    int px;

    AnimatorSet animatorSetClose;
    AnimatorSet animatorSetOpen;
    private CheckBox rotationAnimationCheckbox;
    private boolean rotationButtonEnabled;

    public MyActivity() {
        this.animation_button1 = LONG_ANIMATION_TIME;
        this.animation_button2 = MEDIUM_ANIMATION_TIME;
        this.animation_button3 = SHORT_ANIMATION_TIME;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        menu = (Button) findViewById(R.id.ic_main_menu);

        menuButton1 = (Button) findViewById(R.id.layout_button_1);
        menuButton2 = (Button) findViewById(R.id.menu_button_2);
        menuButton3 = (Button) findViewById(R.id.menu_button_3);

        animation_button1_button = (EditText) findViewById(R.id.animation_button_1);
        animation_button2_button = (EditText) findViewById(R.id.animation_button_2);
        animation_button3_button = (EditText) findViewById(R.id.animation_button_3);

        final RotateAnimation openRotateAnimation = setOpenMenuAnimation();
        final RotateAnimation closeRotateAnimation = setCloseMenuAnimation();

        rotationAnimationCheckbox = (CheckBox) findViewById(R.id.rotation_checkbox);
        rotationAnimationCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rotationButtonEnabled = b;
                animatorSetOpen = setAnimatorOpen();
            }
        });

        if (savedInstanceState != null) {
            menuRotated = savedInstanceState.getBoolean(IS_MENU_OPENED);
            animation_button1 = savedInstanceState.getInt(ANIMATION1);
            animation_button2 = savedInstanceState.getInt(ANIMATION2);
            animation_button3 = savedInstanceState.getInt(ANIMATION3);
            rotationAnimationCheckbox.setChecked(savedInstanceState.getBoolean(CHECKBOX_ROTATION));

            menuRotated = false;
        }


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        px = (int) Math.ceil(getResources().getDimension(R.dimen.dim78px) * logicalDensity);

        animatorSetOpen = setAnimatorOpen();
        animatorSetClose = setAnimatorClose();

        // Change button animation values
        Button set_value_button = (Button) findViewById(R.id.set_value_button);
        set_value_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation_button1 = (animation_button1_button.getText().length() > 0) ? Integer.parseInt(animation_button1_button.getText().toString()) : LONG_ANIMATION_TIME;
                animation_button2 = (animation_button2_button.getText().length() > 0) ? Integer.parseInt(animation_button2_button.getText().toString()) : MEDIUM_ANIMATION_TIME;
                animation_button3 = (animation_button3_button.getText().length() > 0) ? Integer.parseInt(animation_button3_button.getText().toString()) : SHORT_ANIMATION_TIME;

                animatorSetOpen = setAnimatorOpen();
                animatorSetClose = setAnimatorClose();
            }
        });

        // Default animation values
        Button default_value_button = (Button) findViewById(R.id.reset_default_value_button);
        default_value_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation_button1 = LONG_ANIMATION_TIME;
                animation_button2 = MEDIUM_ANIMATION_TIME;
                animation_button3 = SHORT_ANIMATION_TIME;

                animation_button1_button.setText("");
                animation_button2_button.setText("");
                animation_button3_button.setText("");

                animatorSetOpen = setAnimatorOpen();
                animatorSetClose = setAnimatorClose();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setClickable(false);

                if (!menuRotated) {
                    showMenuButtons();
                    animatorSetOpen.start();
                    view.startAnimation(openRotateAnimation);
                    menuRotated = true;

                } else {
                    setNoShadowToButtons();
                    animatorSetClose.start();
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
        outState.putInt(ANIMATION1, animation_button1);
        outState.putInt(ANIMATION2, animation_button2);
        outState.putInt(ANIMATION3, animation_button3);
        outState.putBoolean(CHECKBOX_ROTATION, rotationButtonEnabled);
    }


    private AnimatorSet setAnimatorClose() {

        AnimatorSet animation = new AnimatorSet();

        animation.playTogether(
                getMenuButtonAnimation(menuButton1, -px * 3, 0f, animation_button1),
                getMenuButtonAnimation(menuButton2, -px * 2, 0f, animation_button2),
                getMenuButtonAnimation(menuButton3, -px , 0f, animation_button3));

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                menu.setClickable(true);
                hideMenuButtons();

            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        return animation;
    }

    private AnimatorSet setAnimatorOpen() {

        AnimatorSet animation = new AnimatorSet();

        if (rotationButtonEnabled) {

            Keyframe kf0 = Keyframe.ofFloat(0f, 1f);
            Keyframe kf1 = Keyframe.ofFloat(0f, 360f);
            Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
            PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);

            ObjectAnimator rotationAnimButton1 = ObjectAnimator.ofPropertyValuesHolder(menuButton1, pvhRotation);
            ObjectAnimator.ofPropertyValuesHolder(menuButton1, pvhRotation);
            rotationAnimButton1.setDuration(animation_button1);

            AnimatorSet setButton1 = new AnimatorSet();
            setButton1.playTogether(
                    getMenuButtonAnimation(menuButton1, 0f, -px * 3, animation_button1),
                    rotationAnimButton1
            );

            ObjectAnimator rotationAnimButton2 = ObjectAnimator.ofPropertyValuesHolder(menuButton2, pvhRotation);
            ObjectAnimator.ofPropertyValuesHolder(menuButton2, pvhRotation);
            rotationAnimButton2.setDuration(animation_button2);

            AnimatorSet setButton2 = new AnimatorSet();
            setButton1.playTogether(
                    getMenuButtonAnimation(menuButton2, 0f, -px * 2, animation_button2),
                    rotationAnimButton2
            );

            ObjectAnimator rotationAnimButton3 = ObjectAnimator.ofPropertyValuesHolder(menuButton3, pvhRotation);
            ObjectAnimator.ofPropertyValuesHolder(menuButton3, pvhRotation);
            rotationAnimButton3.setDuration(animation_button3);

            AnimatorSet setButton3 = new AnimatorSet();
            setButton1.playTogether(
                    getMenuButtonAnimation(menuButton3, 0f, -px, animation_button3),
                    rotationAnimButton3
            );

            animation.playTogether(
                    setButton1,
                    setButton2,
                    setButton3);

        } else {

            animation.playTogether(
                    getMenuButtonAnimation(menuButton1, 0f, -px * 3, animation_button1),
                    getMenuButtonAnimation(menuButton2, 0f, -px * 2, animation_button2),
                    getMenuButtonAnimation(menuButton3, 0f, -px, animation_button3));
        }

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                setShadowToButtons();
                menu.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        return animation;
    }

    private void setNoShadowToButtons() {

        menuButton1.setBackground(getResources().getDrawable(R.drawable.ic_menu_no_shadow));
        menuButton2.setBackground(getResources().getDrawable(R.drawable.ic_menu_no_shadow));
        menuButton3.setBackground(getResources().getDrawable(R.drawable.ic_menu_no_shadow));
    }

    private void setShadowToButtons() {

        menuButton1.setBackground(getResources().getDrawable(R.drawable.ic_menu));
        menuButton2.setBackground(getResources().getDrawable(R.drawable.ic_menu));
        menuButton3.setBackground(getResources().getDrawable(R.drawable.ic_menu));
    }

    private void showMenuButtons() {

        menuButton1.setVisibility(View.VISIBLE);
        menuButton2.setVisibility(View.VISIBLE);
        menuButton3.setVisibility(View.VISIBLE);
    }

    private void hideMenuButtons() {

        menuButton1.setVisibility(View.GONE);
        menuButton2.setVisibility(View.GONE);
        menuButton3.setVisibility(View.GONE);
    }

    //TODO:  gestire la rotazione

    private ObjectAnimator getMenuButtonAnimation(Button button, float from, float to, int time) {

        ObjectAnimator objectAnimator;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            objectAnimator = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, from, to);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(time);

        } else {
            objectAnimator = ObjectAnimator.ofFloat(button, View.TRANSLATION_X, from, to);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(time);
        }

        return objectAnimator;
    }

    private RotateAnimation setOpenMenuAnimation() {

        RotateAnimation openRotateAnimation = new RotateAnimation(0, 135, Animation.RELATIVE_TO_SELF,
                0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        openRotateAnimation.setDuration(LONG_ANIMATION_TIME);
        openRotateAnimation.setFillAfter(true);
        openRotateAnimation.setFillEnabled(true);

        return openRotateAnimation;
    }

    private RotateAnimation setCloseMenuAnimation() {

        RotateAnimation closeRotateAnimation = new RotateAnimation(135, 0, Animation.RELATIVE_TO_SELF,
                0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        closeRotateAnimation.setDuration(LONG_ANIMATION_TIME);
        closeRotateAnimation.setFillAfter(true);
        closeRotateAnimation.setFillEnabled(true);

        return closeRotateAnimation;
    }
}
