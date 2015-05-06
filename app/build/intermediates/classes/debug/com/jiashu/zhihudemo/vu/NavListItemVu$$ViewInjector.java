// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NavListItemVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.NavListItemVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361937, "field 'mNavIcon'");
    target.mNavIcon = finder.castView(view, 2131361937, "field 'mNavIcon'");
    view = finder.findRequiredView(source, 2131361938, "field 'mNavName'");
    target.mNavName = finder.castView(view, 2131361938, "field 'mNavName'");
  }

  @Override public void reset(T target) {
    target.mNavIcon = null;
    target.mNavName = null;
  }
}
