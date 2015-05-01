// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BigListVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.BigListVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361923, "field 'mListView'");
    target.mListView = finder.castView(view, 2131361923, "field 'mListView'");
    view = finder.findRequiredView(source, 2131361922, "field 'mTextView'");
    target.mTextView = finder.castView(view, 2131361922, "field 'mTextView'");
  }

  @Override public void reset(T target) {
    target.mListView = null;
    target.mTextView = null;
  }
}
