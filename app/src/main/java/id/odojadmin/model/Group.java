package id.odojadmin.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import id.odojadmin.ApplicationMain;

public class Group implements Serializable {
    private String id; //not editable
    private int totalMember;
    private int totalKholas;
    private String adminId;
    private String jamKholas;
    private String adminName;
    private String asmin;

    public Group() {
    }

    public Group(String id, int totalMember, int totalKholas, String adminId, String jamKholas, String adminName, String asmin) {
        this.id = id;
        this.totalMember = totalMember;
        this.totalKholas = totalKholas;
        this.adminId = adminId;
        this.jamKholas = jamKholas;
        this.adminName = adminName;
        this.asmin = asmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    public int getTotalKholas() {
        return totalKholas;
    }

    public void setTotalKholas(int totalKholas) {
        this.totalKholas = totalKholas;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getJamKholas() {
        return jamKholas;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAsmin() {
        return asmin;
    }

    public void createGroup(Group group) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(id).setValue(group);
    }

    public void updateGroup(int groupId, Map<String, Object> map) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(String.valueOf(groupId)).updateChildren(map);
    }

    public void updateAdmin(int groupId, Map<String, Object> map) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(String.valueOf(groupId)).updateChildren(map);
    }

    public void deleteGroup(int group) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(String.valueOf(group)).removeValue();
    }
}
