// Generated code from Butter Knife. Do not modify!
package com.jiashu.zhihudemo.vu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class EditVu$$ViewInjector<T extends com.jiashu.zhihudemo.vu.EditVu> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361901, "field 'mEditText'");
    target.mEditText = finder.castView(view, 2131361901, "field 'mEditText'");
  }

  @Override public void reset(T target) {
    target.mEditText = null;
  }
}
