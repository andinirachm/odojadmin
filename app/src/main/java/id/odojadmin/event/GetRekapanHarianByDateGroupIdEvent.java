package id.odojadmin.event;

import id.odojadmin.model.RekapHarian;

public class GetRekapanHarianByDateGroupIdEvent extends BaseEvent {
    private RekapHarian rekapHarian;

    public GetRekapanHarianByDateGroupIdEvent(boolean isSuccess, String message,RekapHarian rekapHarian) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.rekapHarian = rekapHarian;
    }

    public RekapHarian getMemberList() {
        return rekapHarian;
    }
}
