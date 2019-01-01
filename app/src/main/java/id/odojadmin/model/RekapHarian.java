package id.odojadmin.model;

import java.util.List;

public class RekapHarian {
    private String id;
    private int groupId;
    private String date;
    private int totalKholas;
    private int totalMember;
    private List<Member> memberHarianList;

    public RekapHarian() {
    }

    public RekapHarian(String  id, int groupId, String date, int totalKholas, int totalMember, List<Member> memberHarianList) {
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
}
