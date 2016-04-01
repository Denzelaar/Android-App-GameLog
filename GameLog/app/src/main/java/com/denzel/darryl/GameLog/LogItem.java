package com.denzel.darryl.GameLog;

/**
 * Created by darryl on 02-Mar-16.
 */
public class LogItem {

    private long id;
    private String logitem;
    private String description;
    private String steamSite;

    public long getId() { return id;}

    public void setId(long id) {
        this.id = id;
    }

    public String getLogitem() {
        return logitem;
    }

    public void setLogitem(String logitem) {
        this.logitem = logitem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSteamSite() {
        return steamSite;
    }

    public void setSteamSite(String steamSite) {
        this.steamSite = steamSite;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return logitem + " " + description + " " + steamSite;
    }

}
