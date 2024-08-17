package com.helper;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class ControllerHelper {
    public static void ensureNoSubmitOnRefresh() {
        // prevents the form from resubmitting on page refresh
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
