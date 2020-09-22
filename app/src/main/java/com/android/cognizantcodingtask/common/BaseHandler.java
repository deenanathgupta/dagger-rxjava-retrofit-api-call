package com.android.cognizantcodingtask.common;

import android.view.View;

public interface BaseHandler<T> {
    void onClick(View view, T data);
}
