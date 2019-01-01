package id.odojadmin.event;

import id.odojadmin.model.FormatRekapan;

public class GetFormatRekapanByGroupIdEvent extends BaseEvent {
    private FormatRekapan rekapanList;

    public GetFormatRekapanByGroupIdEvent(boolean isSuccess, String message, FormatRekapan rekapanList) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.rekapanList = rekapanList;
    }

    public FormatRekapan getRekapan() {
        return rekapanList;
    }
}
