package template.solainteractive.com.androidsolatemplate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sola on 09/03/2018.
 */

public class TerminalModel extends MainResponse{
    @SerializedName("TERMINAL")
    public List<Terminal> terminalList;

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }
}

