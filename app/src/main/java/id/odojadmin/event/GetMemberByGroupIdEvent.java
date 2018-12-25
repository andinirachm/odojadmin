package id.odojadmin.event;

import java.util.List;

import id.odojadmin.model.Member;

public class GetMemberByGroupIdEvent extends BaseEvent {
    private List<Member> groupList;

    public GetMemberByGroupIdEvent(boolean isSuccess, String message, List<Member> groupList) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.groupList = groupList;
    }

    public List<Member> getMemberList() {
        return groupList;
    }
}
