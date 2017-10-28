package com.tsubasa.listpopwindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tsubasa.listpopwindow.util.ScreenExtKt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义得popWindow
 * Created by tsubasa on 2017/3/16.
 */
@SuppressWarnings("ALL")
public class ListPopWindow {

    private Builder builder;

    private int popX;
    private int popY;

    private ListPopWindow() {
    }

    public void show() {
        if (builder.bgShade) {
            // 变更背景颜色
            Activity context = (Activity) builder.attachView.getContext();
            final Window window = context.getWindow();
            final WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 0.5f;
            window.setAttributes(attributes);
            // 消失时变回正常
            builder.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    attributes.alpha = 1f;
                    window.setAttributes(attributes);
                    builder.popupWindow.setOnDismissListener(null);
                }
            });
        }
        builder.popupWindow.showAtLocation(builder.attachView.getRootView(), Gravity.START | Gravity.TOP, popX, popY);
    }

    public static class Builder {

        public static final int LEFT = 1;
        public static final int RIGHT = 2;
        public static final int CENTER = 3;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({LEFT, RIGHT, CENTER})
        public @interface HorizontalPosition {
        }

        private Context context;

        private View attachView;

        private List<IconParam> list = new ArrayList<>();

        private boolean bgShade;

        private int reversalOffY;

        private int backgroundColor;

        private int itemHeight;

        private int itemWidth;

        private int mDividerMarginLeft;

        private int mDividerMarginRight;

        private int mDividerHeight;

        private int mPopIconTintColor;

        private int popOffY;

        private int popOffX;

        private int arrowContentOffX;

        private int marginLeft;

        private int marginRight;

        private int cornerRadius;

        private int itemPaddingLeft;

        private int itemPaddingRight;

        private int iconSize;

        private int itemGravity;

        private float textSize;

        private int textColor;

        private int textImageMargin;

        private boolean focus;

        private boolean isOutside;

        private int backgroundPressedColor;

        private int popGravity;

        private int mDividerColor;

        private PopupWindow popupWindow;

        public Builder(View attachView) {
            this.attachView = attachView;
            this.context = attachView.getContext();

            this.backgroundColor = ContextCompat.getColor(context, R.color.pop_window_background_color);
            this.backgroundPressedColor = 0xFFEEEEEE;
            this.itemWidth = ScreenExtKt.dp2px(context, 300);
            this.itemHeight = ScreenExtKt.dp2px(context, 100);
            this.reversalOffY = (int) (ScreenExtKt.getScreenHeight(context) * 0.8);
            this.cornerRadius = ScreenExtKt.dp2px(context, 5);
            this.itemPaddingLeft = this.itemPaddingRight = ScreenExtKt.dp2px(context, 30);
            this.textSize = ScreenExtKt.dp2px(context, 34);
            this.textColor = Color.BLACK;
            this.itemGravity = Gravity.CENTER_VERTICAL;
            this.textImageMargin = ScreenExtKt.dp2px(context, 10);
            this.iconSize = ScreenExtKt.dp2px(context, 30);
            this.focus = true;
            this.popOffY = ScreenExtKt.dp2px(context, 10);
            this.isOutside = true;
            this.bgShade = true;
            this.popGravity = CENTER;
        }

        public Builder withStyle(@StyleRes int styleId) {
            if (styleId != 0) {
                ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, styleId);
                TypedArray typedArray = themeWrapper.obtainStyledAttributes(new int[]{
                        R.attr.popUnitAutoAdaptive,
                        R.attr.popArrowContentOffX,
                        R.attr.popCornerRadius,
                        R.attr.popIconSize,
                        R.attr.popItemHeight,
                        R.attr.popItemWidth,
                        R.attr.popItemPaddingLeft,
                        R.attr.popItemPaddingRight,
                        R.attr.popOffX,
                        R.attr.popOffY,
                        R.attr.popItemReversalOffY,
                        R.attr.popMinMarginLeft,
                        R.attr.popMinMarginRight,
                        R.attr.popTextSize,
                        R.attr.popTextImageMargin,
                        R.attr.popBackgroundColor,
                        R.attr.popPressedBackgroundColor,
                        R.attr.popTextColor,
                        R.attr.popBgShade,
                        R.attr.popFocus,
                        R.attr.popGravity,
                        R.attr.popIsOutside,
                        R.attr.popItemGravity,
                        R.attr.popDividerColor,
                        R.attr.popDividerMarginLeft,
                        R.attr.popDividerMarginRight,
                        R.attr.popDividerHeight,
                        R.attr.popIconTintColor,
                });
                boolean autoAdaptive = typedArray.getBoolean(0, true);
                arrowContentOffX = coverDimen2Px(typedArray, 1, 0, autoAdaptive);
                cornerRadius = coverDimen2Px(typedArray, 2, ScreenExtKt.dp2px(context, 5), autoAdaptive);
                iconSize = coverDimen2Px(typedArray, 3, ScreenExtKt.dp2px(context, 40), autoAdaptive);
                itemHeight = coverDimen2Px(typedArray, 4, ScreenExtKt.dp2px(context, 100), autoAdaptive);
                itemWidth = coverDimen2Px(typedArray, 5, ScreenExtKt.dp2px(context, 300), autoAdaptive);
                itemPaddingLeft = coverDimen2Px(typedArray, 6, ScreenExtKt.dp2px(context, 30), autoAdaptive);
                itemPaddingRight = coverDimen2Px(typedArray, 7, ScreenExtKt.dp2px(context, 30), autoAdaptive);
                popOffX = coverDimen2Px(typedArray, 8, 0, autoAdaptive);
                popOffY = coverDimen2Px(typedArray, 9, 0, autoAdaptive);
                reversalOffY = (int) (typedArray.getFloat(10, 0.8f) * ScreenExtKt.getScreenHeight(context));
                marginLeft = coverDimen2Px(typedArray, 11, ScreenExtKt.dp2px(context, 30), autoAdaptive);
                marginRight = coverDimen2Px(typedArray, 12, ScreenExtKt.dp2px(context, 0), autoAdaptive);
                textSize = coverDimen2Px(typedArray, 13, ScreenExtKt.dp2px(context, 34), autoAdaptive);
                textImageMargin = coverDimen2Px(typedArray, 14, ScreenExtKt.dp2px(context, 20), autoAdaptive);

                backgroundColor = typedArray.getColor(15, Color.WHITE);
                backgroundPressedColor = typedArray.getColor(16, 0xffaaaaaa);
                textColor = typedArray.getColor(17, 0xff333333);

                bgShade = typedArray.getBoolean(18, true);
                focus = typedArray.getBoolean(19, true);
                isOutside = typedArray.getBoolean(20, true);
                popGravity = typedArray.getInt(21, CENTER);
                itemGravity = typedArray.getInt(22, Gravity.CENTER_VERTICAL);
                mDividerColor = typedArray.getColor(23, ContextCompat.getColor(context, R.color.list_item_divider_color));
                mDividerMarginLeft = coverDimen2Px(typedArray, 24, ScreenExtKt.dp2px(context, 20), autoAdaptive);
                mDividerMarginRight = coverDimen2Px(typedArray, 25, ScreenExtKt.dp2px(context, 0), autoAdaptive);
                mDividerHeight = coverDimen2Px(typedArray, 26, ScreenExtKt.dp2px(context, 1), autoAdaptive);
                mPopIconTintColor = typedArray.getColor(27, Color.WHITE);
                typedArray.recycle();
            }

            return this;
        }

        /**
         * 设置外部是否右阴影遮罩
         */
        public Builder withBgShade(boolean val) {
            bgShade = val;
            return this;
        }

        /**
         * pop原本是在目标view的下面的，这个表示的是pop的底部到达屏幕的多少比例后自动变到目标view的上方
         */
        public Builder withReversalOffY(float val) {
            reversalOffY = (int) (val * ScreenExtKt.getScreenHeight(context));
            return this;
        }

        /**
         * 整个pop的背景色
         */
        public Builder withBackgroundColor(int val) {
            backgroundColor = val;
            return this;
        }

        /**
         * 每个item的高度
         */
        public Builder withItemHeight(int val) {
            itemHeight = val;
            return this;
        }

        /**
         * 每个item的宽度
         */
        public Builder withItemWidth(int val) {
            itemWidth = val;
            return this;
        }

        /**
         * 分割线的左外边距
         */
        public Builder withDividerMarginLeft(int val) {
            mDividerMarginLeft = val;
            return this;
        }

        /**
         * 分割线的右外边距
         */
        public Builder withDividerMarginRight(int val) {
            mDividerMarginRight = val;
            return this;
        }

        /**
         * 分割线的高度
         */
        public Builder withDividerHeight(int val) {
            mDividerHeight = val;
            return this;
        }

        /**
         * 图标的tint颜色
         */
        public Builder withPopIconTintColor(int val) {
            mPopIconTintColor = val;
            return this;
        }

        /**
         * 整个pop在x轴上的偏移（负数往左，正数往右）
         */
        public Builder withPopOffY(int val) {
            popOffY = val;
            return this;
        }

        /**
         * 整个pop在y轴上的偏移（负数往上，正数往下）
         */
        public Builder withPopOffX(int val) {
            popOffX = val;
            return this;
        }

        /**
         * 箭头和底下部分的偏移（负数往左，正数往右）
         *
         * @param val 单位是px
         * @return
         */
        public Builder withArrowContentOffX(int val) {
            arrowContentOffX = val;
            return this;
        }

        /**
         * 设置整个pop的最小左外边距
         */
        public Builder withMarginLeft(int val) {
            marginLeft = val;
            return this;
        }

        /**
         * 设置整个pop的最小右外边距
         */
        public Builder withMarginRight(int val) {
            marginRight = val;
            return this;
        }

        /**
         * pop四个角的圆角半径
         */
        public Builder withCornerRadius(int val) {
            cornerRadius = val;
            return this;
        }

        /**
         * 每个item的左内边距
         */
        public Builder withItemPaddingLeft(int val) {
            itemPaddingLeft = val;
            return this;
        }

        /**
         * 每个item的右内边距
         */
        public Builder withItemPaddingRight(int val) {
            itemPaddingRight = val;
            return this;
        }

        /**
         * item图标的大小
         */
        public Builder withIconSize(int val) {
            iconSize = val;
            return this;
        }

        /**
         * item内容的Gravity，对齐方式
         */
        public Builder withItemGravity(int val) {
            itemGravity = val;
            return this;
        }

        /**
         * 每个item的字体大小
         */
        public Builder withTextSize(float val) {
            textSize = val;
            return this;
        }

        /**
         * 每个item的字体颜色
         */
        public Builder withTextColor(int val) {
            textColor = val;
            return this;
        }

        /**
         * item图标和文字的边距
         */
        public Builder withTextImageMargin(int val) {
            textImageMargin = val;
            return this;
        }

        /**
         * 设置pop外的地方是否可以点击
         */
        public Builder withFocus(boolean val) {
            focus = val;
            return this;
        }

        /**
         * 设置点击pop外的地方是否关闭pop
         */
        public Builder withIsOutside(boolean val) {
            isOutside = val;
            return this;
        }

        /**
         * 按压状态下的背景色
         */
        public Builder withBackgroundPressedColor(int val) {
            backgroundPressedColor = val;
            return this;
        }

        /**
         * 分割线的颜色
         */
        public Builder withDividerColor(int val) {
            mDividerColor = val;
            return this;
        }

        private int coverDimen2Px(TypedArray typedArray, int attrId, int defaultVal, boolean autoAdaptive) {
            String dimen = typedArray.getString(attrId);
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(attrId, defaultVal);
            if (!autoAdaptive || !dimen.endsWith("px")) {
                return dimensionPixelOffset;
            } else {
                return ScreenExtKt.dp2px(context, dimensionPixelOffset);
            }
        }

        /**
         * 添加选项
         */
        public Builder addMenuItem(IconParam item) {
            list.add(item);
            return this;
        }

        /**
         * 添加选项
         */
        public Builder addMenuItem(String itemTitle, Runnable onItemClick) {
            return addMenuItem(null, itemTitle, onItemClick);
        }

        /**
         * 添加选项
         */
        public Builder addMenuItem(Drawable icon, String itemTitle, Runnable onItemClick) {
            list.add(new IconParam(icon, itemTitle, onItemClick));
            return this;
        }

        /**
         * 添加选项
         */
        public Builder addMenuItem(@DrawableRes int iconRes, String itemTitle, Runnable onItemClick) {
            list.add(new IconParam(iconRes, itemTitle, onItemClick));
            return this;
        }

        public ListPopWindow build() {
            // 准备item的背景(带圆角的)
            int roundRadius = ScreenExtKt.dp2px(context, cornerRadius);
            Drawable[] backgrounds = new Drawable[list.size()];
            for (int i = 0; i < backgrounds.length; i++) {
                int topRadius = i == 0 ? roundRadius : 0;
                int bottomRadius = i == backgrounds.length - 1 ? roundRadius : 0;
                float[] radii = {topRadius, topRadius, topRadius, topRadius, bottomRadius, bottomRadius, bottomRadius, bottomRadius};

                GradientDrawable normalDrawable = new GradientDrawable();
                normalDrawable.setShape(GradientDrawable.RECTANGLE);
                normalDrawable.setCornerRadii(radii);
                normalDrawable.setColor(Color.TRANSPARENT);

                GradientDrawable pressedDrawable = new GradientDrawable();
                pressedDrawable.setShape(GradientDrawable.RECTANGLE);
                pressedDrawable.setCornerRadii(radii);
                pressedDrawable.setColor(backgroundPressedColor);

                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
                stateListDrawable.addState(new int[]{}, normalDrawable);
                backgrounds[i] = stateListDrawable;
            }

            // 获取目标view的位置和大小
            int[] targetLocation = new int[2];
            attachView.getLocationInWindow(targetLocation);
            int attachViewWidth = attachView.getWidth() == 0 ? attachView.getMeasuredWidth() : attachView.getWidth();
            int attachViewHeight = attachView.getHeight() == 0 ? attachView.getMeasuredHeight() : attachView.getHeight();

            // 初始化箭头控件
            int arrowWidth = ScreenExtKt.dp2px(context, 10) + roundRadius * 2;
            int arrowHeight = ScreenExtKt.dp2px(context, 7);
            ImageView arrowView = new ImageView(attachView.getContext());
            arrowView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams arrowLp = new LinearLayout.LayoutParams(arrowWidth, arrowHeight);
            arrowView.setPadding(roundRadius, 0, roundRadius, 0);

            // 首先计算整个popWindow的高度
            int popHeight = itemHeight * list.size() + arrowHeight * 2;
            // 初始化整个pop的container
            LinearLayout popView = new LinearLayout(attachView.getContext());
            popView.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, popHeight));
            popView.setOrientation(LinearLayout.VERTICAL);

            // 判断pop是在目标的上方或者下方
            boolean isBelowTarget = targetLocation[1] + attachViewHeight + popHeight + popOffY < reversalOffY;

            // 添加列表的部分
            LinearLayout listContianer = new LinearLayout(context);
            listContianer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listContianer.setOrientation(LinearLayout.VERTICAL);
            float[] radii = {roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius};
            GradientDrawable listContainerBackground = new GradientDrawable();
            listContainerBackground.setShape(GradientDrawable.RECTANGLE);
            listContainerBackground.setCornerRadii(radii);
            listContainerBackground.setColor(backgroundColor);
            listContianer.setBackground(listContainerBackground);
            popView.addView(listContianer);


            for (int i = 0; i < list.size(); i++) {
                LinearLayout item = new LinearLayout(attachView.getContext());
                // 设置背景（顶部、底部和中间的圆角不同）
                item.setBackground(backgrounds[i]);

                item.setPadding(itemPaddingLeft, 0, itemPaddingRight, 0);
                item.setGravity(itemGravity);

                IconParam iconParam = list.get(i);

                // 设置文字
                TextView tvTitle = new TextView(context);
                tvTitle.setText(iconParam.getTitle());
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tvTitle.setTextColor(textColor);
                tvTitle.setLines(1);
                tvTitle.setEllipsize(TextUtils.TruncateAt.END);
                LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // 设置图标
                Integer iconTintColor = iconParam.getIconTintColor();
                Drawable iconDrawable = iconParam.getIconImg() != null ? iconParam.getIconImg() : iconParam.getIconRes() != 0 ? ContextCompat.getDrawable(context, iconParam.getIconRes()) : null;
                if (iconDrawable != null) {
                    if (iconTintColor != null) {
                        DrawableCompat.setTint(iconDrawable, iconTintColor);
                    } else if (mPopIconTintColor != 0) {
                        DrawableCompat.setTint(iconDrawable, mPopIconTintColor);
                    }
                    ImageView ivIcon = new ImageView(context);
                    ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivIcon.setImageDrawable(iconDrawable);

                    LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(iconSize, iconSize);
                    item.addView(ivIcon, iconLp);

                    textLp.setMarginStart(textImageMargin);
                }

                item.addView(tvTitle, textLp);


                // 设置点击事件
                final IconParam onClickListenerIconParam = iconParam;
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListenerIconParam.getOnItemClickListener() != null) {
                            onClickListenerIconParam.getOnItemClickListener().run();
                        }
                        popupWindow.dismiss();
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
                listContianer.addView(item, params);

                // 添加divider
                if (mDividerHeight > 0 && i < list.size() - 1) {
                    View divider = new View(context);
                    divider.setBackgroundColor(mDividerColor);
                    LinearLayout.LayoutParams dividerLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDividerHeight);
                    dividerLp.setMarginStart(mDividerMarginLeft);

                    dividerLp.setMarginEnd(mDividerMarginRight);
                    listContianer.addView(divider, dividerLp);
                }
            }


            // 最后添加箭头控件
            if (isBelowTarget) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_triangle_indicator_up);
                DrawableCompat.setTint(drawable, backgroundColor);
                arrowView.setImageDrawable(drawable);
                popView.addView(arrowView, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_triangle_indicator_down);
                DrawableCompat.setTint(drawable, backgroundColor);
                arrowView.setImageDrawable(drawable);
                popView.addView(arrowView);
            }


            popupWindow = new PopupWindow(popView, itemWidth, popHeight);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setContentView(popView);
            popupWindow.setFocusable(focus);
            popupWindow.setOutsideTouchable(isOutside);
            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

            ListPopWindow customPopWindow = new ListPopWindow();
            customPopWindow.builder = this;

            // 计算弹出框得位置
            // y轴的位置（相对于屏幕而言）
            if (isBelowTarget) {
                customPopWindow.popY = targetLocation[1] + attachView.getHeight() + popOffY;
            } else {
                customPopWindow.popY = targetLocation[1] - popHeight - popOffY;
            }

            // X轴的位置（相对于屏幕而言）
            int arrowCenterPositionX = targetLocation[0] + (attachViewWidth >> 1) + popOffX;
            int popXTemp;
            if (popGravity == LEFT) {
                popXTemp = arrowCenterPositionX - (arrowWidth >> 1);
            } else if (popGravity == RIGHT) {
                popXTemp = arrowCenterPositionX + (arrowWidth >> 1) - itemWidth;
            } else {
                popXTemp = arrowCenterPositionX - (itemWidth >> 1);
            }

            // 处理角标和列表的偏移
            popXTemp -= arrowContentOffX;
            if (popXTemp > arrowCenterPositionX - (arrowWidth >> 1)) {
                popXTemp = arrowCenterPositionX - (arrowWidth >> 1);
            } else if (popXTemp + itemWidth < arrowCenterPositionX + (arrowWidth >> 1)) {
                popXTemp = arrowCenterPositionX + (arrowWidth >> 1) - itemWidth;
            }

            // 处理靠边时得偏移
            if (popXTemp < marginLeft) {
                popXTemp = marginLeft;
            } else if (popXTemp > ScreenExtKt.getScreenWidth(context) - marginRight - itemWidth) {
                popXTemp = ScreenExtKt.getScreenWidth(context) - marginRight - itemWidth;
            }

            // 偏移箭头的位置
            arrowLp.leftMargin = arrowCenterPositionX - popXTemp - (arrowWidth >> 1);
            arrowView.setLayoutParams(arrowLp);
            customPopWindow.popX = popXTemp;
            return customPopWindow;
        }
    }

    public static class IconParam {


        private Drawable iconImg;

        private int iconRes;

        private String title;

        private Integer iconTintColor;

        private Runnable onItemClickListener;

        public IconParam(Drawable iconImg, String iconText, Runnable onItemClickListener) {
            this.iconImg = iconImg;
            this.title = iconText;
            this.onItemClickListener = onItemClickListener;
        }

        public IconParam(@DrawableRes int iconImg, String iconText, Runnable onItemClickListener) {
            this.iconRes = iconImg;
            this.title = iconText;
            this.onItemClickListener = onItemClickListener;
        }


        public IconParam(Drawable iconImg, String iconText, Runnable onItemClickListener, int iconTintColor) {
            this.iconImg = iconImg;
            this.title = iconText;
            this.onItemClickListener = onItemClickListener;
            this.iconTintColor = iconTintColor;
        }

        public IconParam(@DrawableRes int iconImg, String iconText, Runnable onItemClickListener, int iconTintColor) {
            this.iconRes = iconImg;
            this.title = iconText;
            this.onItemClickListener = onItemClickListener;
            this.iconTintColor = iconTintColor;
        }

        public Integer getIconTintColor() {
            return iconTintColor;
        }

        public void setIconTintColor(int iconTintColor) {
            this.iconTintColor = iconTintColor;
        }

        public Drawable getIconImg() {
            return iconImg;
        }

        public void setIconImg(Drawable iconImg) {
            this.iconImg = iconImg;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Runnable getOnItemClickListener() {
            return onItemClickListener;
        }

        public void setOnItemClickListener(Runnable onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }
}
