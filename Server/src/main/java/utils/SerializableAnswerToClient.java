package utils;

import message.MessageColor;

import java.io.Serializable;

public class SerializableAnswerToClient implements Serializable {
    private static final long serialVersionUID = 12324123;

    private MessageColor color;
    private String ans;
    private Boolean bool = false;

    public SerializableAnswerToClient(MessageColor color, String ans) {
        this.color = color;
        this.ans = ans;
    }

    public MessageColor getColor() {
        return color;
    }

    public void setNormaAns(MessageColor color) {
        this.color = color;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }
}
