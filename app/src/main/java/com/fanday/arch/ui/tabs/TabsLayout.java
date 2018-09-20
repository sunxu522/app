package com.fanday.arch.ui.tabs;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanday.arch.R;
import com.fanday.arch.library.utils.Kits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanday on 2018/3/1.
 */

public class TabsLayout extends RelativeLayout implements ViewPager.OnPageChangeListener {

    Context c;

    public TabsLayout(Context context) {
        this(context, null, 0);
    }

    public TabsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabsLayout);
        selectColor = ta.getColor(R.styleable.TabsLayout_selectColor, context.getResources().getColor(R.color.color_e60516));
        indicatorColor = ta.getColor(R.styleable.TabsLayout_indicatorColor, context.getResources().getColor(R.color.color_e60516));
        unSelectColor = ta.getColor(R.styleable.TabsLayout_unSelectColor, context.getResources().getColor(R.color.drak_gray));
        animDuring = ta.getInteger(R.styleable.TabsLayout_animDuring, 200);
        titleSize = ta.getDimensionPixelSize(R.styleable.TabsLayout_titleSize, 13);
        showBottomLine = ta.getBoolean(R.styleable.TabsLayout_showBottomLine, true);
        String xmlTitles = ta.getString(R.styleable.TabsLayout_titles);
        if (!TextUtils.isEmpty(xmlTitles))
            titles = xmlTitles.split(",");
        this.c = context;
        initTabContainer(context);
        initBottomLine();
        initIndicator(context);
        initTabs(context);
        postDeal();
        initListener();
        ta.recycle();
    }

    private void initBottomLine() {
        if(!showBottomLine)return;
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Kits.UIKits.getColor(R.color.color_e1e1e1));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        this.addView(bottomLine, lp);
    }

    private View indicator;
    private LinearLayout tabContainer;
    private String[] titles = new String[]{};
    private List<TextView> tabs = new ArrayList<>();
    private List<RelativeLayout> tabParent = new ArrayList<>();

    private int currentIndex = 0;

    private int selectColor = Color.RED;
    private int unSelectColor = Color.GRAY;
    private int indicatorColor = Color.RED;
    private float titleSize = 13;

    private int animDuring = 200;
    private boolean showBottomLine;

    public void setAnimDuring(int animDuring) {
        this.animDuring = animDuring;
    }

    public List<RelativeLayout> getTabParent() {
        return tabParent;
    }

    public List<TextView> getTabs() {
        return tabs;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).setTextSize(titleSize);
        }
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).setTextColor(i == 0 ? selectColor : unSelectColor);
        }
    }


    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).setTextColor(i == 0 ? selectColor : unSelectColor);
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        indicator.setBackgroundColor(indicatorColor);
    }

    public void setTitles(String... titles) {
        this.titles = titles;
        tabs.clear();
        tabParent.clear();
        tabContainer.removeAllViews();
        initTabs(getContext());
        postDeal();
        initListener();


    }

    private boolean isClick = false;

    private void initListener() {
        for (int i = 0; i < tabs.size(); i++) {
            final int position = i;
            TextView tab = tabs.get(i);
            tab.setTag(i);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (position == currentIndex)
                        return;

                    isClick = true;
                    if (listener != null)
                        listener.onSelect(position);

                    if(!isLaunchScrolling)
                        moveIndicator(position, true);
                }
            });
        }
    }


    private void postDeal() {
        this.post(new Runnable() {
            @Override
            public void run() {


                int itemWidth = getWidth() / titles.length;
                TextView tab = tabs.get(0);
                float textWidth = tab.getPaint().measureText(tab.getText().toString());
                int offsetX = (int) ((itemWidth - textWidth) / 2);

                ViewGroup.LayoutParams lp = indicator.getLayoutParams();
                lp.width = (int) textWidth;
                indicator.setLayoutParams(lp);
                indicator.setBackgroundColor(indicatorColor);
                indicator.setX(offsetX + itemWidth * currentIndex);
                fromWidth = textWidth * (currentIndex + 1);
                fromX = offsetX + itemWidth * currentIndex;

                for (int i = 0; i < tabs.size(); i++) {
                    tabs.get(i).setTextColor(i == currentIndex ? selectColor : unSelectColor);
                }

            }
        });

    }

    private float fromWidth;
    private float fromX;

    private boolean isLaunchScrolling = false;

    public void setUpWithViewPager(android.support.v4.view.ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);
        isLaunchScrolling = true;
    }

    public void setCurrentItem(final int index) {
        this.post(new Runnable() {
            @Override
            public void run() {
                getTabs().get(index).performClick();
                moveIndicator(index, true);
            }
        });
    }

    public void setIndex(int index){
        getTabParent().get(index).performClick();
        moveIndicator(index, false);
    }

    public void moveIndicator(final int index, boolean isAnimator) {
        final int itemWidth = getWidth() / titles.length;
        TextView tab = tabs.get(index);
        final float textWidth = tab.getPaint().measureText(tab.getText().toString());
        final int offsetX = (int) ((itemWidth - textWidth) / 2);
        final int toX = itemWidth * index + offsetX;
        final int toWidth = (int) textWidth;

        final ViewGroup.LayoutParams lp = indicator.getLayoutParams();
        final int fromWidth = lp.width;
        final int fromX = (int) indicator.getX();


        ValueAnimator va = ValueAnimator.ofFloat(0, 1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                int width = (int) (fromWidth + (toWidth - fromWidth) * fraction);
                int x = (int) (fromX + (toX - fromX) * fraction);
                lp.width = width;
                indicator.setLayoutParams(lp);
                indicator.setX(x);
                if (fraction == 1.0) {
                    TabsLayout.this.currentIndex = index;
                    TabsLayout.this.fromWidth = toWidth;
                    TabsLayout.this.fromX = toX;
                    isClick = false;
                }
            }
        });
        if(isAnimator){
            va.setDuration(animDuring);
        }else{
            va.setDuration(0);
        }
        va.setInterpolator(new LinearInterpolator());
        va.start();

        currentIndex = index;

        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).setTextColor(i == index ? selectColor : unSelectColor);
        }
    }


    private synchronized void setIndicatorPosition(float positionOffset, boolean isRight) {

        final int itemWidth = getWidth() / titles.length;
        //将要滑到的text位置文字长度等信息
        int toIndex = isRight ? currentIndex + 1 : currentIndex - 1;
        if (toIndex < 0 || toIndex > tabs.size() - 1)
            return;
        TextView tab = tabs.get(toIndex);
        final float textWidth = tab.getPaint().measureText(tab.getText().toString());
        final int offsetX = (int) ((itemWidth - textWidth) / 2);
        final int toWidth = (int) textWidth;

        //当前的指示器信息
        final ViewGroup.LayoutParams lp = indicator.getLayoutParams();
        final int toX = itemWidth * (toIndex) + offsetX;


        float fraction = isRight ? positionOffset : (1 - positionOffset);
        int width = (int) (fromWidth + (toWidth - fromWidth) * fraction);
        int x = (int) (fromX + (toX - fromX) * fraction);
        lp.width = width;
        indicator.setLayoutParams(lp);
        indicator.setX(x);

        if (fraction == 1.0) {
            currentIndex = toIndex;
            fromWidth = toWidth;
            fromX = toX;
            for (int i = 0; i < tabs.size(); i++) {
                tabs.get(i).setTextColor(i == toIndex ? selectColor : unSelectColor);
            }
        }

    }

    private void initTabs(Context context) {
        for (int i = 0; i < titles.length; i++) {
            TextView text = new TextView(context);
            text.setText(titles[i]);
            if (titleSize != 13) {
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            } else {
                text.setTextSize(titleSize);
            }
            text.setGravity(Gravity.CENTER);
            // 点击区域变小了
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            tabs.add(text);
            RelativeLayout rl = new RelativeLayout(context);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            llp.weight = 1;
            tabContainer.addView(rl, llp);
            rl.addView(text, lp);
            rl.setPadding(dp2px(15), 0, dp2px(15), 0);
            tabParent.add(rl);
        }
    }



    private void initTabContainer(Context context) {
        tabContainer = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(tabContainer, lp);
    }

    private void initIndicator(Context context) {
        indicator = new View(context);
        LayoutParams lp = new LayoutParams(0, dp2px(2));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        this.addView(indicator, lp);
    }


    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }


    private OnTabSelectListener listener;

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.listener = listener;
    }


    private boolean isRight = false;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0 || isClick)
            return;
        isRight = currentIndex == position;
        setIndicatorPosition(positionOffset, isRight);
    }


    @Override
    public void onPageSelected(int position) {
        if(isClick){
            moveIndicator(position, true);
        }else{
            setIndicatorPosition(isRight ? 1.0f : 0.0f, isRight);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public interface OnTabSelectListener {
        void onSelect(int index);
    }

}
