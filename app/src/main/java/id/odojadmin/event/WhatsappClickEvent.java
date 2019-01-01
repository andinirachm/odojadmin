package id.odojadmin.event;

import id.odojadmin.model.Member;

public class WhatsappClickEvent {
    protected int position;
    protected Member member;

    public WhatsappClickEvent(int position, Member member) {
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
