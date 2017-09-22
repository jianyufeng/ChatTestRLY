package com.fule.myapplication.common;

/**
 * Created by Administrator on 2016/8/4.
 */
public enum  Account {
    JIANYUFENG("jianyufeng ","uJQgudFC1uNGCBdidXxdD5SSp8XMvSxhI/jzjXcPG1uAkrSuMzOFHhORekdGrTGDgyoT1vbBUOX3AzLBpIuZbVRybifoCGNp"),
    JIANYUFENG1("jianyufeng1","6gJt7XsG6nZVf79pc/1PXpnvCftvcerND+jbdgKxJF6OdcO0Dgc8Tq9KpFml/sDBDYLP0pntI55wl1psVlQVBSpzne7fwht6"),
    JIANYUFENG2("jianyufeng2","FXCwwW2ahHI0y8cFrI24KZnvCftvcerND+jbdgKxJF6OdcO0Dgc8TtvDvfTG9w9ZDYLP0pntI55wl1psVlQVBSkFX7EOwRW/");
    private final String mId;
    private final Object mDefaultValue;

    public Object getmDefaultValue() {
        return mDefaultValue;
    }

    public String getmId() {
        return mId;
    }

    private Account(String id, Object defaultValue){
      this.mId = id;
      this.mDefaultValue = defaultValue;
  }
    public static Account fromId(String id) {
        Account[] values = values();
        int cc = values.length;
        for (int i = 0; i < cc; i++) {
            if (values[i].mId == id) {
                return values[i];
            }
        }
        return null;
    }
}
