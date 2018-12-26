package id.odojadmin.event;

import java.util.List;

import id.odojadmin.model.Group;

public class GetDetailGroupEvent extends BaseEvent {
    private Group group;

    public GetDetailGroupEvent(boolean isSuccess, String message, Group group) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.group = group;
    }

    public Group getGroupDetail() {
        return group;
    }
}
