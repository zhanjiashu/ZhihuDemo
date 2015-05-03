// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class QuizVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.QuizVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361894, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131361894, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131361933, "field 'mTabStrip'");
    target.mTabStrip = finder.castView(view, 2131361933, "field 'mTabStrip'");
    view = finder.findRequiredView(source, 2131361934, "field 'mContentPager'");
    target.mContentPager = finder.castView(view, 2131361934, "field 'mContentPager'");
  }

  @Override public void reset(T target) {
    target.mToolbar = null;
    target.mTabStrip = null;
    target.mContentPager = null;
  }
}
