// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu.inner;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FolTopicVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.inner.FolTopicVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'mFragmentName'");
    target.mFragmentName = finder.castView(view, 2131296335, "field 'mFragmentName'");
  }

  @Override public void reset(T target) {
    target.mFragmentName = null;
  }
}
