// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.MainVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296352, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131296352, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131296377, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131296377, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131296420, "field 'mNavListView'");
    target.mNavListView = finder.castView(view, 2131296420, "field 'mNavListView'");
    view = finder.findRequiredView(source, 2131296370, "field 'mConainerLayout'");
    target.mConainerLayout = finder.castView(view, 2131296370, "field 'mConainerLayout'");
    view = finder.findRequiredView(source, 2131296348, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131296348, "field 'mRefreshLayout'");
  }

  @Override public void reset(T target) {
    target.mToolbar = null;
    target.mDrawerLayout = null;
    target.mNavListView = null;
    target.mConainerLayout = null;
    target.mRefreshLayout = null;
  }
}
