package com.feitianzhu.fu700.payforme;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dicallc on 2017/10/17 0017.
 */

public class FlowEvent {
  public static final int SHOP_PAY_FLOW = 0;

  @IntDef({ SHOP_PAY_FLOW }) @Retention(RetentionPolicy.SOURCE) public @interface WeekDays {
  }

  @WeekDays int currentDay = SHOP_PAY_FLOW;

  public FlowEvent(@WeekDays int mCurrentDay) {
    currentDay = mCurrentDay;
  }
  @WeekDays public int getEvent() {
    return currentDay;
  }
}
