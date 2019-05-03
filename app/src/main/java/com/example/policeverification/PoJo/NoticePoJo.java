package com.example.policeverification.PoJo;

public class NoticePoJo {

    private String id;
    private String tittle;
    private String notice;

    public NoticePoJo(String tittle, String notice) {
        this.tittle = tittle;
        this.notice = notice;
    }

    public NoticePoJo(String id, String tittle, String notice) {
        this.id = id;
        this.tittle = tittle;
        this.notice = notice;
    }

    public NoticePoJo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
