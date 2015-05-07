// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ViewPagerVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.ViewPagerVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361944, "field 'mTab'");
    target.mTab = finder.castView(view, 2131361944, "field 'mTab'");
    view = finder.findRequiredView(source, 2131361945, "field 'mPager'");
    target.mPager = finder.castView(view, 2131361945, "field 'mPager'");
  }

  @Override public void reset(T target) {
    target.mTab = null;
    target.mPager = null;
  }
}
