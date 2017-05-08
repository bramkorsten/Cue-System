package io.bramkorsten.cuesystem.classes;

/**
 * Created by Bram Korsten on 4/10/2017.
 */

public class Cue {
    private String message;
    private String sender;
    private String time;

    public Cue() {

    }

    public Cue(String msg, String sender, String time) {
        this.message = msg;
        this.sender = sender;
        this.time = time;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSender() {
        return this.sender;
    }

    public String getTime() {
        return time;
    }
}
