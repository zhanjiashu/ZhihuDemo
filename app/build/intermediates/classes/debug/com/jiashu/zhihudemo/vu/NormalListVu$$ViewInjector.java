// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NormalListVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.NormalListVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361925, "field 'mListView'");
    target.mListView = finder.castView(view, 2131361925, "field 'mListView'");
    view = finder.findRequiredView(source, 2131361924, "field 'mTextView'");
    target.mTextView = finder.castView(view, 2131361924, "field 'mTextView'");
  }

  @Override public void reset(T target) {
    target.mListView = null;
    target.mTextView = null;
  }
}
