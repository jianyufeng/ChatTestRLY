package com.fule.myapplication.group;

/**
 * Created by Administrator on 2016/11/19.
 */

public class Group {
    public int getIcon() {
        return icon;
    }

    public Group() {
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public int getIsMyCreate() {
        return isMyCreate;
    }

    public void setIsMyCreate(int isMyCreate) {
        this.isMyCreate = isMyCreate;
    }

    String groupName;
    String groupIcon;
    int isMyCreate;
}
