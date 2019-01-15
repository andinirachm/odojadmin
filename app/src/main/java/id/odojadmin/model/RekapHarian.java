package id.odojadmin.model;

import java.util.List;
import java.util.Map;

import id.odojadmin.ApplicationMain;

public class RekapHarian {
    private String id;
    private int groupId;
    private String date;
    private int totalKholas;
    private int totalMember;
    private List<Member> memberHarianList;

    public RekapHarian() {
    }

    public RekapHarian(String id, int groupId, String date, int totalKholas, int totalMember, List<Member> memberHarianList) {
        this.id = id;
        this.groupId = groupId;
        this.date = date;
        this.totalKholas = totalKholas;
        this.totalMember = totalMember;
        this.memberHarianList = memberHarianList;
    }

    public String getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getDate() {
        return date;
    }

    public int getTotalKholas() {
        return totalKholas;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public List<Member> getMemberHarianList() {
        return memberHarianList;
    }

    public void createRekapHarian(RekapHarian group) {
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().child(id).setValue(group);
    }

    public void updateRekapHarian(int groupId, Map<String, Object> map) {
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().child(String.valueOf(groupId)).updateChildren(map);
    }

    public void updateTilawah(int groupId, String date, int idMember, Map<String, Object> map) {
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().child(String.valueOf(groupId) + "-" + date).
                child("memberHarianList").child(String.valueOf(idMember)).updateChildren(map);
    }

    public void deleteGroupRekapHarian(int group) {
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().child(String.valueOf(group)).removeValue();
    }
}
