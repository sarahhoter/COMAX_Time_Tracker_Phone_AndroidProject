package com.binasystems.mtimereporter.customview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;

import java.util.ArrayList;

public class LayoutOnTouchListener extends ViewGroup  {


	public LayoutOnTouchListener(Context context) {

		super(context);
	}

	public LayoutOnTouchListener(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LayoutOnTouchListener(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public int getDescendantFocusability() {
		return super.getDescendantFocusability();
	}

	@Override
	public void setDescendantFocusability(int focusability) {
		super.setDescendantFocusability(focusability);
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		super.requestChildFocus(child, focused);
	}

	@Override
	public void focusableViewAvailable(View v) {
		super.focusableViewAvailable(v);
	}

	@Override
	public boolean showContextMenuForChild(View originalView) {
		return super.showContextMenuForChild(originalView);
	}

	@Override
	public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
		return super.startActionModeForChild(originalView, callback);
	}

	@Override
	public View focusSearch(View focused, int direction) {
		return super.focusSearch(focused, direction);
	}

	@Override
	public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
		return super.requestChildRectangleOnScreen(child, rectangle, immediate);
	}

	@Override
	public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
		return super.requestSendAccessibilityEvent(child, event);
	}

	@Override
	public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
		return super.onRequestSendAccessibilityEvent(child, event);
	}

	@Override
	public void childHasTransientStateChanged(View child, boolean childHasTransientState) {
		super.childHasTransientStateChanged(child, childHasTransientState);
	}

	@Override
	public boolean hasTransientState() {
		return super.hasTransientState();
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		return super.dispatchUnhandledMove(focused, direction);
	}

	@Override
	public void clearChildFocus(View child) {
		super.clearChildFocus(child);
	}

	@Override
	public void clearFocus() {
		super.clearFocus();
	}

	@Override
	public View getFocusedChild() {
		return super.getFocusedChild();
	}

	@Override
	public boolean hasFocus() {
		return super.hasFocus();
	}

	@Override
	public View findFocus() {
		return super.findFocus();
	}

	@Override
	public boolean hasFocusable() {
		return super.hasFocusable();
	}

	@Override
	public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
		super.addFocusables(views, direction, focusableMode);
	}

	@Override
	public void setTouchscreenBlocksFocus(boolean touchscreenBlocksFocus) {
		super.setTouchscreenBlocksFocus(touchscreenBlocksFocus);
	}

	@Override
	public boolean getTouchscreenBlocksFocus() {
		return super.getTouchscreenBlocksFocus();
	}

	@Override
	public void findViewsWithText(ArrayList<View> outViews, CharSequence text, int flags) {
		super.findViewsWithText(outViews, text, flags);
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		super.dispatchWindowFocusChanged(hasFocus);
	}

	@Override
	public void addTouchables(ArrayList<View> views) {
		super.addTouchables(views);
	}

	@Override
	public void dispatchDisplayHint(int hint) {
		super.dispatchDisplayHint(hint);
	}

	@Override
	protected void dispatchVisibilityChanged(View changedView, int visibility) {
		super.dispatchVisibilityChanged(changedView, visibility);
	}

	@Override
	public void dispatchWindowVisibilityChanged(int visibility) {
		super.dispatchWindowVisibilityChanged(visibility);
	}

	@Override
	public void dispatchConfigurationChanged(Configuration newConfig) {
		super.dispatchConfigurationChanged(newConfig);
	}

	@Override
	public void recomputeViewAttributes(View child) {
		super.recomputeViewAttributes(child);
	}

	@Override
	public void bringChildToFront(View child) {
		super.bringChildToFront(child);
	}

	@Override
	public boolean dispatchDragEvent(DragEvent event) {
		return super.dispatchDragEvent(event);
	}

	@Override
	public void dispatchWindowSystemUiVisiblityChanged(int visible) {
		super.dispatchWindowSystemUiVisiblityChanged(visible);
	}

	@Override
	public void dispatchSystemUiVisibilityChanged(int visible) {
		super.dispatchSystemUiVisibilityChanged(visible);
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		return super.dispatchKeyEventPreIme(event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		return super.dispatchKeyShortcutEvent(event);
	}

	@Override
	public boolean dispatchTrackballEvent(MotionEvent event) {
		return super.dispatchTrackballEvent(event);
	}

	@Override
	protected boolean dispatchHoverEvent(MotionEvent event) {
		return super.dispatchHoverEvent(event);
	}

	@Override
	public void addChildrenForAccessibility(ArrayList<View> childrenForAccessibility) {
		super.addChildrenForAccessibility(childrenForAccessibility);
	}

	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		return super.onInterceptHoverEvent(event);
	}

	@Override
	protected boolean dispatchGenericPointerEvent(MotionEvent event) {
		return super.dispatchGenericPointerEvent(event);
	}

	@Override
	protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
		return super.dispatchGenericFocusedEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void setMotionEventSplittingEnabled(boolean split) {
		super.setMotionEventSplittingEnabled(split);
	}

	@Override
	public boolean isMotionEventSplittingEnabled() {
		return super.isMotionEventSplittingEnabled();
	}

	@Override
	public boolean isTransitionGroup() {
		return super.isTransitionGroup();
	}

	@Override
	public void setTransitionGroup(boolean isTransitionGroup) {
		super.setTransitionGroup(isTransitionGroup);
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
		return super.requestFocus(direction, previouslyFocusedRect);
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
		return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
	}

	@Override
	public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
		super.notifySubtreeAccessibilityStateChanged(child, source, changeType);
	}

	@Override
	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
		super.dispatchSaveInstanceState(container);
	}

	@Override
	protected void dispatchFreezeSelfOnly(SparseArray<Parcelable> container) {
		super.dispatchFreezeSelfOnly(container);
	}

	@Override
	protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
		super.dispatchRestoreInstanceState(container);
	}

	@Override
	protected void dispatchThawSelfOnly(SparseArray<Parcelable> container) {
		super.dispatchThawSelfOnly(container);
	}

	@Override
	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		super.setChildrenDrawingCacheEnabled(enabled);
	}

	@Override
	protected void onAnimationStart() {
		super.onAnimationStart();
	}

	@Override
	protected void onAnimationEnd() {
		super.onAnimationEnd();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	@Override
	public ViewGroupOverlay getOverlay() {
		return super.getOverlay();
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		return super.getChildDrawingOrder(childCount, i);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		return super.drawChild(canvas, child, drawingTime);
	}

	@Override
	public boolean getClipChildren() {
		return super.getClipChildren();
	}

	@Override
	public void setClipChildren(boolean clipChildren) {
		super.setClipChildren(clipChildren);
	}

	@Override
	public void setClipToPadding(boolean clipToPadding) {
		super.setClipToPadding(clipToPadding);
	}

	@Override
	public boolean getClipToPadding() {
		return super.getClipToPadding();
	}

	@Override
	public void dispatchSetSelected(boolean selected) {
		super.dispatchSetSelected(selected);
	}

	@Override
	public void dispatchSetActivated(boolean activated) {
		super.dispatchSetActivated(activated);
	}

	@Override
	protected void dispatchSetPressed(boolean pressed) {
		super.dispatchSetPressed(pressed);
	}

	@Override
	protected void setStaticTransformationsEnabled(boolean enabled) {
		super.setStaticTransformationsEnabled(enabled);
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		return super.getChildStaticTransformation(child, t);
	}

	@Override
	public void addView(View child) {
		super.addView(child);
	}

	@Override
	public void addView(View child, int index) {
		super.addView(child, index);
	}

	@Override
	public void addView(View child, int width, int height) {
		super.addView(child, width, height);
	}

	@Override
	public void addView(View child, LayoutParams params) {
		super.addView(child, params);
	}

	@Override
	public void addView(View child, int index, LayoutParams params) {
		super.addView(child, index, params);
	}

	@Override
	public void updateViewLayout(View view, LayoutParams params) {
		super.updateViewLayout(view, params);
	}

	@Override
	protected boolean checkLayoutParams(LayoutParams p) {
		return super.checkLayoutParams(p);
	}

	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		super.setOnHierarchyChangeListener(listener);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	@Override
	protected boolean addViewInLayout(View child, int index, LayoutParams params) {
		return super.addViewInLayout(child, index, params);
	}

	@Override
	protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
		return super.addViewInLayout(child, index, params, preventRequestLayout);
	}

	@Override
	protected void cleanupLayoutState(View child) {
		super.cleanupLayoutState(child);
	}

	@Override
	protected void attachLayoutAnimationParameters(View child, LayoutParams params, int index, int count) {
		super.attachLayoutAnimationParameters(child, params, index, count);
	}

	@Override
	public void removeView(View view) {
		super.removeView(view);
	}

	@Override
	public void removeViewInLayout(View view) {
		super.removeViewInLayout(view);
	}

	@Override
	public void removeViewsInLayout(int start, int count) {
		super.removeViewsInLayout(start, count);
	}

	@Override
	public void removeViewAt(int index) {
		super.removeViewAt(index);
	}

	@Override
	public void removeViews(int start, int count) {
		super.removeViews(start, count);
	}

	@Override
	public void setLayoutTransition(LayoutTransition transition) {
		super.setLayoutTransition(transition);
	}

	@Override
	public LayoutTransition getLayoutTransition() {
		return super.getLayoutTransition();
	}

	@Override
	public void removeAllViews() {
		super.removeAllViews();
	}

	@Override
	public void removeAllViewsInLayout() {
		super.removeAllViewsInLayout();
	}

	@Override
	protected void removeDetachedView(View child, boolean animate) {
		super.removeDetachedView(child, animate);
	}

	@Override
	protected void attachViewToParent(View child, int index, LayoutParams params) {
		super.attachViewToParent(child, index, params);
	}

	@Override
	protected void detachViewFromParent(View child) {
		super.detachViewFromParent(child);
	}

	@Override
	protected void detachViewFromParent(int index) {
		super.detachViewFromParent(index);
	}

	@Override
	protected void detachViewsFromParent(int start, int count) {
		super.detachViewsFromParent(start, count);
	}

	@Override
	protected void detachAllViewsFromParent() {
		super.detachAllViewsFromParent();
	}

	@Override
	public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
		return super.invalidateChildInParent(location, dirty);
	}

	@Override
	public boolean getChildVisibleRect(View child, Rect r, Point offset) {
		return super.getChildVisibleRect(child, r, offset);
	}

	@Override
	protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

	}

	@Override
	protected boolean canAnimate() {
		return super.canAnimate();
	}

	@Override
	public void startLayoutAnimation() {
		super.startLayoutAnimation();
	}

	@Override
	public void scheduleLayoutAnimation() {
		super.scheduleLayoutAnimation();
	}

	@Override
	public void setLayoutAnimation(LayoutAnimationController controller) {
		super.setLayoutAnimation(controller);
	}

	@Override
	public LayoutAnimationController getLayoutAnimation() {
		return super.getLayoutAnimation();
	}

	@Override
	public boolean isAnimationCacheEnabled() {
		return super.isAnimationCacheEnabled();
	}

	@Override
	public void setAnimationCacheEnabled(boolean enabled) {
		super.setAnimationCacheEnabled(enabled);
	}

	@Override
	public boolean isAlwaysDrawnWithCacheEnabled() {
		return super.isAlwaysDrawnWithCacheEnabled();
	}

	@Override
	public void setAlwaysDrawnWithCacheEnabled(boolean always) {
		super.setAlwaysDrawnWithCacheEnabled(always);
	}

	@Override
	protected boolean isChildrenDrawnWithCacheEnabled() {
		return super.isChildrenDrawnWithCacheEnabled();
	}

	@Override
	protected void setChildrenDrawnWithCacheEnabled(boolean enabled) {
		super.setChildrenDrawnWithCacheEnabled(enabled);
	}

	@Override
	protected boolean isChildrenDrawingOrderEnabled() {
		return super.isChildrenDrawingOrderEnabled();
	}

	@Override
	protected void setChildrenDrawingOrderEnabled(boolean enabled) {
		super.setChildrenDrawingOrderEnabled(enabled);
	}

	@Override
	public int getPersistentDrawingCache() {
		return super.getPersistentDrawingCache();
	}

	@Override
	public void setPersistentDrawingCache(int drawingCacheToKeep) {
		super.setPersistentDrawingCache(drawingCacheToKeep);
	}

	@Override
	public int getLayoutMode() {
		return super.getLayoutMode();
	}

	@Override
	public void setLayoutMode(int layoutMode) {
		super.setLayoutMode(layoutMode);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return super.generateLayoutParams(attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return super.generateLayoutParams(p);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return super.generateDefaultLayoutParams();
	}

	@Override
	protected void debug(int depth) {
		super.debug(depth);
	}

	@Override
	public int indexOfChild(View child) {
		return super.indexOfChild(child);
	}

	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	@Override
	public View getChildAt(int index) {
		return super.getChildAt(index);
	}

	@Override
	protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
		super.measureChildren(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
		super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
	}

	@Override
	protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
	}

	@Override
	public void clearDisappearingChildren() {
		super.clearDisappearingChildren();
	}

	@Override
	public void startViewTransition(View view) {
		super.startViewTransition(view);
	}

	@Override
	public void endViewTransition(View view) {
		super.endViewTransition(view);
	}

	@Override
	public boolean gatherTransparentRegion(Region region) {
		return super.gatherTransparentRegion(region);
	}

	@Override
	public void requestTransparentRegion(View child) {
		super.requestTransparentRegion(child);
	}

	@Override
	public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
		return super.dispatchApplyWindowInsets(insets);
	}

	@Override
	public Animation.AnimationListener getLayoutAnimationListener() {
		return super.getLayoutAnimationListener();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
	}

	@Override
	public void jumpDrawablesToCurrentState() {
		super.jumpDrawablesToCurrentState();
	}

	@Override
	public void drawableHotspotChanged(float x, float y) {
		super.drawableHotspotChanged(x, y);
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		return super.onCreateDrawableState(extraSpace);
	}

	@Override
	public void setAddStatesFromChildren(boolean addsStates) {
		super.setAddStatesFromChildren(addsStates);
	}

	@Override
	public boolean addStatesFromChildren() {
		return super.addStatesFromChildren();
	}

	@Override
	public void childDrawableStateChanged(View child) {
		super.childDrawableStateChanged(child);
	}

	@Override
	public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
		super.setLayoutAnimationListener(animationListener);
	}

	@Override
	public boolean shouldDelayChildPressedState() {
		return super.shouldDelayChildPressedState();
	}

	@Override
	public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
		return super.onStartNestedScroll(child, target, nestedScrollAxes);
	}

	@Override
	public void onNestedScrollAccepted(View child, View target, int axes) {
		super.onNestedScrollAccepted(child, target, axes);
	}

	@Override
	public void onStopNestedScroll(View child) {
		super.onStopNestedScroll(child);
	}

	@Override
	public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
	}

	@Override
	public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
		super.onNestedPreScroll(target, dx, dy, consumed);
	}

	@Override
	public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
		return super.onNestedFling(target, velocityX, velocityY, consumed);
	}

	@Override
	public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
		return super.onNestedPreFling(target, velocityX, velocityY);
	}

	@Override
	public int getNestedScrollAxes() {
		return super.getNestedScrollAxes();
	}
}
