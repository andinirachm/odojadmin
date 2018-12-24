package id.odojadmin.controller;

import de.greenrobot.event.EventBus;
import id.odojadmin.ApplicationMain;

public abstract class BaseController {
    protected EventBus eventBus = ApplicationMain.getInstance().getEventBus();
}
