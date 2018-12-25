package id.odojadmin.event;

import java.util.List;

import id.odojadmin.model.Group;

public class GetGroupByAdminIdEvent extends BaseEvent {
    private List<Group> groupList;

    public GetGroupByAdminIdEvent(boolean isSuccess, String message, List<Group> groupList) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.groupList = groupList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }
}
