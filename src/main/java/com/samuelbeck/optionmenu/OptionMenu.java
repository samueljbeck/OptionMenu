package com.samuelbeck.optionmenu;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by samuelbeck on 12/2/15.
 */
public class OptionMenu {

    private OptionMenuInterface toolBarOptionMenuInterface;
    private OptionMenuStringInterface toolBarOptionStringMenuInterface;
    private Activity thisActivity;

    private LinearLayout optionLayout;
    private View optionHolder;
    private LinearLayout optionScrollHolder;
    private LinearLayout background;
    private ArrayList unclickableOptions;
    private ArrayList<String> disabledOptions;
    private Integer optionItemLayout;

    public OptionMenu(Activity _thisActivity, OptionMenuInterface _toolBarOptionMenuInterface, final ArrayList<String> options, final ArrayList<String> _unclickableOptions, final String _permissionDenied) {
        unclickableOptions = _unclickableOptions;
        thisActivity = _thisActivity;
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionMenuInterface, options, false);
    }

    public OptionMenu(Activity _thisActivity, OptionMenuInterface _toolBarOptionMenuInterface, final ArrayList<String> options) {
        thisActivity = _thisActivity;
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionMenuInterface, options, false);

    }

    public OptionMenu(Activity _thisActivity, OptionMenuStringInterface _toolBarOptionStringMenuInterface, final ArrayList<String> options) {
        thisActivity = _thisActivity;
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionStringMenuInterface, options, false);

    }

    public OptionMenu(Activity _thisActivity, OptionMenuInterface _toolBarOptionMenuInterface, final ArrayList<String> options, final ArrayList<String> _disabledOptions) {
        disabledOptions = _disabledOptions;
        thisActivity = _thisActivity;
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionMenuInterface, options, false);

    }

    public OptionMenu(Activity _thisActivity, OptionMenuInterface _toolBarOptionMenuInterface, final ArrayList<String> options, boolean avoidPadding) {
        thisActivity = _thisActivity;
        unclickableOptions = new ArrayList();
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionMenuInterface, options, avoidPadding);
    }

    public OptionMenu(Activity _thisActivity, OptionMenuStringInterface _toolBarOptionMenuInterface, final ArrayList<String> options, boolean avoidPadding, Integer optionItemLayout) {
        this.optionItemLayout = optionItemLayout;
        thisActivity = _thisActivity;
        unclickableOptions = new ArrayList();
        optionLayout = thisActivity.findViewById(R.id.toolbar_options_layout);
        optionScrollHolder = thisActivity.findViewById(R.id.option_scroll_holder);
        optionHolder = thisActivity.findViewById(R.id.options_holder);
        setOptionMenu(_toolBarOptionMenuInterface, options, avoidPadding);
    }
    public OptionMenu(Activity _thisActivity, final String[] _options, LinearLayout _optionLayout, ScrollView _optionScrollView, LinearLayout _optionHolder, LinearLayout _optionScrollHolder, LinearLayout _background) {
        thisActivity = _thisActivity;
        unclickableOptions = new ArrayList();
        optionLayout = _optionLayout;
        optionHolder = _optionHolder;
        optionScrollHolder = _optionScrollHolder;
        background = _background;
        background.setOnClickListener(v -> close());
        setOptionMenu((OptionMenuInterface)_thisActivity, new ArrayList<>(Arrays.asList(_options)), false);
        close();
    }

    private void setOptionMenu(OptionMenuInterface _toolBarOptionMenuInterface, final ArrayList<String> options, boolean avoidPadding) {
        toolBarOptionMenuInterface = _toolBarOptionMenuInterface;
        RelativeLayout.LayoutParams toolBarLayoutParams = (RelativeLayout.LayoutParams)optionHolder.getLayoutParams();

        // avoidPadding is specific for optionHolder not optionItemHolder but the value is also true for optionItemHolder
        if (!avoidPadding) {
            thisActivity.runOnUiThread(() -> toolBarLayoutParams.setMargins(0, 15, 15, 0));
        }

        thisActivity.runOnUiThread(() -> {
            optionHolder.setLayoutParams(toolBarLayoutParams);
            optionScrollHolder.removeAllViews();

        });
        for (String s : options) {
            View option = thisActivity.getLayoutInflater().inflate(optionItemLayout == null ? R.layout.option_item : optionItemLayout, null);
            LinearLayout optionItemHolder = option.findViewById(R.id.options_item_holder);
            TextView optionText = option.findViewById(R.id.option_item_text);
            if (unclickableOptions != null && unclickableOptions.contains(s)) {
                thisActivity.runOnUiThread(() -> {
                    optionText.setText(Html.fromHtml(s + " " + thisActivity.getResources().getString(R.string.option_denied)));
                    optionItemHolder.setClickable(false);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_54));

                });
            } else if (disabledOptions != null && disabledOptions.contains(s)) {
                thisActivity.runOnUiThread(() -> {
                    optionText.setText(s);
                    optionItemHolder.setClickable(true);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_54));

                });
                optionItemHolder.setOnClickListener(v -> {
                    String s1 = (String)v.getTag();

                    options.indexOf(s1);
                    toolBarOptionMenuInterface.optionMenuSelect(options.indexOf(s1));
                    close();

                });
            } else {

                thisActivity.runOnUiThread(() -> {
                    optionText.setText(s);
                    optionItemHolder.setClickable(true);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_87));

                });
                optionItemHolder.setOnClickListener(v -> {
                    String s1 = (String)v.getTag();

                    options.indexOf(s1);
                    toolBarOptionMenuInterface.optionMenuSelect(options.indexOf(s1));
                    close();

                });
            }

            if (avoidPadding) {
                // probably not the best option.. but reduces the width of the item
                thisActivity.runOnUiThread(() -> optionItemHolder.setPadding(0, 0, 32, 0));
            }

            optionItemHolder.setTag(s);

            thisActivity.runOnUiThread(() -> optionScrollHolder.addView(option));

        }
        optionLayout.setOnClickListener(v -> close());




    }


    private void setOptionMenu(OptionMenuStringInterface _toolBarOptionStringMenuInterface, final ArrayList<String> options, boolean avoidPadding) {
        toolBarOptionStringMenuInterface = _toolBarOptionStringMenuInterface;
        RelativeLayout.LayoutParams toolBarLayoutParams = (RelativeLayout.LayoutParams)optionHolder.getLayoutParams();

        // avoidPadding is specific for optionHolder not optionItemHolder but the value is also true for optionItemHolder
        if (!avoidPadding) {
            thisActivity.runOnUiThread(() -> toolBarLayoutParams.setMargins(0, 15, 15, 0));
        }

        thisActivity.runOnUiThread(() -> {
            optionHolder.setLayoutParams(toolBarLayoutParams);
            optionScrollHolder.removeAllViews();

        });
        for (String s : options) {
            View option = thisActivity.getLayoutInflater().inflate(optionItemLayout == null ? R.layout.option_item : optionItemLayout, null);
            LinearLayout optionItemHolder = option.findViewById(R.id.options_item_holder);
            TextView optionText = option.findViewById(R.id.option_item_text);
            if (unclickableOptions != null && unclickableOptions.contains(s)) {
                thisActivity.runOnUiThread(() -> {
                    optionText.setText(Html.fromHtml(s + " " + thisActivity.getResources().getString(R.string.option_denied).replace(" ", "&nbsp;")));
                    optionItemHolder.setClickable(false);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_54));

                });
            } else if (disabledOptions != null && disabledOptions.contains(s)) {
                thisActivity.runOnUiThread(() -> {
                    optionText.setText(s);
                    optionItemHolder.setClickable(true);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_54));

                });
                optionItemHolder.setOnClickListener(v -> {
                    String s1 = (String)v.getTag();

                    options.indexOf(s1);
                    toolBarOptionStringMenuInterface.optionMenuSelect(s);
                    close();

                });
            } else {

                thisActivity.runOnUiThread(() -> {
                    optionText.setText(s);
                    optionItemHolder.setClickable(true);
                    optionText.setTextColor(ContextCompat.getColor(thisActivity,R.color.black_87));

                });
                optionItemHolder.setOnClickListener(v -> {
                    String s1 = (String)v.getTag();

                    options.indexOf(s1);
                    toolBarOptionStringMenuInterface.optionMenuSelect(s);
                    close();

                });
            }

            if (avoidPadding) {
                // probably not the best option.. but reduces the width of the item
                thisActivity.runOnUiThread(() -> optionItemHolder.setPadding(0, 0, 32, 0));
            }

            optionItemHolder.setTag(s);

            thisActivity.runOnUiThread(() -> optionScrollHolder.addView(option));

        }
        optionLayout.setOnClickListener(v -> close());




    }

    public void close() {
        thisActivity.runOnUiThread(() -> {
            optionLayout.setVisibility(View.INVISIBLE);
            if (background != null) {
                background.setVisibility(View.INVISIBLE);
            }

        });

    }

    public void open(){

        thisActivity.runOnUiThread(() -> {
            optionLayout.setVisibility(View.VISIBLE);
            if (background != null) {
                background.setVisibility(View.VISIBLE);
            }

        });
    }

    public void openWithSelection(String selection) {
        for (int i = 0; i < optionScrollHolder.getChildCount(); i++) {
            View v = optionScrollHolder.getChildAt(i);
            LinearLayout optionItemHolder = (LinearLayout)v.findViewById(R.id.options_item_holder);
            LinearLayout optionLayout = (LinearLayout)v.findViewById(R.id.options_layout);
            String tag = (String)optionItemHolder.getTag();
            if (tag != null && tag.equals(selection)) {
                thisActivity.runOnUiThread(() -> optionLayout.setBackgroundColor(ContextCompat.getColor(thisActivity, R.color.background_gray)));
            } else {
                thisActivity.runOnUiThread(() -> optionLayout.setBackgroundColor(ContextCompat.getColor(thisActivity, R.color.white_100)));
            }
        }
        InputMethodManager imm = (InputMethodManager)thisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(optionScrollHolder.getWindowToken(), 0);

        open();
    }

    public boolean isOpen() {
        return optionLayout.getVisibility() == View.VISIBLE;
    }



}
