package com.xmpp.library.xmpp;


import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;

public class RosterEntryItem {

    private RosterEntry rosterEntry;
    private Presence presence;

    public RosterEntryItem(RosterEntry rosterEntry, Presence presence) {
        this.rosterEntry = rosterEntry;
        this.presence = presence;
    }

    public RosterEntry getRosterEntry() {
        return rosterEntry;
    }

    public void setRosterEntry(RosterEntry rosterEntry) {
        this.rosterEntry = rosterEntry;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    @Override
    public String toString() {
        return "RosterEntryItem{" +
                "rosterEntry=" + rosterEntry +
                ", presence=" + presence +
                '}';
    }
}
