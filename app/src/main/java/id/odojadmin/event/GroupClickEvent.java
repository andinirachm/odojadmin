package id.odojadmin.event;

import id.odojadmin.model.Group;

public class GroupClickEvent {
    protected int position;
    protected Group group;

    public GroupClickEvent(int position, Group group) {
        this.position = position;
        this.group = group;
    }

    public int getPosition() {
        return position;
    }

    public Group getGroup() {
        return group;
    }
}
