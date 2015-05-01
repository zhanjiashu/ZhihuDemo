// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class DiscoveryVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.DiscoveryVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296333, "field 'mTabStrip'");
    target.mTabStrip = finder.castView(view, 2131296333, "field 'mTabStrip'");
    view = finder.findRequiredView(source, 2131296334, "field 'mContentPager'");
    target.mContentPager = finder.castView(view, 2131296334, "field 'mContentPager'");
  }

  @Override public void reset(T target) {
    target.mTabStrip = null;
    target.mContentPager = null;
  }
}
