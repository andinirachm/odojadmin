package id.odojadmin.event;

import id.odojadmin.model.Member;

public class KholasClickEvent {
    protected int position;
    protected Member member;

    public KholasClickEvent(int position, Member member) {
        this.position = position;
        this.member = member;
    }

    public int getPosition() {
        return position;
    }

    public Member getMember() {
        return member;
    }
}
