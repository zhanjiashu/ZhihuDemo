// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class GuidePageVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.GuidePageVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361899, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131361899, "field 'mViewPager'");
    view = finder.findRequiredView(source, 2131361903, "field 'mIndicator'");
    target.mIndicator = finder.castView(view, 2131361903, "field 'mIndicator'");
    view = finder.findRequiredView(source, 2131361904, "field 'mTip'");
    target.mTip = finder.castView(view, 2131361904, "field 'mTip'");
    view = finder.findRequiredView(source, 2131361901, "method 'onLoginClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onLoginClick();
        }
      });
    view = finder.findRequiredView(source, 2131361902, "method 'onRegisterClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onRegisterClick();
        }
      });
  }

  @Override public void reset(T target) {
    target.mViewPager = null;
    target.mIndicator = null;
    target.mTip = null;
  }
}
